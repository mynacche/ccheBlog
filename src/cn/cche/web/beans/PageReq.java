/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.web.beans;

import cn.cche.util.Utils;

/**
 * @author chexingyou
 * @date 2013-5-21
 */
public class PageReq {

	private int page = 0;
	private int pageSize = 10;
	private int topN = 0;

	public PageReq() {

	}

	public PageReq(int topN) {

		this.topN = topN;
	}

	public int getPage() {

		return page;
	}

	public void setPage(int page) {

		this.page = page;
	}

	public int getPageSize() {

		return pageSize;
	}

	public void setPageSize(int pageSize) {

		this.pageSize = pageSize;
	}

	public int getTopN() {

		return topN;
	}

	public void setTopN(int topN) {

		this.topN = topN;
	}

	public String toString() {

		return Utils.toString(this);
	}

	public String buildSql(String sql) {
		
		if(pageSize <= 0)
			pageSize = 10;
		// topN优先
		StringBuffer sb = new StringBuffer(sql);

		if (page != 0 || topN != 0) {

			if (topN != 0) {
				sb.append(" limit " + topN);
			} else {
				sb.append(" limit " + (page - 1) * pageSize + "," + pageSize);
			}
		}
		
		return sb.toString();
	}
}
