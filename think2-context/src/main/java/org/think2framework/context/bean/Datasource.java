package org.think2framework.context.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.think2framework.common.StringUtils;
import org.think2framework.common.exception.NonsupportException;

/**
 * Created by zhoubin on 16/7/8. 数据源
 */
public class Datasource {

	private String name; // 数据源名称

	private String type; // 数据库类型

	private Integer minIdle; // 数据源最小空闲连接

	private Integer maxIdle; // 数据源最大空闲连接

	private Integer initialSize; // 数据源初始化连接数

	private Integer removeAbandonedTimeout; // 数据源超时时间(以秒数为单位)

	private String database; // 数据库名称

	private String queryAddress; // 读取数据库地址

	private String writerAddress; // 写入数据库地址

	private String username; // 数据库用户名

	private String password; // 数据库密码

	@JsonIgnore
	private JdbcTemplate queryJdbcTemplate; // 读取JdbcTemplate

	@JsonIgnore
	private JdbcTemplate writerJdbcTemplate; // 写入JdbcTemplate

	public Datasource() {

	}

	public Datasource(String name, String type, Integer minIdle, Integer maxIdle, Integer initialSize,
			Integer removeAbandonedTimeout, String database, String queryAddress, String writerAddress, String username,
			String password) {
		this.name = name;
		this.type = type;
		this.minIdle = minIdle;
		this.maxIdle = maxIdle;
		this.initialSize = initialSize;
		this.removeAbandonedTimeout = removeAbandonedTimeout;
		this.database = database;
		this.queryAddress = queryAddress;
		this.writerAddress = writerAddress;
		this.username = username;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	public Integer getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}

	public Integer getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(Integer initialSize) {
		this.initialSize = initialSize;
	}

	public Integer getRemoveAbandonedTimeout() {
		return removeAbandonedTimeout;
	}

	public void setRemoveAbandonedTimeout(Integer removeAbandonedTimeout) {
		this.removeAbandonedTimeout = removeAbandonedTimeout;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getQueryAddress() {
		return queryAddress;
	}

	public void setQueryAddress(String queryAddress) {
		this.queryAddress = queryAddress;
	}

	public String getWriterAddress() {
		return writerAddress;
	}

	public void setWriterAddress(String writerAddress) {
		this.writerAddress = writerAddress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public JdbcTemplate getQueryJdbcTemplate() {
		if (null == queryJdbcTemplate) {
			queryJdbcTemplate = getJdbcTemplate(queryAddress);
		}
		return queryJdbcTemplate;
	}

	public JdbcTemplate getWriterJdbcTemplate() {
		if (null == writerJdbcTemplate) {
			if (StringUtils.equals(writerAddress, queryAddress)) {
				writerJdbcTemplate = getQueryJdbcTemplate();
			} else {
				writerJdbcTemplate = getJdbcTemplate(writerAddress);
			}
		}
		return writerJdbcTemplate;
	}

	private JdbcTemplate getJdbcTemplate(String address) {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setMinIdle(this.minIdle);
		basicDataSource.setMaxIdle(this.maxIdle);
		basicDataSource.setInitialSize(this.initialSize);
		basicDataSource.setRemoveAbandonedOnBorrow(true);
		basicDataSource.setRemoveAbandonedTimeout(this.removeAbandonedTimeout);
		basicDataSource.setLogAbandoned(true);
		basicDataSource.setValidationQuery("SELECT 1");
		if ("mysql".equalsIgnoreCase(type)) {
			basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
			basicDataSource.setUrl("jdbc:mysql://" + address + "/" + database + "?characterEncoding=utf-8");
		} else if ("oracle".equalsIgnoreCase(type)) {
			basicDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
			basicDataSource.setUrl("jdbc:oracle:thin:@" + address + ":" + database);
		} else if ("sqlserver".equalsIgnoreCase(type)) {
			basicDataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			basicDataSource.setUrl("jdbc:sqlserver://" + address + ";databaseName=" + database);
		} else if ("sqlite".equalsIgnoreCase(type)) {
			basicDataSource.setDriverClassName("org.sqlite.JDBC");
			basicDataSource.setUrl("jdbc:sqlite:" + address + "/" + database);
		} else {
			throw new NonsupportException("Database type " + type);
		}
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);
		return new JdbcTemplate(basicDataSource);
	}

}
