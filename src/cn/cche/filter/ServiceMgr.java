/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.filter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import cn.cche.dbutils.Jdbc;

/**
 * @author chexingyou
 * @date 2013-5-14
 */
public class ServiceMgr {

	public static Map<String, String> services;
	public static Map<String,String> vos;

	static {
		init();
	}

	private static void init() {

		Properties properties = new Properties();

		try {
			properties.load(new FileInputStream(Jdbc.class.getResource("/").getPath()
					+ "service.properties"));

			vos = new HashMap<String, String>();
			services = new HashMap<String, String>();

			for (Entry<Object, Object> entry : properties.entrySet()) {
				
				vos.put((String) entry.getKey(), (String) entry.getValue().toString().split("@")[0]);
				services.put((String) entry.getKey(), (String) entry.getValue().toString().split("@")[1]);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
