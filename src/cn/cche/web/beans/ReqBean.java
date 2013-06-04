/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.web.beans;

import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.cche.filter.ServiceMgr;
import cn.cche.util.Utils;
import cn.cche.util.Const;
import cn.cche.vo.ID;
import cn.cche.vo.User;

/**
 * @author chexingyou
 * @date 2013-5-13
 */
public class ReqBean {

	private HttpServletRequest request;
	private String url;
	private String clazz;
	private String method;
	private Object[] params;
	private String vo;
	private Object voInstance;

	private PageReq pageReq = null;

	public ReqBean() {

	}

	public ReqBean(String url) {

		this.url = url;
		parseUrl(url);
		System.out.println(toString());
	}

	public ReqBean(HttpServletRequest request, String url) {

		this.request = request;
		this.url = url;

		parseUrl(url);
		parseRequest(request);

		System.out.println(toString());
	}

	public void parseUrl(String url) {

		if (url.startsWith("/")) {
			url = url.substring(1);
		}
		Object[] arr = url.split("/");
		if (arr.length > 0) {
			this.clazz = ServiceMgr.services.get((String) arr[0]);
			this.vo = ServiceMgr.vos.get((String) arr[0]);
		}
		if (arr.length > 1) {
			this.method = (String) arr[1];
		}
		if (arr.length > 2) {
			this.params = new Object[arr.length - 2];
			System.arraycopy(arr, 2, this.params, 0, params.length);
		}
	}

	public void parseRequest(HttpServletRequest request) {

		pageReq = new PageReq();

		Map<String, String[]> params = request.getParameterMap();
		Field[] fields = this.pageReq.getClass().getDeclaredFields();
		try {
			for (Field field : fields) {

				field.setAccessible(true);
				if (params.get(field.getName()) != null) {

					if (field.getType().equals(int.class)) {
						field.set(this.pageReq, Integer.parseInt(params.get(field.getName())[0]));
					} else {
						field.set(this.pageReq, params.get(field.getName())[0]);
					}

				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public String errPage() {

		return request.getContextPath() + Const.ERR_PAGE;
	}

	public String url() {

		if (request.getQueryString() != null) {
			return request.getRequestURL() + "?" + request.getQueryString();
		} else {
			return request.getRequestURL().toString();
		}
	}

	public String uri() {

		return request.getRequestURI();
	}

	public String ctxPath() {

		return request.getContextPath();
	}

	public String getId() {

		if (params == null)
			return null;

		if (params[0] == null)
			return null;

		if (params[0].toString().length() > 3) {
			return params[0].toString().substring(3);
		}
		return null;
	}

	public ID getID() {

		if (params == null)
			return null;

		if (params[0] == null)
			return null;

		if (params[0].toString().length() > 3) {
			return new ID(params[0].toString().substring(3));
		}
		return null;
	}
	
	public User getLoginUser(){
		
		return (User)request.getSession().getAttribute(Const.SESSIONATTR);
		
	}
	
	public HttpServletRequest getRequest() {

		return request;
	}

	public void setRequest(HttpServletRequest request) {

		this.request = request;
	}

	public String getUrl() {

		return url;
	}

	public void setUrl(String url) {

		this.url = url;
	}

	public String getClazz() {

		return clazz;
	}

	public void setClazz(String clazz) {

		this.clazz = clazz;
	}

	public String getMethod() {

		return method;
	}

	public void setMethod(String method) {

		this.method = method;
	}

	public Object[] getParams() {

		return params;
	}

	public void setParams(Object[] params) {

		this.params = params;
	}

	public String getVo() {

		return vo;
	}

	public void setVo(String vo) {

		this.vo = vo;
	}

	public Object getVoInstance() {

		return voInstance;
	}

	public void setVoInstance(Object voInstance) {

		this.voInstance = voInstance;
	}

	public PageReq getPageReq() {

		return pageReq;
	}

	public void setPageReq(PageReq pageReq) {

		this.pageReq = pageReq;
	}

	public String toString() {

		return Utils.toString(this);
	}

}
