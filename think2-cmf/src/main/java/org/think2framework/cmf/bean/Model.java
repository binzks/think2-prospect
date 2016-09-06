package org.think2framework.cmf.bean;

import java.util.Map;

import org.think2framework.cmf.support.Constants;
import org.think2framework.orm.bean.Filter;
import org.think2framework.orm.core.ClassUtils;
import org.think2framework.orm.persistence.Column;
import org.think2framework.orm.persistence.Table;

/**
 * Created by zhoubin on 16/2/16. 模型bean
 */
@Table(name = "system_model", pk = "model_id", uniques = {"model_name"}, indexes = {"model_status"}, comment = "系统模型表")
public class Model {

	@Column(name = "model_id", type = ClassUtils.TYPE_INTEGER, comment = "主键")
	private Integer id;

	@Column(name = "model_name", comment = "模型名称")
	private String name;

	@Column(name = "model_status", type = ClassUtils.TYPE_INTEGER, length = 2, comment = "状态,对应枚举值"
			+ Constants.STATUS_GROUP)
	private Integer status;

	@Column(name = "ds_name", comment = "数据源名称")
	private String ds;

	@Column(name = "model_table", comment = "数据库表名")
	private String table;

	@Column(name = "model_pk", comment = "主键名称")
	private String pk;

	@Column(name = "model_auto_increment", type = ClassUtils.TYPE_BOOLEAN, defaultValue = "true", comment = "模型数据库主键是否整型自增长true-是 false-否,如果否则为UUID")
	private Boolean autoIncrement;

	@Column(name = "model_unique", type = ClassUtils.TYPE_JSON, comment = "模型的唯一性约束,String[]的json格式")
	private String[] unique;

	@Column(name = "model_index", type = ClassUtils.TYPE_JSON, comment = "模型的索引,String[]的json格式")
	private String[] index;

	@Column(name = "model_join", type = ClassUtils.TYPE_JSON, comment = "模型对应的关联信息,String[]的json格式")
	private Map<String, ModelJoin> joins; // 模型对应的关联信息

	@Column(name = "model_column", type = ClassUtils.TYPE_JSON, comment = "模型对应的列,List<ModelColumn>的json格式")
	private Map<String, ModelColumn> columns; // 模型数据库表的字段

	@Column(name = "model_filter", type = ClassUtils.TYPE_JSON, comment = "模型对应的默认过滤条件,String[]的json格式")
	private Map<String, Filter> filters;

	@Column(name = "model_action", type = ClassUtils.TYPE_JSON, comment = "模型对应的按钮操作,List<ModelAction>的json格式")
	private Map<String, ModelAction> actions;

	@Column(name = "model_comment", length = 255, comment = "模型备注")
	private String comment;

	public Model() {

	}

	public Model(String name, String ds, String table, String pk, Boolean autoIncrement, String comment) {
		this.name = name;
		this.status = Constants.STATUS_VALID;
		this.ds = ds;
		this.table = table;
		this.pk = pk;
		this.autoIncrement = autoIncrement;
		this.comment = comment;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
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

	public Boolean getAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(Boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public String[] getUnique() {
		return unique;
	}

	public void setUnique(String[] unique) {
		this.unique = unique;
	}

	public String[] getIndex() {
		return index;
	}

	public void setIndex(String[] index) {
		this.index = index;
	}

	public Map<String, ModelJoin> getJoins() {
		return joins;
	}

	public void setJoins(Map<String, ModelJoin> joins) {
		this.joins = joins;
	}

	public Map<String, ModelColumn> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, ModelColumn> columns) {
		this.columns = columns;
	}

	public Map<String, Filter> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, Filter> filters) {
		this.filters = filters;
	}

	public Map<String, ModelAction> getActions() {
		return actions;
	}

	public void setActions(Map<String, ModelAction> actions) {
		this.actions = actions;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
