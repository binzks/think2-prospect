package org.think2framework.orm.bean;

/**
 * Created by zhoubin on 16/7/12. 实体列bean
 */
public class EntityColumn {

	private String name; // 字段名称

	private String join; // 字段关联名称,如果为null或者空表示主表

	private String type; // 字段类型

	private String alias; // 字段别名

	public EntityColumn() {

	}

	public EntityColumn(String name, String join, String type, String alias) {
		this.name = name;
		this.join = join;
		this.type = type;
		this.alias = alias;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJoin() {
		return join;
	}

	public void setJoin(String join) {
		this.join = join;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
