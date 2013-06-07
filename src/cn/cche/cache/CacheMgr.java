/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import net.sourceforge.sizeof.SizeOf;

/**
 * @author chexingyou
 * @date 2013-5-23
 */
public class CacheMgr {

	private static Map<Serializable, Map<Serializable, CacheValue>> cache = new HashMap<Serializable, Map<Serializable, CacheValue>>();

	private CacheMgr() {

	}

	static {

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {

				//System.out.println("定时器工作中...");
				/*
				 * for (Entry<Serializable, Map<Serializable, CacheValue>> entry
				 * : cache.entrySet()) { Map<Serializable, CacheValue>
				 * regionCache = entry.getValue(); for(Entry<Serializable,
				 * CacheValue> en :regionCache.entrySet()){ CacheValue value =
				 * en.getValue(); if ((System.currentTimeMillis() -
				 * value.getTime()) / 1000 > value.getPriod()) {
				 * evict(entry.getKey(), en.getKey()); } }
				 * 
				 * }
				 */

				Iterator<Serializable> it = cache.keySet().iterator();
				while (it.hasNext()) {
					Serializable regionkey = it.next();
					Map<Serializable, CacheValue> regionCache = cache.get(regionkey);
					Iterator<Serializable> regionit = regionCache.keySet().iterator();
					while (regionit.hasNext()) {
						Serializable key = regionit.next();
						CacheValue value = regionCache.get(key);
						if ((System.currentTimeMillis() - value.getTime()) / 1000 > value
								.getPriod()) {
							// evict(regionkey,key);
							System.out.println("清除缓存: " + regionkey + "/" + key);
							regionit.remove();

						}
					}
				}

			}
		};
		timer.schedule(task, 0, 5000);

	}

	public static Object get(Serializable region, Serializable key) {

		// sizeOf();
		Map<Serializable, CacheValue> regionCache = cache.get(region);
		CacheValue value = null;
		if (region != null && key != null) {
			regionCache = cache.get(region);
			if (regionCache == null)
				return null;
			value = regionCache.get(key);
			if (value != null) {
				if ((System.currentTimeMillis() - value.getTime()) / 1000 > value.getPriod()) {
					evict(region, key);
					value = null;
				} else {
					System.out.println("获取缓存: " + region + "/" + key);
				}
			}
		}

		return value == null ? null : value.getObj();
	}

	public static void put(Serializable region, Serializable key, CacheValue value) {

		if (key != null && value != null) {

			Map<Serializable, CacheValue> map = new HashMap<Serializable, CacheValue>();
			map.put(key, value);

			Map<Serializable, CacheValue> regionCache = cache.get(region);
			if (regionCache == null) {
				cache.put(region, map);
			} else {
				regionCache.putAll(map);
			}

			System.out.println("更新缓存: " + region + "/" + key);
		}
	}

	public static void evict(Object region, Object key) {

		if (region != null && key != null) {
			Map<Serializable, CacheValue> regionCache = cache.get(region);
			if (regionCache != null)
				cache.get(region).remove(key);
			System.out.println("清除缓存: " + region + "/" + key);
		}
	}

	public static void sizeOf() {

		String size = SizeOf.humanReadable(SizeOf.deepSizeOf(cache));
		System.out.println("缓存大小:" + size);
	}

}
