/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.cache;

import java.io.Serializable;

import cn.cche.util.Utils;
import cn.cche.util.Const;

/**
 * @author chexingyou
 * @date   2013-5-23
 */
public class CacheValue implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Object obj;
	private long time;
	private long priod;
	
	public CacheValue(){
		
	}
	
	public CacheValue(Object obj, long time) {

		this.obj = obj;
		this.time = time;
		this.priod = Const.CachePriod.normal;
	}
	
	public CacheValue(Object obj, long time,long priod) {

		this.obj = obj;
		this.time = time;
		this.priod = priod;
	}
	
	public Object getObj() {
	
		return obj;
	}
	public void setObj(Object obj) {
	
		this.obj = obj;
	}
	public long getTime() {
	
		return time;
	}
	public void setTime(long time) {
	
		this.time = time;
	}
	
	public long getPriod() {
	
		return priod;
	}

	public void setPriod(long priod) {
	
		this.priod = priod;
	}

	public String toString(){
		return Utils.toString(this);
	}
	
}
