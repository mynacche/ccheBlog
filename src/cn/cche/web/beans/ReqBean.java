/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.web.beans;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import cn.cche.filter.ServiceMgr;

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
	
	private String mapping;

	
	public ReqBean() {

	}

	public ReqBean(String url) {

		this.url = url;
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
		
		System.out.println(toString());
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

	public String getMapping() {

		return mapping;
	}

	public void setMapping(String mapping) {

		this.mapping = mapping;
	}

	public String toString() {

		return "{url: " + url + "\n" + " clazz: " + clazz + "\n" + " method: " + method
				+ "\n" + " params: " + Arrays.toString(params) + "\n" + "}";

	}

}
