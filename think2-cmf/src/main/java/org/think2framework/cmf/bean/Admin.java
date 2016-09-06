package org.think2framework.cmf.bean;

import java.util.Map;

import org.think2framework.cmf.support.Constants;
import org.think2framework.orm.core.ClassUtils;
import org.think2framework.orm.persistence.Column;
import org.think2framework.orm.persistence.JoinTable;
import org.think2framework.orm.persistence.Table;

/**
 * Created by zhoubin on 16/4/19. 系统管理员
 */
@Table(name = "system_admin", pk = "admin_id", uniques = {"admin_code"}, comment = "系统管理员表")
@JoinTable(name = "roleJoin", table = "system_admin_role", key = "admin_role_id", joinKey = "admin_role_id")
public class Admin {

	@Column(name = "admin_id", type = ClassUtils.TYPE_INTEGER, comment = "主键")
	private Integer id;

	@Column(name = "admin_code", comment = "用户编号,唯一")
	private String code;

	@Column(name = "admin_name", comment = "用户姓名")
	private String name;

	@Column(name = "admin_password", comment = "用户密码")
	private String password;

	@Column(name = "admin_phone", comment = "手机号码")
	private String phone;

	@Column(name = "admin_email", comment = "用户邮箱")
	private String email;

	@Column(name = "admin_role_id", type = ClassUtils.TYPE_INTEGER, length = 11, comment = "用户角色")
	private Integer roleId;

	@Column(name = "admin_status", type = ClassUtils.TYPE_INTEGER, comment = "状态,对应枚举" + Constants.STATUS_GROUP)
	private Integer status;

	@Column(name = "admin_comment", length = 255, comment = "用户备注")
	private String comment;

	@Column(name = "admin_role_code", join = "roleJoin")
	private String roleCode;

	@Column(name = "admin_role_name", join = "roleJoin")
	private String roleName;

	@Column(name = "admin_role_module", join = "roleJoin", type = "json")
	private Map<String, AdminRolePermissions> roleModules;

	public Admin() {

	}

	public Admin(String code, String name, String password, String phone, String email, Integer roleId, Integer status,
			String comment) {
		this.code = code;
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.roleId = roleId;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Map<String, AdminRolePermissions> getRoleModules() {
		return roleModules;
	}

	public void setRoleModules(Map<String, AdminRolePermissions> roleModules) {
		this.roleModules = roleModules;
	}
}
