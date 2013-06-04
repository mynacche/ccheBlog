/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * @author chexingyou
 * @date 2013-5-14
 */
public class ServiceMgr {

	public static Map<String, String> services;
	public static Map<String, String> vos;

	static {
		init();
	}

	private static void init() {

		vos = new HashMap<String, String>();
		services = new HashMap<String, String>();
		
		Properties properties = new Properties();
		try {
			properties.load(ServiceMgr.class.getResourceAsStream("/service.properties"));
			for (Entry<Object, Object> entry : properties.entrySet()) {

				vos.put((String) entry.getKey(), (String) entry.getValue().toString().split("@")[0]);
				services.put((String) entry.getKey(),
						(String) entry.getValue().toString().split("@")[1]);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*ResourceBundle bundle = ResourceBundle.getBundle("service");
		for (String key : bundle.keySet()) {
			vos.put(key, bundle.getString(key).split("@")[0]);
			services.put(key, bundle.getString(key).split("@")[1]);
		}*/
	}
}
