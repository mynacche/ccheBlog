/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.vo;

import cn.cche.util.Utils;

/**
 * @author chexingyou
 * @date 2013-5-14
 */
public class User extends ID{

	private String userid;
	private String username;
	private String password;


	public String getUserid() {
	
		return userid;
	}

	public void setUserid(String userid) {
	
		this.userid = userid;
	}

	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public String getPassword() {

		return password;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public String toString(){
		
		return Utils.toString(this);
	}
}
