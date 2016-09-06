package org.think2framework.cmf.bean;

import java.util.Map;

import org.think2framework.cmf.support.Constants;
import org.think2framework.orm.core.ClassUtils;
import org.think2framework.orm.persistence.Column;
import org.think2framework.orm.persistence.Table;

/**
 * Created by zhoubin on 16/4/29. 管理员角色
 */
@Table(name = "system_admin_role", pk = "admin_role_id", uniques = {}, indexes = {}, comment = "系统管理员角色表")
public class AdminRole {

	@Column(name = "admin_role_id", type = ClassUtils.TYPE_INTEGER, comment = "主键")
	private Integer id;

	@Column(name = "admin_role_code", comment = "角色编号")
	private String code;

	@Column(name = "admin_role_name", comment = "角色名称")
	private String name;

	@Column(name = "admin_role_status", type = ClassUtils.TYPE_INTEGER, length = 2, comment = "状态,对应枚举"
			+ Constants.STATUS_GROUP)
	private Integer status;

	@Column(name = "admin_role_module", type = ClassUtils.TYPE_JSON, comment = "角色的权限,包括模块和按钮")
	private Map<String, AdminRolePermissions> modules;

	@Column(name = "admin_role_comment", length = 255, comment = "角色备注")
	private String comment;

	public AdminRole() {

	}

	public AdminRole(String code, String name, Integer status, Map<String, AdminRolePermissions> modules,
			String comment) {
		this.code = code;
		this.name = name;
		this.status = status;
		this.modules = modules;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Map<String, AdminRolePermissions> getModules() {
		return modules;
	}

	public void setModules(Map<String, AdminRolePermissions> modules) {
		this.modules = modules;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
