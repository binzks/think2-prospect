package org.think2framework.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.think2framework.common.JsonUtils;
import org.think2framework.common.exception.ExistException;
import org.think2framework.common.exception.NonExistException;
import org.think2framework.context.bean.WeixinAccount;
import org.think2framework.weixin.core.ClusterObject;
import org.think2framework.weixin.core.SingleObject;
import org.think2framework.weixin.core.WeixinObject;

/**
 * Created by zhoubin on 16/7/29. 微信工厂
 */
public class WeixinFactory {

	private static final Logger logger = LogManager.getLogger(WeixinFactory.class);

	private static Map<String, WeixinObject> weixinObjects = new HashMap<>(); // 微信对象

	private static boolean cluster = false; // 是否集群部署,默认否

	/**
	 * 创建微信工厂
	 * 
	 * @param weixinAccounts
	 *            微信号列表
	 */
	public static synchronized void build(List<WeixinAccount> weixinAccounts) {
		weixinAccounts.forEach(WeixinFactory::append);
		logger.debug(WeixinFactory.class.getName() + " build success,cluster deploy " + cluster);
	}

	/**
	 * 创建微信工厂
	 *
	 * @param clusterDeploy
	 *            是否集群部署
	 * @param weixinAccounts
	 *            微信号列表
	 */
	public static synchronized void build(boolean clusterDeploy, List<WeixinAccount> weixinAccounts) {
		cluster = clusterDeploy;
		build(weixinAccounts);
	}

	/**
	 * 追加一个微信
	 * 
	 * @param weixinAccount
	 *            微信号
	 */
	public static synchronized void append(WeixinAccount weixinAccount) {
		String name = weixinAccount.getName();
		if (null != weixinObjects.get(name)) {
			throw new ExistException(WeixinAccount.class.getName() + " " + name);
		}
		WeixinObject weixinObject;
		if (cluster) {
			weixinObject = new ClusterObject(weixinAccount.getAppId(), weixinAccount.getAppSecret(),
					weixinAccount.getExpiresIn(), weixinAccount.getNonce());
		} else {
			weixinObject = new SingleObject(weixinAccount.getAppId(), weixinAccount.getAppSecret(),
					weixinAccount.getExpiresIn(), weixinAccount.getNonce());
		}
		weixinObjects.put(name, weixinObject);
		logger.debug("Append " + WeixinObject.class.getName() + " " + name + " " + JsonUtils.toString(weixinAccount));
	}

	/**
	 * 获取一个微信对象
	 * 
	 * @param name
	 *            微信名称
	 * @return 微信对象
	 */
	public static WeixinObject get(String name) {
		WeixinObject weixinObject = weixinObjects.get(name);
		if (null == weixinObject) {
			throw new NonExistException(WeixinObject.class.getName() + " " + name);
		}
		return weixinObject;
	}

}
