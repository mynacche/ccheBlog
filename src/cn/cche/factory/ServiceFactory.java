/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.factory;

import cn.cche.service.BlogServiceImpl;
import cn.cche.service.MainPageServiceImpl;
import cn.cche.service.UserServiceImpl;

/**
 * @author chexingyou
 * @date 2013-5-14
 */
public class ServiceFactory {

	public static UserServiceImpl getUserServiceImpl() {

		return new UserServiceImpl();
	}

	public static BlogServiceImpl getBlogServiceImpl() {

		return new BlogServiceImpl();
	}
	
	public static MainPageServiceImpl getMainPageServiceImpl() {

		return new MainPageServiceImpl();
	}
}
