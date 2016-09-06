package org.think2framework.orm.bean;

import org.think2framework.orm.core.SelectHelp;

import java.util.List;
import java.util.Map;

/**
 * Created by zhoubin on 16/7/12. 实体bean
 */
public class Entity {

	private String table; // 主表名称

	private String pk; // 主键名称

	private Map<String, EntityColumn> columns; // 列

	private String joinSql; // 关联sql

	private String columnSql; // 默认查询列sql

	public Entity(String table, String pk, List<Join> joins, Map<String, EntityColumn> columns) {
		this.table = table;
		this.pk = pk;
		this.columns = columns;
		this.joinSql = SelectHelp.generateJoins(joins);
		this.columnSql = SelectHelp.generateColumns(columns);
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public Map<String, EntityColumn> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, EntityColumn> columns) {
		this.columns = columns;
	}

	public String getJoinSql() {
		return joinSql;
	}

	public void setJoinSql(String joinSql) {
		this.joinSql = joinSql;
	}

	public String getColumnSql() {
		return columnSql;
	}

	public void setColumnSql(String columnSql) {
		this.columnSql = columnSql;
	}
}
