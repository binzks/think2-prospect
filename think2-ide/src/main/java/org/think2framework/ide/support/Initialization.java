package org.think2framework.ide.support;

import org.think2framework.cmf.support.CmfSystem;
import org.think2framework.context.Configuration;
import org.think2framework.context.ModelFactory;
import org.think2framework.context.bean.Constant;
import org.think2framework.context.bean.Datasource;

/**
 * Created by zhoubin on 16/7/19. 系统初始化
 */
public class Initialization {

	private String ds; // 数据源名称

	private String type = "mysql"; // 数据库类型

	private Integer minIdle = 1; // 数据源最小空闲连接

	private Integer maxIdle = 2; // 数据源最大空闲连接

	private Integer initialSize = 2;// 数据源初始化连接数

	private Integer removeAbandonedTimeout = 300;// 数据源超时时间(以秒数为单位)

	private String database; // 数据库名称

	private String queryAddress; // 读取数据地址

	private String writerAddress;// 写入数据地址

	private String username; // 数据库用户名

	private String password; // 数据库密码

	public void init() {
		if (!Configuration.isInitialized() && null != ds) {
			// https访问的时候需要设置
			// System.setProperty("jsse.enableSNIExtension", "false");
			new CmfSystem().build(
					new Datasource(ds, type, minIdle, maxIdle, initialSize, removeAbandonedTimeout, database,
							queryAddress, writerAddress, username, password),
					"Think2 IDE", "IDE", "Think2 is a Integrated Development Environment for wap and cms system.",
					"V1.0");
			ModelFactory.scanPackages(ds, true, Constant.class.getPackage().getName());
			Configuration.config(this.getClass().getResource("/").getPath());
		}
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}

	public void setInitialSize(Integer initialSize) {
		this.initialSize = initialSize;
	}

	public void setRemoveAbandonedTimeout(Integer removeAbandonedTimeout) {
		this.removeAbandonedTimeout = removeAbandonedTimeout;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public void setQueryAddress(String queryAddress) {
		this.queryAddress = queryAddress;
	}

	public void setWriterAddress(String writerAddress) {
		this.writerAddress = writerAddress;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
