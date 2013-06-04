/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.vo;

import java.sql.Timestamp;

/**
 * @author chexingyou
 * @date   2013-5-28
 */
public class Comment extends ID{

	private int blogId;
	private int userId;
	private byte[] content;
	private Timestamp createtime;
	public int getBlogId() {
	
		return blogId;
	}
	public void setBlogId(int blogId) {
	
		this.blogId = blogId;
	}
	public int getUserId() {
	
		return userId;
	}
	public void setUserId(int userId) {
	
		this.userId = userId;
	}
	public byte[] getContent() {
	
		return content;
	}
	public void setContent(byte[] content) {
	
		this.content = content;
	}
	public Timestamp getCreatetime() {
	
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
	
		this.createtime = createtime;
	}
	
	
	
}
