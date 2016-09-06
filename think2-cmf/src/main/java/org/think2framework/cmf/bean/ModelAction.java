package org.think2framework.cmf.bean;

/**
 * Created by zhoubin on 16/5/13. 模型的操作按钮
 */
public class ModelAction {

	private String name; // 按钮显示名称

	private String title; // 按钮标题

	private String clazz; // 按钮的css样式

	private String type; // 按钮类型,对应ViewActionEnum

	private String href; // 按钮的实际功能链接

	private String comment; // 按钮的注释

	public ModelAction() {

	}

	public ModelAction(String name, String title, String clazz, String type, String href, String comment) {
		this.name = name;
		this.title = title;
		this.clazz = clazz;
		this.type = type;
		this.href = href;
		this.comment = comment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
