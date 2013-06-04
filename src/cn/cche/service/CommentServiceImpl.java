/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.cche.cache.CacheMgr;
import cn.cche.cache.CacheValue;
import cn.cche.dbutils.Jdbc;
import cn.cche.dbutils.TransMgr;
import cn.cche.util.Const;
import cn.cche.vo.Blog;
import cn.cche.vo.Comment;
import cn.cche.vo.ID;
import cn.cche.vo.User;
import cn.cche.web.beans.ReqBean;
import cn.cche.web.beans.RespBean;

/**
 * @author chexingyou
 * @date 2013-5-20
 */
public class CommentServiceImpl extends BaseDAO {

	public RespBean<Blog> add(ReqBean reqBean) {

		RespBean<Blog> respBean = new RespBean<Blog>();

		Comment comment = (Comment) reqBean.getVoInstance();
		respBean.setMappingType(Const.MappingType.AJAX);

		Connection conn = null;
		TransMgr tm = null;
		try {

			conn = Jdbc.getConnection();
			tm = Jdbc.getTransMgr(conn);
			tm.beginTransaction();

			User loginUser = reqBean.getLoginUser();
			int i = update(conn,
					"insert into t_comment(blogid,userid,content,createtime) values (?,?,?,?)",
					comment.getBlogId(), loginUser.getId(), comment.getContent(),new java.util.Date());
			if (i > 0) {
				if (i > 0) {
					tm.commitAndClose();
					respBean.setFlag(Const.SUC);
					respBean.setRespMsg(Const.BlogResult.SUC);
					respBean.setMapping(reqBean.ctxPath() + "/blog/show/" + comment.getBlogId());
				}
			}
		} catch (SQLException e) {
			tm.rollbackAndClose();
			respBean.setFlag(Const.ERR);
			respBean.setRespMsg(Const.BlogResult.ERR);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				Jdbc.close(conn);
			}
		}

		return respBean;
	}

	public RespBean<Blog> edit(ReqBean reqBean) {

		RespBean<Blog> respBean = new RespBean<Blog>();

		Blog blog = (Blog) reqBean.getVoInstance();

		if (blog == null) {
			reqBean.getRequest().setAttribute("blog", loadBean(Blog.class,reqBean.getID()));
			respBean.setMapping("/blog/blogedit.jsp");
			return respBean;
		}

		respBean.setMappingType(Const.MappingType.AJAX);

		Connection conn = null;
		TransMgr tm = null;
		try {

			conn = Jdbc.getConnection();
			tm = Jdbc.getTransMgr(conn);
			tm.beginTransaction();

			int i = update(
					conn,
					"update t_blog set title=?,content=?,updatetime=? where blogid = ? ",
					blog.getTitle(), blog.getContent(), new java.util.Date(), blog.getBlogId());
			if (i > 0) {
				tm.commitAndClose();
				
				Blog obj = (Blog) CacheMgr.get("blog",blog.getBlogId().substring(3));
				if (obj != null) {
					blog = read(Blog.class, "select * from t_blog where blogid = ?", blog.getBlogId());
					
					CacheMgr.put("blog", blog.getBlogId().substring(3),
							new CacheValue(blog, System.currentTimeMillis()));
				}

				respBean.setFlag(Const.SUC);
				respBean.setRespMsg(Const.BlogResult.SUC);
				respBean.setMapping(reqBean.ctxPath() + "/blog/show/" + blog.getBlogId());
			}
		} catch (SQLException e) {
			tm.rollbackAndClose();
			respBean.setFlag(Const.ERR);
			respBean.setRespMsg(Const.BlogResult.ERR);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				Jdbc.close(conn);
			}
		}

		return respBean;
	}

	public RespBean<Blog> list(ReqBean reqBean) {

		RespBean<Blog> respBean = new RespBean<Blog>();

		String sql = "select id from t_blog order by createtime desc";

		sql = reqBean.getPageReq().buildSql(sql);

		List<ID> ids = query_cache(ID.class, null, null, sql);

		List<Blog> list = loadList(Blog.class, ids, "order by createtime desc");

		for (Blog blog : list) {

			blog.setAuthor(getAuthor(blog.getAuthorId()));
		}

		respBean.setMapping("/blog/bloglist.jsp");
		respBean.setDataList(list);

		return respBean;
	}

	public RespBean<Blog> show(ReqBean reqBean) {

		RespBean<Blog> bean = new RespBean<Blog>();
		String id = reqBean.getId();

		if (id == null) {
			bean.setMapping(Const.ERR_PAGE);
			return bean;
		}

		final Blog blog = read_cache(Blog.class, "blog", id, "select * from t_blog where id=?", id);
		if (blog != null) {
			blog.setAuthor(getAuthor(blog.getAuthorId()));
		}
		List<Blog> list = new ArrayList<Blog>() {
			private static final long serialVersionUID = 1L;
			{
				add(blog);
			}
		};
		bean.setDataList(list);
		bean.setMapping("/blog/blogdetail.jsp");

		return bean;
	}

	public User getAuthor(String userid) {

		return read_cache(User.class, "user", userid, "select * from t_user where id =?", userid);
		// return loadBean(User.class, new ID(userid));
	}

}
