package org.think2framework.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.think2framework.common.JsonUtils;
import org.think2framework.common.StringUtils;
import org.think2framework.common.exception.ExistException;
import org.think2framework.common.exception.NonExistException;
import org.think2framework.context.bean.Constant;

/**
 * Created by zhoubin on 16/2/16. 常量工厂
 */
public class ConstantFactory {

	private static final Logger logger = LogManager.getLogger(ConstantFactory.class);

	private static Map<String, Constant> constants = new HashMap<>(); // 系统常量缓存

	private static Map<String, Map<String, Object>> groups = new HashMap<>(); // 系统常量组缓存

	/**
	 * 创建常量工厂,先清除原先常量和分组,再初始化
	 * 
	 * @param constantList
	 *            初始化的常量
	 */
	public static synchronized void build(List<Constant> constantList) {
		int constSize = constants.size();
		int groupSize = groups.size();
		constants.clear();
		if (constSize > 0) {
			logger.debug("Clear {} constants", constSize);
		}
		groups.clear();
		if (groupSize > 0) {
			logger.debug("Clear {} groups", groupSize);
		}
		if (null != constantList) {
			constantList.forEach(ConstantFactory::append);
		}
		logger.debug(ConstantFactory.class.getName() + " build success");
	}

	/**
	 * 获取常量的key,如果分组不为null和空,则分组名_常量名,否则常量名
	 *
	 * @param group
	 *            分组名
	 * @param name
	 *            常量名
	 * @return 常量key
	 */
	private static String getConstantKey(String group, String name) {
		String key = name;
		if (StringUtils.isNotBlank(group)) {
			key = group + "_" + name;
		}
		return key;
	}

	/**
	 * 追加常量
	 * 
	 * @param constant
	 *            常量
	 */
	public static synchronized void append(Constant constant) {
		String group = constant.getGroup();
		String key = getConstantKey(group, constant.getName());
		if (null != constants.get(key)) {
			throw new ExistException(Constant.class.getName() + " " + key);
		}
		constants.put(key, constant);
		// 如果常量存在分组名称,则将常量添加到分组
		if (StringUtils.isNotBlank(group)) {
			String value = constant.getValue();
			// 如果常量分组还不存在则创建
			Map<String, Object> map = groups.get(group);
			if (null == map) {
				map = new HashMap<>();
			}
			// 如果常量分组中已经存在待添加的常量值则抛出异常
			if (null != map.get(value)) {
				throw new ExistException(Constant.class.getName() + " " + group + "," + value);
			}
			map.put(value, constant.getDisplay());
			groups.put(group, map);

		}
		logger.debug("Append " + Constant.class.getName() + " " + JsonUtils.toString(constant));
	}

	/**
	 * 根据分组名称和常量名称获取常量值
	 *
	 * @param group
	 *            分组名称
	 * @param name
	 *            常量名称
	 * @return 常量值
	 */
	public static String getConstant(String group, String name) {
		String key = getConstantKey(group, name);
		Constant constant = constants.get(key);
		if (null == constant) {
			throw new NonExistException(Constant.class.getName() + " " + key);
		}
		return constant.getValue();
	}

	/**
	 * 根据常量名称获取常量值
	 *
	 * @param name
	 *            常量名称
	 * @return 常量值
	 */
	public static String get(String name) {
		return getConstant(null, name);
	}

	/**
	 * 根据分组名和常量名获取一个常量
	 *
	 * @param group
	 *            分组名称
	 * @param name
	 *            常量名称
	 * @return 常量值
	 */
	public static String get(String group, String name) {
		return getConstant(group, name);
	}

	/**
	 * 根据分组名称获取一个常量分组
	 *
	 * @param name
	 *            分组名称
	 * @return 分组map
	 */
	public static Map<String, Object> getConstantGroup(String name) {
		Map<String, Object> map = groups.get(name);
		if (null == map) {
			throw new NonExistException("Constant group " + name);
		}
		return map;
	}

	/**
	 * 根据常量分组名称和常量值获取常量的显示名称
	 *
	 * @param group
	 *            分组名称
	 * @param value
	 *            常量值
	 * @return 显示名称
	 */
	public static String getConstantGroupDisplay(String group, Object value) {
		Map<String, Object> map = getConstantGroup(group);
		String v = StringUtils.defaultString(String.valueOf(value));
		Object display = map.get(v);
		if (null == display) {
			throw new NonExistException(Constant.class.getName() + " " + group + "_" + value + " display");
		}
		return display.toString();
	}

	/**
	 * 根据分组名获取一个常量分组
	 *
	 * @param name
	 *            分组名
	 * @return 分组map
	 */
	public static Map<String, Object> getGroup(String name) {
		return getConstantGroup(name);
	}

	/**
	 * 根据常量分组名称和常量值获取常量的显示名称
	 *
	 * @param group
	 *            分组名称
	 * @param value
	 *            常量值
	 * @return 显示名称
	 */
	public static String getGroupDisplay(String group, Object value) {
		return getConstantGroupDisplay(group, value);
	}

	/**
	 * 对比值是否和常量值相等
	 *
	 * @param name
	 *            常量名称
	 * @param value
	 *            比较值
	 * @return 是否相等
	 */
	public static boolean equals(String name, Object value) {
		return equals(null, name, value);
	}

	/**
	 * 对比值是否和常量值相等
	 *
	 * @param group
	 *            分组名称
	 * @param name
	 *            常量名称
	 * @param value
	 *            比较值
	 * @return 是否相等
	 */
	public static boolean equals(String group, String name, Object value) {
		Boolean result = false;
		Object v = getConstant(group, name);
		if (Object.class.getClass() == String.class.getClass()) {
			if (value.toString().equals(v.toString())) {
				result = true;
			}
		} else {
			if (v == value) {
				result = true;
			}
		}
		return result;
	}

}
