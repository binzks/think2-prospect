package org.think2framework.common;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import org.think2framework.common.exception.SimpleException;

/**
 * Created by zhoubin on 15/12/9. 文件工具
 */
public class XmlUtils {

	/**
	 * 读取xml文件，返回root Element
	 *
	 * @param file
	 *            xml文件路径
	 * @return root Element
	 */
	public static Element readXml(String file) {
		SAXReader reader = new SAXReader();
		Element element;
		try {
			element = reader.read(file).getRootElement();
		} catch (DocumentException e) {
			throw new SimpleException(e);
		}
		return element;
	}

	/**
	 * 读取xml文件，返回root Element
	 *
	 * @param file
	 *            xml文件
	 * @return root Element
	 */
	public static Element readXml(File file) {
		SAXReader reader = new SAXReader();
		Element element;
		try {
			element = reader.read(file).getRootElement();
		} catch (DocumentException e) {
			throw new SimpleException(e);
		}
		return element;
	}

	/**
	 * 读取一个xml字符串转换为root Element
	 * 
	 * @param text
	 *            xml字符串
	 * @return root Element
	 */
	public static Element readXmlSting(String text) {
		try {
			Element element = DocumentHelper.parseText(text).getRootElement();
			return element;
		} catch (DocumentException e) {
			throw new SimpleException(e);
		}
	}

	/**
	 * 读取一个xml字符串转换为map数据
	 * 
	 * @param text
	 *            xml字符串
	 * @return map
	 */
	public static Map<String, Object> readXmlStingToMap(String text) {
		Map<String, Object> map = new HashMap<>();
		Element element = readXmlSting(text);
		elementToMap(map, element);
		return map;
	}

	/**
	 * 将文件读取后解析成map对象
	 * 
	 * @param file
	 *            文件
	 * @return map数据
	 */
	public static Map<String, Object> readXmlToMap(File file) {
		Map<String, Object> map = new HashMap<>();
		Element element = readXml(file);
		elementToMap(map, element);
		return map;
	}

	/**
	 * xml的节点转换成map对象，递归
	 *
	 * @param map
	 *            最终map对象
	 * @param source
	 *            xml节点
	 */
	private static void elementToMap(Map map, Element source) {
		List<Element> elements = source.elements();
		if (elements.size() == 0) {
			// 如果没有子节点则添加自己
			map.put(source.getName(), source.getText());
		} else if (elements.size() == 1) {
			// 如果有一个子节点，则添加一个对象
			Map<String, Object> nodeMap = new HashMap<>();
			elementToMap(nodeMap, elements.get(0));
			map.put(source.getName(), nodeMap);
		} else {
			// 如果有多个子节点，则添加一个数组
			List<Map<String, Object>> list = new ArrayList<>();
			for (Element element : elements) {
				Map<String, Object> nodeMap = new HashMap<>();
				elementToMap(nodeMap, element);
				list.add(nodeMap);
			}
			map.put(source.getName(), list);
		}
	}

}
