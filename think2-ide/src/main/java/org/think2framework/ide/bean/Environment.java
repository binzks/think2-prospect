package org.think2framework.ide.bean;

import org.think2framework.cmf.support.Constants;
import org.think2framework.orm.core.ClassUtils;
import org.think2framework.orm.persistence.Column;
import org.think2framework.orm.persistence.Table;

/**
 * Created by zhoubin on 16/5/23. 系统环境bean
 */
@Table(name = "system_environment", pk = "environment_id", uniques = {"environment_code",
		"environment_name"}, indexes = {"environment_status"}, comment = "系统环境表")
public class Environment {

	@Column(name = "environment_id", type = ClassUtils.TYPE_INTEGER, nullable = false, comment = "主键")
	private Integer id;

	@Column(name = "environment_code", nullable = false, comment = "环境编号,唯一")
	private String code;

	@Column(name = "environment_name", nullable = false, comment = "环境名称")
	private String name;

	@Column(name = "environment_db_address", comment = "环境对应数据库地址", length = 255)
	private String dbAddress;

	@Column(name = "environment_db_port", comment = "环境对应数据库端口", type = ClassUtils.TYPE_INTEGER, length = 10)
	private Integer dbPort;

	@Column(name = "environment_db_username", comment = "环境对应数据库用户名")
	private String dbUsername;

	@Column(name = "environment_db_password", comment = "环境对应的数据库密码")
	private String dbPassword;

	@Column(name = "environment_status", type = ClassUtils.TYPE_INTEGER, nullable = false, length = 2, comment = "状态,对应枚举值"
			+ Constants.STATUS_GROUP)
	private Integer status;

	@Column(name = "environment_comment", length = 255, comment = "环境备注")
	private String comment;

	public Environment() {

	}

	public Environment(String code, String name, String dbAddress, Integer dbPort, String dbUsername, String dbPassword,
			Integer status, String comment) {
		this.code = code;
		this.name = name;
		this.dbAddress = dbAddress;
		this.dbPort = dbPort;
		this.dbUsername = dbUsername;
		this.dbPassword = dbPassword;
		this.status = status;
		this.comment = comment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
