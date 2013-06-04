/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.vo;

import java.sql.Timestamp;

import cn.cche.util.Utils;

/**
 * @author chexingyou
 * @date 2013-5-14
 */
public class Blog extends ID{

	private String blogId;
	private String title;
	private byte[] content;
	private String authorId;
	private User author;
	private Timestamp createtime;
	private Timestamp updatetime;

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	
	public byte[] getContent() {
	
		return content;
	}

	public void setContent(byte[] content) {
	
		this.content = content;
	}

	public String getBlogId() {

		return blogId;
	}

	public void setBlogId(String blogId) {

		this.blogId = blogId;
	}

	public String getAuthorId() {

		return authorId;
	}

	public void setAuthorId(String authorId) {

		this.authorId = authorId;
	}

	public User getAuthor() {

		return author;
	}

	public void setAuthor(User author) {

		this.author = author;
	}

	public Timestamp getCreatetime() {

		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {

		this.createtime = createtime;
	}
	
	public Timestamp getUpdatetime() {
	
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
	
		this.updatetime = updatetime;
	}

	public String toString(){
		
		return Utils.toString(this);
	}
	
}
