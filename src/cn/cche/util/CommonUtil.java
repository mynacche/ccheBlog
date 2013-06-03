/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.util;

/**
 * @author chexingyou
 * @date 2013-5-15
 */
public class CommonUtil {

	public static String UUID() {

		java.util.UUID uuid = java.util.UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

	public static boolean isEmpty(String str) {

		return str == null || "".equals(str.trim());
	}

}
