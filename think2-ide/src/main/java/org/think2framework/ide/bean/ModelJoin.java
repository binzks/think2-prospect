package org.think2framework.ide.bean;

/**
 * Created by zhoubin on 15/9/7. 模型关联的定义
 */
public class ModelJoin {

	private String table;

	private String type;

	private String key;

	private String joinName;

	private String joinKey;

	private String comment;

	public ModelJoin() {

	}

	public ModelJoin(String table, String type, String key, String joinName, String joinKey, String comment) {
		this.table = table;
		this.type = type;
		this.key = key;
		this.joinName = joinName;
		this.joinKey = joinKey;
		this.comment = comment;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getJoinName() {
		return joinName;
	}

	public void setJoinName(String joinName) {
		this.joinName = joinName;
	}

	public String getJoinKey() {
		return joinKey;
	}

	public void setJoinKey(String joinKey) {
		this.joinKey = joinKey;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
