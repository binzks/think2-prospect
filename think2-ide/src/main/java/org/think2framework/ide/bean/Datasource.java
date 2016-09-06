package org.think2framework.ide.bean;

import org.think2framework.cmf.support.Constants;
import org.think2framework.orm.core.ClassUtils;
import org.think2framework.orm.persistence.Column;
import org.think2framework.orm.persistence.JoinTable;
import org.think2framework.orm.persistence.Table;

/**
 * Created by zhoubin on 16/5/1. 数据库bean
 */
@Table(name = "system_datasource", pk = "ds_id", uniques = {"environment_code,ds_name"}, indexes = {
		"ds_status"}, comment = "系统数据源表")
@JoinTable(name = "enJoin", table = "system_environment", key = "environment_code", joinKey = "environment_code")
public class Datasource {

	@Column(name = "ds_id", type = ClassUtils.TYPE_INTEGER, nullable = false, comment = "主键")
	private Integer id;

	@Column(name = "environment_code", length = 255, nullable = false, comment = "环境编号,String[]的json数据")
	private String environment;

	@Column(name = "ds_name", nullable = false, comment = "数据源名称")
	private String name;

	@Column(name = "ds_type", nullable = false, comment = "类型,对应枚举值")
	private String type;

	@Column(name = "ds_database", nullable = false, comment = "数据库名称")
	private String database;

	@Column(name = "ds_status", type = ClassUtils.TYPE_INTEGER, nullable = false, length = 2, comment = "状态,对应枚举值"
			+ Constants.STATUS_GROUP)
	private Integer status;

	@Column(name = "ds_comment", comment = "数据源备注", length = 255)
	private String comment;

	@Column(name = "environment_db_address", join = "enJoin")
	private String dbAddress;

	@Column(name = "environment_db_port", join = "enJoin", type =  ClassUtils.TYPE_INTEGER)
	private Integer dbPort;

	@Column(name = "environment_db_username", join = "enJoin")
	private String dbUsername;

	@Column(name = "environment_db_password", join = "enJoin")
	private String dbPassword;

	public Datasource() {

	}

	public Datasource(String environment, String name, String type, String database, Integer status, String comment) {
		this.environment = environment;
		this.name = name;
		this.type = type;
		this.database = database;
		this.status = status;
		this.comment = comment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
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

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDbAddress() {
		return dbAddress;
	}

	public void setDbAddress(String dbAddress) {
		this.dbAddress = dbAddress;
	}

	public Integer getDbPort() {
		return dbPort;
	}

	public void setDbPort(Integer dbPort) {
		this.dbPort = dbPort;
	}

	public String getDbUsername() {
		return dbUsername;
	}

	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
}
