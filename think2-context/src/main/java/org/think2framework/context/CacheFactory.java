package org.think2framework.context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.think2framework.common.exception.ExistException;
import org.think2framework.common.exception.NonExistException;
import org.think2framework.common.exception.NonsupportException;
import org.think2framework.context.cache.Cache;
import org.think2framework.context.cache.ModelCache;
import org.think2framework.context.cache.JsonFileCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoubin on 16/7/28. 缓存工厂
 */
public class CacheFactory {

	public static final String TYPE_MODEL = "model"; // 缓存类型-数据库模型

	public static final String TYPE_JSON = "json"; // 缓存类型-json文件

	private static final Logger logger = LogManager.getLogger(CacheFactory.class);

	private static Map<String, Cache> caches = new HashMap<>(); // 缓存对象

	public static synchronized void build(List<org.think2framework.context.bean.Cache> cacheList) {
		int size = caches.size();
		caches.clear();
		if (size > 0) {
			logger.debug("Clear {} caches", size);
		}
		for (org.think2framework.context.bean.Cache cache : cacheList) {
			String type = cache.getType();
			if (TYPE_MODEL.equalsIgnoreCase(type)) {
				append(new ModelCache(cache.getName(), cache.getEntity(), cache.getValid()));
			} else if (TYPE_JSON.equalsIgnoreCase(type)) {
				append(new JsonFileCache(cache.getName(), cache.getEntity(), cache.getValid()));
			} else {
				throw new NonsupportException(org.think2framework.context.bean.Cache.class.getName() + " type " + type);
			}
		}
		logger.debug(CacheFactory.class.getName() + " build success");
	}

	/**
	 * 追加一个缓存
	 *
	 * @param cacheData
	 *            缓存配置
	 */
	public static synchronized void append(Cache cacheData) {
		String name = cacheData.getName();
		if (null != caches.get(name)) {
			throw new ExistException(org.think2framework.context.bean.Cache.class.getName() + " " + name);
		}
		caches.put(name, cacheData);
		logger.debug("Append " + org.think2framework.context.bean.Cache.class.getName() + " " + name);
	}

	/**
	 * 清除所有缓存数据
	 */
	public static synchronized void clearAll() {
		caches.values().forEach(Cache::clear);
	}

	/**
	 * 刷新所有缓存数据
	 */
	public static synchronized void refreshAll() {
		caches.values().forEach(Cache::refreshData);
	}

	/**
	 * 根据名称获取一个缓存
	 * 
	 * @param name
	 *            缓存名称
	 * @return 缓存
	 */
	public static Cache get(String name) {
		Cache cache = caches.get(name);
		if (null == cache) {
			throw new NonExistException(Cache.class.getName() + " " + name);
		}
		return cache;
	}

	/**
	 * 根据缓存名称，获取缓存的所有数据
	 * 
	 * @param name
	 *            缓存名称
	 * @param elementType
	 *            类
	 * @return 缓存的所有数据对象数组
	 */
	public static <T> List<T> getList(String name, Class<T> elementType) {
		Cache cache = get(name);
		return cache.getList(elementType);
	}

	/**
	 * 根据缓存名称，获取单个缓存对象
	 * 
	 * @param name
	 *            缓存名称
	 * @param elementType
	 *            类
	 * @param key
	 *            数据的取值字段名称
	 * @param keyValue
	 *            数据中实际获取的数据的字段值
	 * @return key值为keyValue的数据
	 */
	public static <T> T getObject(String name, Class<T> elementType, String key, Object keyValue) {
		Cache cache = get(name);
		return cache.getObject(elementType, key, keyValue);
	}

	/**
	 * 根据缓存名称,获取缓存的所有数据
	 * 
	 * @param name
	 *            缓存名称
	 * @return 缓存的所有数据map数组
	 */
	public static List<Map<String, Object>> getMaps(String name) {
		Cache cache = get(name);
		return cache.getMaps();
	}

	/**
	 * 根据缓存名称,获取缓存中某个字段值为某个值的数据
	 * 
	 * @param name
	 *            缓存名称
	 * @param key
	 *            缓存取值字段名称
	 * @param keyValue
	 *            缓存取值字段值
	 * @return 缓存的某条数据
	 */
	public static Map<String, Object> getMap(String name, String key, Object keyValue) {
		Cache cache = get(name);
		return cache.getMap(key, keyValue);
	}

	/**
	 * 获取一个缓存值,key值为keyValue的数据的map数据,map的field数据
	 *
	 * @param key
	 *            数据的取值字段名称
	 * @param keyValue
	 *            数据的取值字段值
	 * @param name
	 *            数据中实际获取的数据的字段值
	 * @return key值为keyValue的数据的map数据,map的field数据
	 */
	public static Object getValue(String name, String key, Object keyValue, String field) {
		Cache cache = get(name);
		return cache.getValue(key, keyValue, field);
	}
}
