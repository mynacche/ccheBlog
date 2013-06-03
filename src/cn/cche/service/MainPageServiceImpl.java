/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cche.factory.ServiceFactory;
import cn.cche.vo.User;
import cn.cche.web.beans.ReqBean;
import cn.cche.web.beans.RespBean;

/**
 * @author chexingyou
 * @date   2013-5-17
 */
public class MainPageServiceImpl {
	
	public RespBean<?> get(ReqBean reqBean){
		
		RespBean<Class<?>> respBean = new RespBean<Class<?>>();
		
		Map<String,List<?>> dataMap = respBean.getDataMap();
		
		List<User> userList = ServiceFactory.getUserServiceImpl().top(reqBean,5).getDataList();
		
		dataMap.put("userList", userList);
		
		respBean.setMapping("/index.jsp");

		return respBean;
	}

}
