/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.cche.dbutils.Jdbc;
import cn.cche.dbutils.TransMgr;
import cn.cche.exception.DAOException;
import cn.cche.util.CommonUtil;
import cn.cche.util.Constant;
import cn.cche.vo.User;
import cn.cche.web.beans.ReqBean;
import cn.cche.web.beans.RespBean;

/**
 * @author chexingyou
 * @date 2013-5-14
 */
public class UserServiceImpl {

	public RespBean<User> list(ReqBean reqBean) {

		RespBean<User> bean = new RespBean<User>();
		List<User> list = null;
		QueryRunner runner = Jdbc.getQueryRunner();
		try {
			list = runner.query("select * from t_user", new BeanListHandler<User>(User.class));
			bean.setSuc();
			bean.setMapping("/userList.jsp");
		} catch (SQLException e) {
			bean.setErr();
			throw new DAOException("获取用户失败");
		}

		bean.setDataList(list);

		return bean;
	}

	public RespBean<User> top(ReqBean reqBean, int top) {

		RespBean<User> bean = new RespBean<User>();
		List<User> list = null;
		QueryRunner runner = Jdbc.getQueryRunner();
		try {
			list = runner.query("select * from t_user limit 0," + top, new BeanListHandler<User>(
					User.class));
			bean.setSuc();
		} catch (SQLException e) {
			bean.setErr();
			throw new DAOException("获取用户失败");
		}

		bean.setDataList(list);

		return bean;
	}

	public RespBean<User> show(ReqBean reqBean) {

		RespBean<User> bean = new RespBean<User>();
		List<User> list = null;
		QueryRunner runner = Jdbc.getQueryRunner();
		try {
			list = runner.query("select * from t_user where userid= ?", new BeanListHandler<User>(
					User.class), reqBean.getParams());
			bean.setSuc();
			bean.setMapping("/userDetail.jsp");
		} catch (SQLException e) {
			bean.setErr();
			throw new DAOException("获取用户失败");
		}

		bean.setDataList(list);

		return bean;
	}

	public RespBean<User> reg(ReqBean reqBean) {

		RespBean<User> bean = new RespBean<User>();

		User user = (User) reqBean.getVoInstance();

		String result = addUser(user);
		/*if (result.equals(Constant.RegResult.SUC)) {
			bean.setSuc();
		} else {
			bean.setErr();
		}*/
		bean.setRespMsg(result);
		bean.setMappingType(Constant.MappingType.AJAX);
		bean.setMapping(Constant.ERR_PAGE);
		return bean;
	}

	protected String addUser(User user) {

		String result = null;

		QueryRunner runner = Jdbc.getQueryRunner();
		Connection conn = null;
		TransMgr tm = null;
		try {

			if (validateExists(user)) {
				return Constant.RegResult.USERNAMEERR;
			}

			conn = Jdbc.getConnection();
			tm = Jdbc.getTransMgr(conn);
			tm.beginTransaction();

			int i = runner.update(conn, "insert into t_user(username,password) values (?,?)",
					user.getUsername(), user.getPassword());
			if (i > 0) {
				Object[] id = runner.query(conn, "select id from t_user where username =?",
						new ArrayHandler(), user.getUsername());
				String prefix = (new Random().nextInt(999) + "000").substring(0, 3);
				i = runner.update(conn, "update t_user set userid = ? where username=?", prefix
						+ id[0], user.getUsername());
				if (i > 0) {
					tm.commitAndClose();
					result = Constant.RegResult.SUC;
				}
			}
		} catch (SQLException e) {
			tm.rollbackAndClose();
			result = Constant.RegResult.ERR;
			e.printStackTrace();
		} finally {
			if (conn != null) {
				Jdbc.close(conn);
			}
		}

		return result;
	}

	public boolean validateExists(User user) {

		boolean flag = false;

		QueryRunner runner = Jdbc.getQueryRunner();

		try {
			Object ret = runner.query("select * from t_user where username =?",
					new BeanHandler<User>(User.class), user.getUsername());
			System.out.println("ret =============" + ret + ",username =============" + user.getUsername() );
			if (ret != null) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public RespBean<User> login(ReqBean reqBean) {

		RespBean<User> bean = new RespBean<User>();

		String redirect = null;
		if (!CommonUtil.isEmpty(reqBean.getRequest().getParameter(Constant.URLREDIRECT))) {
			redirect = reqBean.getRequest().getParameter(Constant.URLREDIRECT);
		}
		System.out.println("redirect: " + redirect);
		if (redirect != null) {
			bean.setMappingType(Constant.MappingType.REDIRECT);
			bean.setMapping(redirect);
		} else {
			bean.setMappingType(Constant.MappingType.REDIRECT);
			bean.setMapping(Constant.ERR_PAGE);
		}

		User user = (User) reqBean.getVoInstance();

		QueryRunner runner = Jdbc.getQueryRunner();
		try {
			Object ret = runner.query("select * from t_user where username =? and password = ?",
					new BeanHandler<User>(User.class), user.getUsername(), user.getPassword());
			
			if (ret != null) {
				user = (User) ret;
				bean.setSuc();
				reqBean.getRequest().getSession().setAttribute(Constant.SESSIONATTR, user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
}
