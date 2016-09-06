package org.think2framework.wap.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.think2framework.context.Configuration;
import org.think2framework.context.ModelFactory;

/**
 * Created by zhoubin on 16/7/19. 系统初始化
 */
public class Initialization implements ApplicationContextAware {

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (!Configuration.isInitialized()) {
			System.setProperty("jsse.enableSNIExtension", "false");
			Configuration.config(this.getClass().getResource("/").getPath());
			ModelFactory.scanPackage("wiseksWap", true, "org.think2framework.wap.bean");
		}
	}
}
