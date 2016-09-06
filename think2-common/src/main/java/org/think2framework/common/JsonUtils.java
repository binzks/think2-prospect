package org.think2framework.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.think2framework.common.exception.NonExistException;
import org.think2framework.common.exception.SimpleException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by zhoubin on 16/7/19. json工具类,以jackson为基础
 */
public class JsonUtils {

	/**
	 * 对象转字符串
	 * 
	 * @param object
	 *            对象
	 * @return json字符串
	 */
	public static String toString(Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new SimpleException(e);
		}
	}

	/**
	 * 字符串转换为单个对象
	 * 
	 * @param json
	 *            字符串
	 * @param valueType
	 *            类
	 * @return 类对象
	 */
	public static <T> T readString(String json, Class<T> valueType) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			return objectMapper.readValue(json, valueType);
		} catch (IOException e) {
			throw new SimpleException(e);
		}
	}

	/**
	 * 字符串转换为复杂对象
	 * 
	 * @param json
	 *            json字符串
	 * @param valueType
	 *            type类型
	 * @return 对象
	 */
	public static <T> T readString(String json, Type valueType) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			return objectMapper.readValue(json, objectMapper.constructType(valueType));
		} catch (IOException e) {
			throw new SimpleException(e);
		}
	}

	/**
	 * 字符串转换为复杂对象
	 * 
	 * @param json
	 *            字符串
	 * @param valueTypeRef
	 *            复杂类型
	 * @return 对象
	 */
	public static <T> T readString(String json, TypeReference valueTypeRef) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			return objectMapper.readValue(json, valueTypeRef);
		} catch (IOException e) {
			throw new SimpleException(e);
		}
	}

	/**
	 * 读取文件并解析成单个对象
	 *
	 * @param file
	 *            文件
	 * @param valueType
	 *            对象类
	 * @return 对象实例
	 */
	public static <T> T readFile(File file, Class<T> valueType) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(file, valueType);
		} catch (FileNotFoundException e) {
			throw new NonExistException(e);
		} catch (IOException e) {
			throw new SimpleException(e);
		}
	}

	/**
	 * 读取文件并解析成单个对象
	 * 
	 * @param file
	 *            文件路径和名称
	 * @param valueType
	 *            对象类
	 * @return 对象实例
	 */
	public static <T> T readFile(String file, Class<T> valueType) {
		return readFile(new File(file), valueType);
	}

	/**
	 * 读取文件并解析成复杂对象,如数组
	 *
	 * @param file
	 *            文件
	 * @param valueTypeRef
	 *            复杂对象类型
	 * @return 对象实例
	 */
	public static <T> T readFile(File file, TypeReference valueTypeRef) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
			return objectMapper.readValue(file, valueTypeRef);
		} catch (FileNotFoundException e) {
			throw new NonExistException(e);
		} catch (IOException e) {
			throw new SimpleException(e);
		}
	}

	/**
	 * 读取文件并解析成复杂对象,如数组
	 * 
	 * @param file
	 *            文件路径和名称
	 * @param valueTypeRef
	 *            复杂对象类型
	 * @return 对象实例
	 */
	public static <T> T readFile(String file, TypeReference valueTypeRef) {
		return readFile(new File(file), valueTypeRef);
	}

	/**
	 * 将对象转换成json字符串并写入文件
	 * 
	 * @param file
	 *            文件
	 * @param object
	 *            对象
	 */
	public static void writerFile(String file, Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(new File(file), object);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
