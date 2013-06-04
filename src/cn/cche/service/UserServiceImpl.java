/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.cche.dbutils.Jdbc;
import cn.cche.dbutils.TransMgr;
import cn.cche.util.Utils;
import cn.cche.util.Const;
import cn.cche.vo.ID;
import cn.cche.vo.User;
import cn.cche.web.beans.ReqBean;
import cn.cche.web.beans.RespBean;

/**
 * @author chexingyou
 * @date 2013-5-14
 */
public class UserServiceImpl extends BaseDAO {

	public RespBean<User> list(ReqBean reqBean) {

		RespBean<User> bean = new RespBean<User>();

		String sql = "select * from t_user";
		sql = reqBean.getPageReq().buildSql(sql);

		List<ID> ids = query_cache(ID.class, null, null, sql);

		List<User> list = loadList(User.class, ids, null);

		bean.setDataList(list);
		bean.setMapping("/user/userlist.jsp");

		return bean;
	}

	public RespBean<User> show(ReqBean reqBean) {

		RespBean<User> bean = new RespBean<User>();
		String id = reqBean.getId();

		if (id == null) {
			bean.setMapping(Const.ERR_PAGE);
			return bean;
		}

		User user = read_cache(User.class, "user", id, "select * from t_user where id= ?", id);
		List<User> list = new ArrayList<User>();
		list.add(user);
		bean.setDataList(list);
		bean.setMapping("/user/userdetail.jsp");
		return bean;
	}

	public RespBean<User> reg(ReqBean reqBean) {

		RespBean<User> respBean = new RespBean<User>();

		User user = (User) reqBean.getVoInstance();
		if (user == null) {
			respBean.setMapping("/user/reg.html");
			return respBean;
		}

		addUser(reqBean, respBean);
		respBean.setMappingType(Const.MappingType.AJAX);
		respBean.setMapping(reqBean.errPage());
		return respBean;
	}

	protected void addUser(ReqBean reqBean, RespBean<User> respBean) {

		User user = (User) reqBean.getVoInstance();

		Connection conn = null;
		TransMgr tm = null;
		try {

			if (isExists(user)) {
				respBean.setFlag(Const.ERR);
				respBean.setRespMsg(Const.RegResult.USERNAMEERR);
				return;
			}

			conn = Jdbc.getConnection();
			tm = Jdbc.getTransMgr(conn);
			tm.beginTransaction();

			int i = update(conn, "insert into t_user(username,password) values (?,?)",
					user.getUsername(), user.getPassword());
			if (i > 0) {
				Object[] id = query(conn, "select @@identity");

				i = update(conn, "update t_user set userid = ? where id=?",
						Utils.getFullId(id[0]), id[0]);
				if (i > 0) {
					tm.commitAndClose();
					respBean.setFlag(Const.SUC);
					respBean.setRespMsg(Const.RegResult.SUC);
					return;
				}
			}
		} catch (SQLException e) {
			tm.rollbackAndClose();
			respBean.setFlag(Const.ERR);
			respBean.setRespMsg(Const.RegResult.ERR);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				Jdbc.close(conn);
			}
		}

	}

	protected boolean isExists(User user) {

		boolean flag = false;

		Object obj = read(User.class, "select * from t_user where username =?", user.getUsername());

		if (obj != null) {
			flag = true;
		}
		return flag;
	}

	public RespBean<User> login(ReqBean reqBean) {

		RespBean<User> respBean = new RespBean<User>();

		User user = (User) reqBean.getVoInstance();

		if (user == null) {
			respBean.setMapping("/user/login.html");
			return respBean;
		}

		String redirect = null;
		if (!Utils.isEmpty(reqBean.getRequest().getParameter(Const.URLREDIRECT))) {
			redirect = reqBean.getRequest().getParameter(Const.URLREDIRECT);
		}

		respBean.setMapping(redirect == null ? reqBean.errPage() : redirect);
		respBean.setMappingType(Const.MappingType.AJAX);

		user = read(User.class, "select * from t_user where username =? and password = ?",
				user.getUsername(), user.getPassword());
		if (user != null) {
			respBean.setSuc();
			respBean.setRespMsg(Const.LoginResult.SUC);
			reqBean.getRequest().getSession().setAttribute(Const.SESSIONATTR, user);
		} else {
			respBean.setErr();
			respBean.setRespMsg(Const.LoginResult.ERR);
		}
		return respBean;
	}

	public RespBean<User> logout(ReqBean reqBean) {

		RespBean<User> respBean = new RespBean<User>();
		reqBean.getRequest().getSession().setAttribute(Const.SESSIONATTR, null);
		respBean.setMappingType(Const.MappingType.REDIRECT);
		respBean.setMapping(Const.ERR_PAGE);

		return respBean;
	}
}
