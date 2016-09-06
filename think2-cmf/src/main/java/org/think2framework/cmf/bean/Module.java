package org.think2framework.cmf.bean;

import org.think2framework.cmf.support.Constants;
import org.think2framework.orm.core.ClassUtils;
import org.think2framework.orm.persistence.Column;
import org.think2framework.orm.persistence.Table;

/**
 * Created by zhoubin on 16/5/5. 系统模块表
 */
@Table(name = "system_module", pk = "module_id", indexes = {"module_status", "module_order"}, comment = "系统模块表")
public class Module {

	@Column(name = "module_id", type = ClassUtils.TYPE_INTEGER, comment = "主键")
	private Integer id;

	@Column(name = "module_name", comment = "模块名称")
	private String name;

	@Column(name = "module_parent_id", type = ClassUtils.TYPE_INTEGER, comment = "模块父id")
	private Integer parentId;

	@Column(name = "module_status", type = ClassUtils.TYPE_INTEGER, length = 2, comment = "状态,对应枚举"
			+ Constants.STATUS_GROUP)
	private Integer status;

	@Column(name = "module_type", type = ClassUtils.TYPE_INTEGER, length = 2, comment = "模块类型,0-分组,1-节点")
	private Integer type;

	@Column(name = "module_uri", comment = "模块的uri链接")
	private String uri;

	@Column(name = "module_icon", comment = "模块的icon图标")
	private String icon;

	@Column(name = "module_order", type = ClassUtils.TYPE_INTEGER, length = 5, comment = "模块排序")
	private Integer order;

	@Column(name = "model_name", comment = "模型的名称")
	private String model;

	@Column(name = "module_comment", length = 255, comment = "模块备注")
	private String comment;

	private String[] actions; // 模块按钮

	private String[] columns; // 模块列

	public Module() {

	}

	public Module(String name, Integer parentId, Integer status, Integer type, String uri, String icon, Integer order,
			String model, String comment) {
		this.name = name;
		this.parentId = parentId;
		this.status = status;
		this.type = type;
		this.uri = uri;
		this.icon = icon;
		this.order = order;
		this.model = model;
		this.comment = comment;
	}

	public Module(Integer id, String name, Integer parentId, Integer status, Integer type, String uri, String icon,
			Integer order, String model, String comment, String[] actions, String[] columns) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.status = status;
		this.type = type;
		this.uri = uri;
		this.icon = icon;
		this.order = order;
		this.model = model;
		this.comment = comment;
		this.actions = actions;
		this.columns = columns;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
