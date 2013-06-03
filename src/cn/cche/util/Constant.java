/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.util;

/**
 * @author chexingyou
 * @date   2013-5-14
 */
public class Constant {

	public static final String IDENTITY = "id";
	
	public static final String SUC = "0";
	public static final String ERR = "-1";
	public static final String ERR_PAGE = "/index";
	
	public static final String RESPLIST = "dataList";
	public static final String RESPMAP = "dataMap";
	
	public interface MappingType{
		public static final String FORWARD = "0";
		public static final String REDIRECT = "1";
		public static final String AJAX = "2";
	}
	
	public interface RegResult{
		public static final String SUC = "注册成功";
		public static final String USERNAMEERR = "用户名已存在";
		public static final String ERR= "注册失败";
	}
	
	public static final String SESSIONATTR = "user";
	public static final String URLREDIRECT = "redirect";
	public static final String URLLOGIN = "user/login";
}
