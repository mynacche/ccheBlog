/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.util;

/**
 * @author chexingyou
 * @date 2013-5-14
 */
public class Const {

	public static final String IDENTITY = "id";

	public static final String SUC = "0";
	public static final String ERR = "-1";
	public static final String ERR_PAGE = "/index";

	public static final String RESPLIST = "dataList";
	public static final String RESPMAP = "dataMap";

	public interface MappingType {
		public static final String FORWARD = "0";
		public static final String REDIRECT = "1";
		public static final String AJAX = "2";
	}

	public interface RegResult {
		public static final String SUC = "注册成功";
		public static final String USERNAMEERR = "用户名已存在";
		public static final String ERR = "注册失败";
	}

	public interface BlogResult {
		public static final String SUC = "发布成功";
		public static final String ERR = "发布失败";
	}

	public interface LoginResult {
		public static final String SUC = "登录成功";
		public static final String ERR = "用户名或密码错误";
	}

	public interface CachePriod {
		public static final int normal = 1 * 60;
		public static final int bloglist = 2 * 60;
		public static final int blogdetail = 5 * 60;
		public static final int author = 5 * 60;
	}

	public static final String SESSIONATTR = "loginUser";
	public static final String URLREDIRECT = "redirect";
	public static final String URLLOGIN = "user/login";

}
