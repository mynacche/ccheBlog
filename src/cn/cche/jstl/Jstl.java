/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.jstl;

import java.io.UnsupportedEncodingException;

/**
 * @author chexingyou
 * @date 2013-5-22
 */
public class Jstl {

	public static String byteToString(byte[] arr) {

		if (arr == null || arr.length == 0)
			return null;
		String str = null;
		try {
			str = new String(arr, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

}
