/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author chexingyou
 * @date 2013-6-6
 */
public class Proxy {

	private Object obj;
	private Method method;
	private Object[] params;

	public Proxy() {

	}

	public Proxy(Object obj, Method method, Object[] params) {

		this.obj = obj;
		this.method = method;
		this.params = params;

	}

	public Object invoke() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		System.out.println("proxy working");

		Object ret = method.invoke(obj, params);

		return ret;
	}
}
