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
import cn.cche.util.Constant;
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

		/*PrintWriter out = resp.getWriter();
		String file = MainServlet.class.getResource("/cn/cche/servlet/welcome.txt").getPath();
		BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
		String a = "";
		while((a=r.readLine())!= null){
			out.println("<h1>" + a +"</h1><br/>");
			//out.println("<a href='" + req.getContextPath() + "/user/list'>用户列表</a>");
		}
		*/
	
		RespBean<?> bean = ServiceFactory.getMainPageServiceImpl().get(null);
		req.setAttribute(Constant.RESPMAP, bean.getDataMap());
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
