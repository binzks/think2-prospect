package org.think2framework.orm.core;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.LinkedCaseInsensitiveMap;

import org.think2framework.common.JsonUtils;
import org.think2framework.common.NumberUtils;
import org.think2framework.common.StringUtils;
import org.think2framework.common.exception.NonExistException;
import org.think2framework.common.exception.SimpleException;
import org.think2framework.orm.persistence.Column;

/**
 * Created by zhoubin on 16/6/2. 类工具,主要处理类字段,读取和写入,以及创建实例
 */
public class ClassUtils {

	public static final String TYPE_STRING = "string"; // string类型

	public static final String TYPE_INTEGER = "integer"; // integer类型

	public static final String TYPE_LONG = "long"; // long类型

	public static final String TYPE_BOOLEAN = "boolean"; // boolean类型

	public static final String TYPE_DOUBLE = "double"; // double类型

	public static final String TYPE_FLOAT = "float"; // float类型

	public static final String TYPE_TEXT = "text"; // text类型

	public static final String TYPE_JSON = "json"; // json类型

	/**
	 * 获取字段的名称,如果定义了别名以别名为准,如果定义了名称以名称为准,如果都没有以字段本身名称为准
	 * 
	 * @param field
	 *            字段
	 * @return 字段的名称
	 */
	private static String getFieldKey(Field field) {
		String key = field.getName();
		Column column = field.getAnnotation(Column.class);
		if (null != column) {
			if (StringUtils.isNotBlank(column.name())) {
				key = column.name();
			}
			if (StringUtils.isNotBlank(column.alias())) {
				key = column.alias();
			}
		}
		return key;
	}

	/**
	 * 从实例获取一个字段
	 * 
	 * @param instance
	 *            实例
	 * @param name
	 *            字段名称
	 * @return 字段
	 */
	public static Field getField(Object instance, String name) {
		Field[] fields = instance.getClass().getDeclaredFields();
		Field result = null;
		for (Field field : fields) {
			String key = getFieldKey(field);
			if (key.equals(name)) {
				result = field;
				break;
			}
		}
		if (null == result) {
			throw new NonExistException(instance.getClass().getName() + " field " + name);
		}
		return result;
	}

	/**
	 * 获取实例的一个字段值,如果是map类型使用get,如果是其他则使用反射获取字段值
	 * 
	 * @param instance
	 *            实例
	 * @param name
	 *            字段名称
	 * @return 字段值
	 */
	public static Object getFieldValue(Object instance, String name) {
		Class clazz = instance.getClass();
		if (HashMap.class == clazz || LinkedHashMap.class == clazz || LinkedCaseInsensitiveMap.class == clazz) {
			Map map = (Map) instance;
			Object value = map.get(name);
			// 如果是数组,则转换为字符串
			if (null != value && ArrayList.class == value.getClass()) {
				value = value.toString();
			}
			return value;
		} else {
			Field field = getField(instance, name);
			Column column = field.getAnnotation(Column.class);
			try {
				field.setAccessible(true);
				Object data = field.get(instance);
				if (null != column) {
					if (TYPE_JSON.equals(column.type())) {
						data = JsonUtils.toString(data);
					}
				}
				return data;
			} catch (IllegalAccessException e) {
				throw new SimpleException(e);
			}
		}
	}

	/**
	 * 为一个实体对象设置字段值,如果是map类型使用put,其他则使用反射设置
	 * 
	 * @param instance
	 *            实体对象
	 * @param name
	 *            设置字段名称
	 * @param value
	 *            设置字段值
	 */
	public static void setFieldValue(Object instance, String name, Object value) {
		Class clazz = instance.getClass();
		if (HashMap.class == clazz || LinkedHashMap.class == clazz || LinkedCaseInsensitiveMap.class == clazz) {
			Map map = (Map) instance;
			map.put(name, value);
		} else {
			Field field = getField(instance, name);
			try {
				field.setAccessible(true);
				field.set(instance, value);
			} catch (IllegalAccessException e) {
				throw new SimpleException(e);
			}
		}
	}

	/**
	 * 根据map对象创建一个类实例
	 * 
	 * @param clazz
	 *            类
	 * @param map
	 *            map数据
	 * @return 类实例
	 */
	public static <T> T createInstance(Class<T> clazz, Map<String, Object> map) {
		try {
			T instance = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				String name = getFieldKey(field);
				Object value = map.get(name);
				// 如果map没有数据，则不添加，默认为null
				if (null == value) {
					continue;
				}
				Column column = field.getAnnotation(Column.class);
				field.setAccessible(true);
				if (null != column) {
					String type = column.type();
					if (ClassUtils.TYPE_STRING.equalsIgnoreCase(type) || ClassUtils.TYPE_TEXT.equalsIgnoreCase(type)) {
						field.set(instance, map.get(name).toString());
					} else if (ClassUtils.TYPE_INTEGER.equalsIgnoreCase(type)) {
						field.set(instance, NumberUtils.toInt(map.get(name)));
					} else if (ClassUtils.TYPE_BOOLEAN.equalsIgnoreCase(type)) {
						field.set(instance, Boolean.parseBoolean(map.get(name).toString()));
					} else if (ClassUtils.TYPE_JSON.equalsIgnoreCase(type)) {
						if (field.getGenericType() instanceof ParameterizedType) {
							field.set(instance, JsonUtils.readString(map.get(name).toString(), field.getGenericType()));
						} else {
							field.set(instance, JsonUtils.readString(map.get(name).toString(), field.getType()));
						}
					} else if (ClassUtils.TYPE_DOUBLE.equalsIgnoreCase(type)) {
						field.set(instance, NumberUtils.toDouble(map.get(name)));
					} else if (ClassUtils.TYPE_LONG.equalsIgnoreCase(type)) {
						field.set(instance, NumberUtils.toLong(map.get(name)));
					} else if (ClassUtils.TYPE_FLOAT.equalsIgnoreCase(type)) {
						field.set(instance, NumberUtils.toFloat(map.get(name)));
					} else {
						field.set(instance, map.get(name));
					}
				} else {
					field.set(instance, map.get(name));
				}
			}
			return instance;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new SimpleException(e);
		}
	}

	/**
	 * 根据sql的resultSet和类创建一个类的实例
	 * 
	 * @param clazz
	 *            类
	 * @param rs
	 *            sql结果集
	 * @return 类实例
	 */
	public static <T> T createInstance(Class<T> clazz, ResultSet rs) {
		try {
			T instance = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				String name = getFieldKey(field);
				Column column = field.getAnnotation(Column.class);
				try {
					field.setAccessible(true);
					if (null != column) {
						String type = column.type();
						if (ClassUtils.TYPE_STRING.equalsIgnoreCase(type)
								|| ClassUtils.TYPE_TEXT.equalsIgnoreCase(type)) {
							field.set(instance, rs.getString(name));
						} else if (ClassUtils.TYPE_INTEGER.equalsIgnoreCase(type)) {
							field.set(instance, rs.getInt(name));
						} else if (ClassUtils.TYPE_BOOLEAN.equalsIgnoreCase(type)) {
							field.set(instance, rs.getBoolean(name));
						} else if (ClassUtils.TYPE_JSON.equalsIgnoreCase(type)) {
							if (field.getGenericType() instanceof ParameterizedType) {
								field.set(instance, JsonUtils.readString(rs.getString(name), field.getGenericType()));
							} else {
								field.set(instance, JsonUtils.readString(rs.getString(name), field.getType()));
							}
						} else if (ClassUtils.TYPE_DOUBLE.equalsIgnoreCase(type)) {
							field.set(instance, rs.getDouble(name));
						} else if (ClassUtils.TYPE_LONG.equalsIgnoreCase(type)) {
							field.set(instance, rs.getLong(name));
						} else if (ClassUtils.TYPE_FLOAT.equalsIgnoreCase(type)) {
							field.set(instance, rs.getFloat(name));
						} else {
							field.set(instance, rs.getObject(name));
						}
					} else {
						field.set(instance, rs.getObject(name));
					}
				} catch (SQLException e) {
					if (e.getSQLState().equals("S1000")) { // 表示ResultSet是空的,没有数据
						return null;
					} else if (!e.getSQLState().equals("S0022")) { // S0022表示字段name不存在
						throw new SimpleException(e);
					}
				}
			}
			return instance;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new SimpleException(e);
		}
	}
}
