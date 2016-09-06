package org.think2framework.ide.bean;

import org.think2framework.cmf.support.Constants;
import org.think2framework.orm.core.ClassUtils;
import org.think2framework.orm.persistence.Column;
import org.think2framework.orm.persistence.Table;

/**
 * Created by zhoubin on 16/2/16. 常量bean
 */
@Table(name = "system_const", pk = "const_id", uniques = {"environment_code,const_name,const_group"}, indexes = {
		"const_group", "const_status"}, comment = "系统常量表,常量可以属于一个或者多个环境,在系统发布的时候讲发布相应环境所对应的所有常量")
public class Constant {

	@Column(name = "const_id", type = ClassUtils.TYPE_INTEGER, nullable = false, comment = "主键")
	private Integer id;

	@Column(name = "environment_code", length = 255, nullable = false, comment = "环境编号,String[]的json数据")
	private String environment;

	@Column(name = "const_name", nullable = false, comment = "常量名称")
	private String name;

	@Column(name = "const_status", type = ClassUtils.TYPE_INTEGER, nullable = false, length = 2, comment = "状态,对应枚举值"
			+ Constants.STATUS_GROUP)
	private Integer status;

	@Column(name = "const_display", comment = "显示名称")
	private String display;

	@Column(name = "const_group", comment = "分组名称")
	private String group;

	@Column(name = "const_value", nullable = false, comment = "常量值")
	private String value;

	@Column(name = "const_comment", comment = "常量备注", length = 255)
	private String comment;

	public Constant() {

	}

	public Constant(String environment, String name, Integer status, String display, String group, String value,
			String comment) {
		this.environment = environment;
		this.name = name;
		this.status = status;
		this.display = display;
		this.group = group;
		this.value = value;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
