/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.resptest;

import org.junit.Test;

import cn.cche.factory.ServiceFactory;
import cn.cche.vo.User;
import cn.cche.web.beans.ReqBean;
import cn.cche.web.beans.RespBean;

/**
 * @author chexingyou
 * @date 2013-5-14
 */
public class RespTest {

	@Test
	public void test() {

		ReqBean reqBean = new ReqBean();
		RespBean<User> bean = ServiceFactory.getUserServiceImpl().list(reqBean);

		for (User user : bean.getDataList()) {
			System.out.println(user.toString());
		}
	}

}
