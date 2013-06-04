/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.utiltest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.sizeof.SizeOf;

import org.junit.Test;

import cn.cche.util.Utils;
import cn.cche.web.beans.PageReq;
import cn.cche.web.beans.RespBean;

/**
 * @author chexingyou
 * @date 2013-5-15
 */
public class UtilTest {

	@Test
	public void test() {

		System.out.println(Utils.UUID());
	}

	@Test
	public void test1() {

		System.out.println("abcdefg".contains("ab"));
		System.out.println("abcdefg".contains("aa"));
	}

	@Test
	public void test2() {

		// RespBean<?> bean = new RespBean<?>(); //err
		// List<?> list = new ArrayList<?>();//err
		Map<String, List<?>> map = new HashMap<String, List<?>>(); // ok
		System.out.println(map);
	}

	@Test
	public void test3() {

		System.out.println(Arrays.toString("@123".split("@")));
	}

	@Test
	public void test4() {

		RespBean<Class<?>> respBean = new RespBean<Class<?>>();
		Map<String, List<?>> dataMap = respBean.getDataMap();
		System.out.println(respBean.getDataMap() + "/" + dataMap);
		dataMap.put("key", new ArrayList<Class<?>>());
		System.out.println(respBean.getDataMap() + "/" + dataMap);
	}

	@Test
	public void test5() {

		PageReq page = new PageReq();
		page.setPage(2);
		page.setPageSize(3);
		System.out.println(page);

	}

	@Test
	public void test6() {

		System.out.println(Utils.toString(null));
		System.out.println(Utils.toString(new int[] { 1, 1, 1, 1, 1, 1, 1, }));

	}

	@Test
	public void test7() {

		System.out.println(new java.util.Date());

	}

	@Test
	public void test8() {

		Map<String, String> map = new HashMap<String, String>();
		map.put("aa", "bbb");
		map.put("bb", "bbb");
		String size = SizeOf.humanReadable(SizeOf.deepSizeOf(map));
		System.out.println("map大小:" + size);
		System.out.println(map.remove("aa"));
		System.out.println(map.remove("bb"));
		System.out.println(map.remove("bb"));
		size = SizeOf.humanReadable(SizeOf.deepSizeOf(map));
		System.out.println("map大小:" + size);
		map.clear();
		map = null;
		size = SizeOf.humanReadable(SizeOf.deepSizeOf(map));
		System.out.println("map大小:" + size);
	}

	@Test
	public void test9() {

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.println("j=" + j);
				if(j==2){
					break;
				}
			}
		}

	}
	@Test
	public void test10() {

	    String [] str = new String[]{"a"};
	    System.out.println(str[0]);
	    System.out.println(str[0].toString().substring(3));
	}
	@Test
	public void test11(){
		Map<String, String> map = new HashMap<String, String>();
		System.out.println(map.put("aa", "bbb"));
		System.out.println(map.put("aa", "bbb"));
		
		System.out.println(map.get("aa"));
		System.out.println(map.containsKey("aa"));
		
		System.out.println(map.remove("aa"));
		System.out.println(map.remove("aa"));
		
		System.out.println(map.get("aa"));
		System.out.println(map.containsKey("aa"));
	}
}
