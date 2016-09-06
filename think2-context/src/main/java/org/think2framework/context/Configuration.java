package org.think2framework.context;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.think2framework.common.JsonUtils;
import org.think2framework.common.exception.NonExistException;
import org.think2framework.context.bean.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhoubin on 16/7/8. 配置
 */
public class Configuration {

	private static final Logger logger = LogManager.getLogger(Configuration.class);

	private static final String jsonSuffix = ".json"; // json文件后缀

	private static boolean initialized = false; // 是否初始化过

	/**
	 * 系统配置是否已经初始化过
	 * 
	 * @return 系统配置是否已经初始化过
	 */
	public static boolean isInitialized() {
		return initialized;
	}

	/**
	 * 从一个配置路径加载配置文件,加载日志采用logger.debug输出,不使用log4j输出,log4j日志采用mongodb保存,
	 * 系统启动日志不加入
	 * 
	 * @param dir
	 *            配置文件路径
	 */
	public static synchronized void config(String dir) {
		logger.debug("Think2 context is being initialized");
		// 初始化系统常量
		try {
			List<Constant> constants = JsonUtils.readFile(dir + "think2constant" + jsonSuffix,
					new TypeReference<List<Constant>>() {
					});
			ConstantFactory.build(constants);
		} catch (NonExistException e) {
			logger.debug("The constant file think2constant{} does not exist and has not been loaded", jsonSuffix);
		}
		// 初始化系统消息
		try {
			List<Message> messages = JsonUtils.readFile(dir + "think2message" + jsonSuffix,
					new TypeReference<List<Message>>() {
					});
			MessageFactory.build(messages);
		} catch (NonExistException e) {
			logger.debug("The message file think2message{} does not exist and has not been loaded", jsonSuffix);
		}
		// 初始化模型配置
        try {
            List<Datasource> datasources = JsonUtils.readFile(dir + "think2datasource" + jsonSuffix,
                    new TypeReference<List<Datasource>>() {
                    });
            List<Model> allModels = new ArrayList<>();
            File[] modelFiles = new File(dir + "model").listFiles();
            if (modelFiles != null) {
                for (File file : modelFiles) {
                    String fileName = file.getName();
                    if (fileName.toLowerCase().endsWith(jsonSuffix)) {
                        List<Model> models = JsonUtils.readFile(file, new TypeReference<List<Model>>() {
                        });
                        Collections.copy(allModels, models);
                    }
                }
            }
            ModelFactory.build(datasources, allModels, true);
        }catch (NonExistException e){
            logger.debug("The datasource file think2datasource{} does not exist and has not been loaded", jsonSuffix);
        }
		// 初始化缓存配置
		try {
			List<Cache> caches = JsonUtils.readFile(dir + "think2cache" + jsonSuffix, new TypeReference<List<Cache>>() {
			});
			CacheFactory.build(caches);
		} catch (NonExistException e) {
			logger.debug("The cache file think2cache{} does not exist and has not been loaded", jsonSuffix);
		}
		// 初始化微信配置
		try {
			Weixin weixin = JsonUtils.readFile(dir + "think2weixin" + jsonSuffix, Weixin.class);
			WeixinFactory.build(weixin.getCluster(), weixin.getAccounts());
		} catch (NonExistException e) {
			logger.debug("The weixin file think2weixin{} does not exist and has not been loaded", jsonSuffix);
		}
		initialized = true;
		logger.debug("Think2 context initialization is completed");
	}

}
