/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.utiltest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import cn.cche.util.CommonUtil;
import cn.cche.web.beans.RespBean;

/**
 * @author chexingyou
 * @date   2013-5-15
 */
public class UtilTest {

	@Test
	public void test(){
		System.out.println(CommonUtil.UUID());
	}
	@Test
	public void test1(){
		System.out.println("abcdefg".contains("ab"));
		System.out.println("abcdefg".contains("aa"));
	}
	
	@Test
	public void test2(){
		
		//RespBean<?> bean = new RespBean<?>(); //err
		//List<?> list = new ArrayList<?>();//err
		Map<String,List<?>> map = new HashMap<String,List<?>>(); //ok
		System.out.println(map);
	}
	
	@Test
	public void test3(){

		System.out.println(Arrays.toString("@123".split("@")));
	}
	@Test
	public void test4(){
		RespBean<Class<?>> respBean = new RespBean<Class<?>>();
		Map<String,List<?>> dataMap = respBean.getDataMap();
		System.out.println( respBean.getDataMap() + "/" + dataMap);
		dataMap.put("key", new ArrayList<Class<?>>());
		System.out.println( respBean.getDataMap() + "/" + dataMap);
	}
	
}
