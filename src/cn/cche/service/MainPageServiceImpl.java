/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.service;

import java.util.List;
import java.util.Map;

import cn.cche.factory.ServiceFactory;
import cn.cche.vo.Blog;
import cn.cche.web.beans.PageReq;
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
		
		reqBean.setPageReq(new PageReq(10));
		
		List<Blog> blogList = ServiceFactory.getBlogServiceImpl().list(reqBean).getDataList();
		
		dataMap.put("blogList", blogList);
		
		respBean.setMapping("/index.jsp");
		
		return respBean;
	}

}
