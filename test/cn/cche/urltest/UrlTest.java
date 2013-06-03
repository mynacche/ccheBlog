/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.urltest;

import org.junit.Test;

import cn.cche.web.beans.ReqBean;

/**
 * @author chexingyou
 * @date   2013-5-14
 */
public class UrlTest {

	@Test
	public void test(){
		
		ReqBean bean = new ReqBean("/user/del/admin/1/f");
		System.out.println(bean.toString());
		
	}
	@Test
	public void test1(){
		
		ReqBean bean = new ReqBean("/user/");
		System.out.println(bean.toString());
		
	}
	@Test
	public void test2(){
		
		ReqBean bean = new ReqBean("/user/del");
		System.out.println(bean.toString());
		
	}
}
