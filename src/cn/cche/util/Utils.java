/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.util;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * @author chexingyou
 * @date 2013-5-15
 */
public class Utils {

	public static String UUID() {

		java.util.UUID uuid = java.util.UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

	public static boolean isEmpty(String str) {

		return str == null || "".equals(str.trim());
	}

	public static String getFullId(Object id) {

		return (new Random().nextInt(999) + "000").substring(0, 3) + id;
	}

	public static String toString(Object obj) {

		if(obj == null) 
			return "null";
		
		StringBuffer sb = new StringBuffer();

		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		
		sb.append(clazz.getName() + "{");
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				sb.append("\n  " + field.getName() + ":" + field.get(obj));
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		sb.append("\n}");
		
		return sb.toString();
	}
	
}
