/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.service;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.cche.cache.CacheMgr;
import cn.cche.cache.CacheValue;
import cn.cche.dbutils.Jdbc;
import cn.cche.vo.ID;

/**
 * @author chexingyou
 * @date 2013-5-20
 */
public class BaseDAO {

	@SuppressWarnings("unchecked")
	public <T> T read(Class<T> clazz, String sql, Object... params) {

		T t = null;
		QueryRunner runner = Jdbc.getQueryRunner();
		try {
			t = (T) runner.query(sql, _IsPrimitive(clazz) ? _g_scaleHandler : new BeanHandler<T>(
					clazz), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return t;
	}

	@SuppressWarnings("unchecked")
	public <T> T read_cache(Class<T> clazz, String cache, String key, String sql, Object... params) {

		if (cache == null)
			cache = clazz.getSimpleName().toLowerCase();
		if (key == null)
			key = buildKey(sql, params);

		T t = (T) CacheMgr.get(cache, key);

		if (t == null) {
			t = read(clazz, sql, params);
			CacheMgr.put(cache, key, new CacheValue(t, System.currentTimeMillis()));
		}
		return t;

	}

	public <T> T read(Connection conn, Class<T> clazz, String sql, Object... params)
			throws SQLException {

		QueryRunner runner = Jdbc.getQueryRunner();
		List<T> list = null;
		list = runner.query(conn, sql, new BeanListHandler<T>(clazz), params);
		return list.size() > 0 ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> query(Class<T> clazz, String sql, Object... params) {

		List<T> list = null;

		QueryRunner runner = Jdbc.getQueryRunner();
		try {
			list = runner.query(sql, _IsPrimitive(clazz) ? _g_columnListHandler
					: new BeanListHandler<T>(clazz), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@SuppressWarnings("rawtypes")
	private final static ScalarHandler _g_scaleHandler = new ScalarHandler() {
		@Override
		public Object handle(ResultSet rs) throws SQLException {

			Object obj = super.handle(rs);
			if (obj instanceof BigInteger)
				return ((BigInteger) obj).longValue();
			return obj;
		}
	};
	@SuppressWarnings("rawtypes")
	private final static ColumnListHandler _g_columnListHandler = new ColumnListHandler() {
		@Override
		protected Object handleRow(ResultSet rs) throws SQLException {

			Object obj = super.handleRow(rs);
			if (obj instanceof BigInteger)
				return ((BigInteger) obj).longValue();
			return obj;
		}

	};

	private final static List<Class<?>> PrimitiveClasses = new ArrayList<Class<?>>() {
		private static final long serialVersionUID = 1L;

		{
			add(Long.class);
			add(Integer.class);
			add(String.class);
			add(java.util.Date.class);
			add(java.sql.Date.class);
			add(java.sql.Timestamp.class);
		}
	};

	private final static boolean _IsPrimitive(Class<?> cls) {

		return cls.isPrimitive() || PrimitiveClasses.contains(cls);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> query_cache(Class<T> clazz, String cache, String key, String sql,
			Object... params) {

		if (cache == null)
			cache = clazz.getSimpleName().toLowerCase();
		if (key == null)
			key = buildKey(sql, params);

		List<T> list = (List<T>) CacheMgr.get(cache, key);

		if (list == null) {
			list = query(clazz, sql, params);
			CacheMgr.put(cache, key, new CacheValue(list, System.currentTimeMillis()));
		}
		return list;
	}

	public <T> List<T> query(Connection conn, Class<T> clazz, String sql, Object... params)
			throws SQLException {

		QueryRunner runner = Jdbc.getQueryRunner();
		List<T> list = null;
		list = runner.query(conn, sql, new BeanListHandler<T>(clazz), params);
		return list;
	}

	public Object[] query(Connection conn, String sql, Object... params) throws SQLException {

		Object[] obj = null;
		QueryRunner runner = Jdbc.getQueryRunner();
		obj = runner.query(conn, sql, new ArrayHandler(), params);
		return obj;
	}

	public Object[] query(String sql, Object... params) {

		Object[] obj = null;
		QueryRunner runner = Jdbc.getQueryRunner();
		try {
			obj = runner.query(sql, new ArrayHandler(), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public int update(Connection conn, String sql, Object... params) throws SQLException {

		int ret = 0;
		QueryRunner runner = Jdbc.getQueryRunner();
		ret = runner.update(conn, sql, params);
		return ret;
	}

	public int update(String sql, Object... params) {

		int ret = 0;
		QueryRunner runner = Jdbc.getQueryRunner();
		try {
			ret = runner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

	protected String buildKey(String sql, Object... params) {

		for (Object obj : params) {
			sql = sql.replaceFirst("\\?", (String) obj);
		}
		return sql;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> loadList(Class<T> clazz, List<ID> ids, String orderBy) {

		List<T> list = new ArrayList<T>();
		for (int i = 0; i < ids.size(); i++) {
			list.add(null);
		}

		String clzName = clazz.getSimpleName().toLowerCase();
		List<String> no_cached_ids = new ArrayList<String>();

		for (int i = 0; i < ids.size(); i++) {
			String id = ids.get(i).getId();
			T obj = (T) CacheMgr.get(clzName, id);
			if (obj == null) {
				no_cached_ids.add(id);
			} else {
				list.set(i, obj);
			}
		}

		if (no_cached_ids.size() > 0) {

			List<? extends ID> no_cached_objs = (List<? extends ID>) query(
					clazz,
					"select * from t_" + clzName + " where id in("
							+ insertChar('?', no_cached_ids.size()) + ") " + orderBy,
					no_cached_ids.toArray());

			int j = 0;
			for (ID obj : no_cached_objs) {

				CacheMgr.put(clzName, obj.getId(), new CacheValue(obj, System.currentTimeMillis()));
				for (int i = j; i < ids.size(); i++) {
					if (list.get(i) == null) {
						list.set(i, (T) obj);
						j = i + 1;
						break;
					}
				}
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public <T> T loadBean(Class<T> clazz, ID pid) {

		String id = pid.getId();
		String clzName = clazz.getSimpleName().toLowerCase();

		T obj = (T) CacheMgr.get(clzName, id);
		if (obj == null) {

			obj = read(clazz, "select * from t_" + clzName + " where id = ? ", id);

			CacheMgr.put(clzName, ((ID) obj).getId(),
					new CacheValue(obj, System.currentTimeMillis()));

		}

		return obj;
	}

	protected String insertChar(char ch, int times) {

		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= times; i++) {
			sb.append(ch);
			if (i < times)
				sb.append(",");
		}
		return sb.toString();
	}

}
