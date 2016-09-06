package org.think2framework.context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.think2framework.common.JsonUtils;
import org.think2framework.common.StringUtils;
import org.think2framework.common.exception.ExistException;
import org.think2framework.context.bean.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoubin on 16/7/24. 消息工厂,主要用于处理系统的错误和正确消息提示
 */
public class MessageFactory {

	private static final Logger logger = LogManager.getLogger(MessageFactory.class);

	public static final String CODE_UNKNOWN = "001"; // 未知错误编号

	public static final String CODE_SUCCESS = "0"; // 成功编号

	private static Map<String, Message> messages = new HashMap<>(); // 系统消息

	/**
	 * 创建消息工厂,先清除原先的消息再添加初始化消息
	 * 
	 * @param messageList
	 *            初始化消息
	 */
	public static synchronized void build(List<Message> messageList) {
		int size = messages.size();
		messages.clear();
		if (size > 0) {
			logger.debug("Clear " + size + " messages");
		}
		append(new Message(CODE_SUCCESS, "系统保留的成功编号"));
		append(new Message(CODE_UNKNOWN, "系统保留的未知异常编号"));
		if (null != messageList) {
			messageList.forEach(MessageFactory::append);
		}
		logger.debug(MessageFactory.class.getName() + " build success");
	}

	/**
	 * 追加一个消息,如果已经存在则抛出异常
	 * 
	 * @param message
	 *            消息
	 */
	public static synchronized void append(Message message) {
		String code = message.getCode();
		if (null != messages.get(code)) {
			throw new ExistException(Message.class.getName() + " " + code);
		}
		messages.put(code, message);
		logger.debug("Append " + Message.class.getName() + " " + code + " " + JsonUtils.toString(message));
	}

	/**
	 * 获取一个成功消息
	 * 
	 * @param data
	 *            消息内容
	 * @return 消息
	 */
	public static Message getSuccessMessage(Object data) {
		Message message = new Message();
		message.setCode(CODE_SUCCESS);
		message.setData(data);
		return message;
	}

	/**
	 * 获取一个json字符串的成功消息
	 * 
	 * @param data
	 *            消息内容
	 * @return json字符串
	 */
	public static String getSuccessJsonMessage(Object data) {
		Message message = getSuccessMessage(data);
		return JsonUtils.toString(message);
	}

	/**
	 * 获取一个消息,如果带参数则将消息内容中的?用参数代替
	 * 
	 * @param code
	 *            消息编号
	 * @param values
	 *            参数值
	 * @return 消息
	 */
	public static Message getMessage(String code, String... values) {
		Message message = new Message();
		message.setCode(code);
		Message msg = messages.get(code);
		if (null == msg) {
			message.setCode(CODE_UNKNOWN);
			message.setData(code);
		} else {
			String data = StringUtils.toString(msg.getData());
			if (null != values) {
				for (String value : values) {
					data = data.replaceFirst("\\?", value);
				}
			}
			message.setData(data);
		}
		return message;
	}

	/**
	 * 获取一个json字符串的消息
	 * 
	 * @param code
	 *            消息编号
	 * @param values
	 *            参数值
	 * @return json消息字符串
	 */
	public static String getJsonMessage(String code, String... values) {
		Message message = getMessage(code, values);
		return JsonUtils.toString(message);
	}
}
