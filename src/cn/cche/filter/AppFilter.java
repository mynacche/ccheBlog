/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.cche.util.CommonUtil;
import cn.cche.util.Constant;
import cn.cche.web.beans.ReqBean;
import cn.cche.web.beans.RespBean;

/**
 * @author chexingyou
 * @date 2013-5-13
 */

/*
 * @WebFilter(filterName="r",urlPatterns="/*",initParams={@WebInitParam(name=
 * "encoding",value="utf-8")})
 */
public class AppFilter implements Filter {

	private String encoding = "utf-8";

	private List<String> ignoreUrls;
	
	private List<String> needLoginUrls;

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		try {
			setEncoding(req, resp);

			String url = request.getServletPath();
			printPath(request);
			for (String ignore : ignoreUrls) {
				if (url.contains(ignore)) {
					System.out.println("ignoreUrls :" + url);
					chain.doFilter(req, resp);
					return;
				}
			}
			
			ReqBean bean = new ReqBean(url);
			bean.setRequest(request);
			
			for(String need : needLoginUrls){
				if (url.contains(need)) { 
					if (!validateSession(bean)) {
						response.sendRedirect(request.getContextPath() + "/login.html?redirect=" + url);
						return;
					}
				}
			}
			
			String clsName = bean.getClazz();
			String methodName = bean.getMethod();
			Object obj = null;
			Class<?> clazz = null;

			if (clsName == null) {
				redirectErr(request, response);
				return;
			}
			clazz = Class.forName(clsName);

			if (clazz == null) {
				redirectErr(request, response);
				return;
			}
			obj = clazz.newInstance();

			if (obj != null && methodName != null) {
				Method method = clazz.getDeclaredMethod(methodName, ReqBean.class);
				if (method == null) {
					redirectErr(request, response);
					return;
				}

				instantiateVo(bean);

				RespBean<?> respBean = (RespBean<?>) method.invoke(obj, bean);
				respBean.setRequest(request);
				respBean.setResponse(response);
				redirect(respBean);
				return;
			} else {
				redirectErr(request, response);
				return;
			}

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| InstantiationException | IOException | ServletException e) {
			e.printStackTrace();
			redirectErr(request, response);
		}
	}

	public boolean validateSession(ReqBean bean) {

		boolean flag = true;

		HttpSession session = bean.getRequest().getSession();
		if (session.getAttribute(Constant.SESSIONATTR) == null) {
			flag = false;
		}

		return flag;
	}

	public void instantiateVo(ReqBean bean) {

		String vo = bean.getVo();
		if (CommonUtil.isEmpty(vo)) {
			return;
		}
		HttpServletRequest request = null;
		try {
			Class<?> clazz = Class.forName(vo);
			Field[] fields = clazz.getDeclaredFields();
			request = bean.getRequest();
			Map<String, String[]> params = request.getParameterMap();
			for (Entry<String, String[]> en : params.entrySet()) {
				System.out.println(en.getKey() + "/" + en.getValue()[0]);
			}
			Object obj = null;

			obj = clazz.newInstance();
			for (Field field : fields) {
				field.setAccessible(true);
				if (params.get(field.getName()) == null) {
					if (Constant.IDENTITY.equals(field.getName())) {
						// field.set(obj, CommonUtil.UUID());
					}
				} else {
					field.set(obj, params.get(field.getName())[0]);
				}
			}
			System.out.println(obj);
			bean.setVoInstance(obj);

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void setEncoding(ServletRequest req, ServletResponse resp)
			throws UnsupportedEncodingException {

		req.setCharacterEncoding(encoding);
		resp.setCharacterEncoding(encoding);
		resp.setContentType("text/html; charset=" + encoding);
	}

	public void redirectErr(HttpServletRequest request, HttpServletResponse response) {

		try {
			request.getRequestDispatcher(Constant.ERR_PAGE).forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	public void redirect(RespBean<?> bean) throws ServletException, IOException {

		HttpServletRequest request = bean.getRequest();
		HttpServletResponse response = bean.getResponse();

		if (Constant.ERR.equals(bean.getFlag())) {
			request.getRequestDispatcher(Constant.ERR_PAGE).forward(request, response);
			return;
		}
		
		if (Constant.MappingType.FORWARD.equals(bean.getMappingType())) {
			request.setAttribute(Constant.RESPLIST, bean.getDataList());
			request.setAttribute(Constant.RESPMAP, bean.getDataMap());
			request.getRequestDispatcher(bean.getMapping()).forward(request, response);
		} else if (Constant.MappingType.REDIRECT.equals(bean.getMappingType())) {
			response.sendRedirect(request.getContextPath() + bean.getMapping());
		} else if (Constant.MappingType.AJAX.equals(bean.getMappingType())) {
			PrintWriter out = response.getWriter();
			System.out.println("ajax: " + bean.toJsonStr());
			out.println(bean.toJsonStr());
		}

	}

	public void init(FilterConfig config) throws ServletException {

		if (config.getInitParameter("encoding") != null) {
			encoding = config.getInitParameter("encoding");
		}
		if (config.getInitParameter("ignoreUrls") != null) {
			ignoreUrls = Arrays.asList(config.getInitParameter("ignoreUrls").split(","));
		}
		if (config.getInitParameter("needLoginUrls") != null) {
			needLoginUrls = Arrays.asList(config.getInitParameter("needLoginUrls").split(","));
		}
	}

	public void printPath(HttpServletRequest request) {

		System.out.println("getRequestURL: " + request.getRequestURL().toString());
		System.out.println("getQueryString: " + request.getQueryString());
		System.out.println("getRequestURI: " + request.getRequestURI());
		System.out.println("getContextPath: " + request.getContextPath());
		System.out.println("getServletPath: " + request.getServletPath());
		System.out.println("getServletContext: " + request.getServletContext().getContextPath());
	}

}
