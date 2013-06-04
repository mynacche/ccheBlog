/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cche.factory.ServiceFactory;
import cn.cche.util.Const;
import cn.cche.web.beans.ReqBean;
import cn.cche.web.beans.RespBean;

/**
 * @author chexingyou
 * @date 2013-5-13
 */
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = -8779684180525681767L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {

		RespBean<?> bean = ServiceFactory.getMainPageServiceImpl().get(new ReqBean());
		req.setAttribute(Const.RESPMAP, bean.getDataMap());
		req.getRequestDispatcher(bean.getMapping()).forward(req, resp);
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doGet(req, resp);
	}

	public void destroy() {

		super.destroy();
	}

	public void init() throws ServletException {

		super.init();
	}

}
