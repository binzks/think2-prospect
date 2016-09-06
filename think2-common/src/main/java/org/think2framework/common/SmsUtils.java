package org.think2framework.common;

import java.io.InputStream;
import java.util.PropertyResourceBundle;

import sms.sms.SmsSend;

/**
 * Created by zhoubin on 15/11/12. 发送短信工具类
 */
public class SmsUtils {

	private static String cmcc_password;
	private static String cmcc_appId;
	private static String cmcc_extendCode;
	private static boolean cmcc_deliveryResult;
	private static String cmcc_messageFormat;
	private static String cmcc_sendMethodType;
	private static String cmcc_interfaceId;
	private static String cmcc_interfaceName;

	static {
		try {
			InputStream fis = SmsUtils.class.getClassLoader().getResourceAsStream("config.properties");
			PropertyResourceBundle props = new PropertyResourceBundle(fis);
			cmcc_password = props.getString("apPassword");
			cmcc_appId = props.getString("appid");
			cmcc_extendCode = props.getString("extendCode");
			cmcc_deliveryResult = new Boolean(props.getString("deliveryResultRequest"));
			cmcc_messageFormat = props.getString("messageFormat");
			cmcc_sendMethodType = props.getString("sendMethod");
			cmcc_interfaceId = props.getString("intfid");
			cmcc_interfaceName = props.getString("intfname");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送移动短信
	 *
	 * @param phones
	 *            短信发送目的手机号码（多个号码以逗号分隔）
	 * @param msg
	 *            短信内容
	 * @return 发送短信移动数据库id
	 */
	public static String cmcc(String phones, String msg) {
		SmsSend smsSend = new SmsSend(cmcc_password, cmcc_appId, phones, msg, cmcc_extendCode, cmcc_deliveryResult,
				cmcc_messageFormat, cmcc_sendMethodType, cmcc_interfaceId, cmcc_interfaceName);
		return smsSend.send();
	}

}
