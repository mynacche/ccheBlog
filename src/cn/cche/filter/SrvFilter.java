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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.cche.proxy.Proxy;
import cn.cche.util.Const;
import cn.cche.util.Utils;
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
public class SrvFilter implements Filter {

	private String encoding = "utf-8";

	private List<String> ignoreUrls;

	private List<String> needLoginUrls;

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		/*
		 * System.out.println("title : " + request.getParameter("title"));
		 * System.out.println("content : " + request.getParameter("content"));
		 */
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

			ReqBean bean = new ReqBean(request, url);

			for (String need : needLoginUrls) {
				if (url.contains(need)) {
					if (!validateSession(bean)) {
						response.sendRedirect(request.getContextPath() + "/user/login?redirect="
								+ bean.url());
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

				// RespBean<?> respBean = (RespBean<?>) method.invoke(obj,
				// bean);
				Proxy proxy = new Proxy(obj, method, new Object[] { bean });
				RespBean<?> respBean = (RespBean<?>) proxy.invoke();
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
		if (session.getAttribute(Const.SESSIONATTR) == null) {
			flag = false;
		}

		return flag;
	}

	public void instantiateVo(ReqBean bean) {

		String vo = bean.getVo();
		if (Utils.isEmpty(vo)) {
			return;
		}
		HttpServletRequest request = null;
		try {
			Class<?> clazz = Class.forName(vo);
			Field[] fields = clazz.getDeclaredFields();
			request = bean.getRequest();
			Map<String, String[]> params = request.getParameterMap();
			/*
			 * for (Entry<String, String[]> en : params.entrySet()) {
			 * System.out.println(en.getKey() + "/" + en.getValue()[0]); }
			 */
			Object obj = null;

			obj = clazz.newInstance();
			boolean isNull = true;
			for (Field field : fields) {
				field.setAccessible(true);
				if (params.get(field.getName()) == null) {
					if (Const.IDENTITY.equals(field.getName())) {
						// field.set(obj, CommonUtil.UUID());
					}
				} else {
					isNull = false;
					if (field.getType().equals(int.class)) {
						field.set(obj, Integer.parseInt(params.get(field.getName())[0]));
					} else if (field.getType().equals(byte[].class)) {
						field.set(obj, params.get(field.getName())[0].getBytes("utf-8"));
					} else {
						field.set(obj, params.get(field.getName())[0]);
					}
				}
			}
			if (isNull) {
				obj = null;
			}
			bean.setVoInstance(obj);

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | UnsupportedEncodingException e) {
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
			request.getRequestDispatcher(Const.ERR_PAGE).forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	public void redirect(RespBean<?> bean) throws ServletException, IOException {

		HttpServletRequest request = bean.getRequest();
		HttpServletResponse response = bean.getResponse();

		if (Const.ERR.equals(bean.getFlag())
				&& !Const.MappingType.AJAX.equals(bean.getMappingType())) {
			request.getRequestDispatcher(Const.ERR_PAGE).forward(request, response);
			return;
		}

		if (Const.MappingType.FORWARD.equals(bean.getMappingType())) {
			request.setAttribute(Const.RESPLIST, bean.getDataList());
			request.setAttribute(Const.RESPMAP, bean.getDataMap());
			request.getRequestDispatcher(bean.getMapping()).forward(request, response);
		} else if (Const.MappingType.REDIRECT.equals(bean.getMappingType())) {
			response.sendRedirect(request.getContextPath() + bean.getMapping());
		} else if (Const.MappingType.AJAX.equals(bean.getMappingType())) {
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
