package org.think2framework.context.cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.think2framework.common.exception.NonExistException;
import org.think2framework.orm.core.ClassUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoubin on 16/8/15. 抽象缓存
 */
public abstract class AbstractCache implements Cache {

	protected static final Logger logger = LogManager.getLogger(AbstractCache.class);

	protected String name; // 缓存名称

	protected Class<?> clazz; // 缓存对应的类

	protected String entity; // 缓存实体,model类型对应的model名称,json,xml为对应文件的路径和名称

	protected Integer valid; // 有效时间,单位秒,0表示永不失效

	protected long time;// 最后更新时间

	protected List<Map<String, Object>> maps; // map缓存数据

	protected List list; // 对象缓存数据

	public AbstractCache(String name, String entity, Integer valid) {
		this.name = name;
		this.entity = entity;
		this.valid = valid;
		this.clazz = null;
		this.maps = new ArrayList<>();
		this.list = new ArrayList<>();
	}

	/**
	 * 将缓存的map数据转换为list对象数据
	 * 
	 * @param elementType
	 *            类
	 */
	protected <T> void mapsToList(Class<T> elementType) {
		list = new ArrayList<>();
		for (Map<String, Object> map : maps) {
			T object = ClassUtils.createInstance(elementType, map);
			list.add(object);
		}
		clazz = elementType; // 将最后一次缓存的类设置为最新的类
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void clear() {
		maps.clear();
		list.clear();
	}

	@Override
	public List<Map<String, Object>> getMaps() {
		refreshData();
		return maps;
	}

	@Override
	public Map<String, Object> getMap(String key, Object keyValue) {
		List<Map<String, Object>> list = getList(null);
		Map<String, Object> result = null;
		for (Map<String, Object> map : list) {
			Object value = map.get(key);
			if (value == keyValue || keyValue.toString().equals(value.toString())) {
				result = map;
				break;
			}
		}
		if (null == result) {
			throw new NonExistException("Cache " + name + " key[" + key + "]value[" + keyValue + "]");
		}
		return result;
	}

	@Override
	public Object getValue(String key, Object keyValue, String name) {
		Map<String, Object> map = getMap(key, keyValue);
		Object value = map.get(name);
		if (null == value) {
			throw new NonExistException("Cache " + name + " key[" + key + "]value[" + keyValue + "]name[" + name + "]");
		}
		return value;
	}

	@Override
	public <T> List<T> getList(Class<T> elementType) {
		clazz = elementType;
		refreshData();
		return (List<T>) list;
	}

	@Override
	public <T> T getObject(Class<T> elementType, String key, Object keyValue) {
		clazz = elementType;
		Map<String, Object> map = getMap(key, keyValue);
		return ClassUtils.createInstance(elementType, map);
	}

}
