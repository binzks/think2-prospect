package org.think2framework.context.cache;

import java.util.List;
import java.util.Map;

/**
 * Created by zhoubin on 16/8/15. 缓存数据接口
 */
public interface Cache {

	/**
	 * 获取缓存名称
	 * 
	 * @return 缓存名称
	 */
	String getName();

	/**
	 * 清除所有缓存数据
	 */
	void clear();

	/**
	 * 刷新数据,如果数据已经过期则重新获取缓存,如果没有过期则不处理
	 */
	void refreshData();

	/**
	 * 获取整个缓存的list数据
	 * 
	 * @return 所有缓存数据map数组
	 */
	List<Map<String, Object>> getMaps();

	/**
	 * 获取一个缓存的一条map数据
	 * 
	 * @param key
	 *            数据的取值字段名称
	 * @param keyValue
	 *            数据的取值字段值
	 * @return key值为keyValue的数据的map数据
	 */
	Map<String, Object> getMap(String key, Object keyValue);

	/**
	 * 获取一个缓存值,key值为keyValue的数据的map数据,map的name数据
	 * 
	 * @param key
	 *            数据的取值字段名称
	 * @param keyValue
	 *            数据的取值字段值
	 * @param name
	 *            数据中实际获取的数据的字段值
	 * @return key值为keyValue的数据的map数据,map的name数据
	 */
	Object getValue(String key, Object keyValue, String name);

	/**
	 * 获取整个缓存的list数据
	 *
	 * @return 所有缓存数据对象数组
	 */
	<T> List<T> getList(Class<T> elementType);

	/**
	 * 获取单个对象缓存数据
	 *
	 * @return 单个对象缓存
	 */
	<T> T getObject(Class<T> elementType, String key, Object keyValue);

}
