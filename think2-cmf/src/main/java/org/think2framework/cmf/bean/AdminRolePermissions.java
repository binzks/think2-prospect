package org.think2framework.cmf.bean;

/**
 * Created by zhoubin on 16/5/5. 系统角色行列权限
 */
public class AdminRolePermissions {

	private String[] actions; // 模块按钮

	private String[] columns; // 模块列

	public AdminRolePermissions() {

	}

	public AdminRolePermissions(String[] actions, String[] columns) {
		this.actions = actions;
		this.columns = columns;
	}

	public String[] getActions() {
		return actions;
	}

	public void setActions(String[] actions) {
		this.actions = actions;
	}

	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}
}
