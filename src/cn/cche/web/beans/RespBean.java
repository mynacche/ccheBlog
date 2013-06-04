/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.web.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cche.util.Utils;
import cn.cche.util.Const;

/**
 * @author chexingyou
 * @date 2013-5-14
 */
public class RespBean<T> {

	private HttpServletRequest request;
	private HttpServletResponse response;

	private List<T> dataList = new ArrayList<T>();

	private Map<String, List<?>> dataMap = new HashMap<String, List<?>>();

	private String respMsg;
	private String flag = "0";
	private String mapping;
	private String MappingType = "0";

	public String toJsonStr() {

		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"flag\":\"" + this.flag + "\",");
		sb.append("\"respMsg\":\"" + this.respMsg + "\",");
		sb.append("\"mapping\":\"" + this.mapping + "\",");
		sb.append("\"MappingType\":\"" + this.MappingType + "\"");
		sb.append("}");

		return sb.toString();
	}

	public void setErr() {

		setFlag(Const.ERR);
	}

	public void setSuc() {

		setFlag(Const.SUC);
	}

	public List<T> getDataList() {

		return dataList;
	}

	public void setDataList(List<T> dataList) {

		this.dataList = dataList;
	}

	public Map<String, List<?>> getDataMap() {

		return dataMap;
	}

	public void setDataMap(Map<String, List<?>> dataMap) {

		this.dataMap = dataMap;
	}

	public String getRespMsg() {

		return respMsg;
	}

	public void setRespMsg(String respMsg) {

		this.respMsg = respMsg;
	}

	public String getMapping() {

		return mapping;
	}

	public void setMapping(String mapping) {

		this.mapping = mapping;
	}

	public String getFlag() {

		return flag;
	}

	public void setFlag(String flag) {

		this.flag = flag;
	}

	public HttpServletRequest getRequest() {

		return request;
	}

	public void setRequest(HttpServletRequest request) {

		this.request = request;
	}

	public HttpServletResponse getResponse() {

		return response;
	}

	public void setResponse(HttpServletResponse response) {

		this.response = response;
	}

	public String getMappingType() {

		return MappingType;
	}

	public void setMappingType(String mappingType) {

		MappingType = mappingType;
	}

	public String toString() {

		return Utils.toString(this);
	}

}
