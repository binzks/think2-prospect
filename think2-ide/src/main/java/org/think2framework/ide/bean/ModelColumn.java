package org.think2framework.ide.bean;

/**
 * Created by zhoubin on 16/2/16. 模型字段bean
 */
public class ModelColumn {

	private String name; // 字段名称

	private String joinName; // 关联名称,如果为null或者空表示主表字段

	private String alias; // 列别名,主要是关联情况下重名字段使用,用于数据查询后获取值,sql的as名称

	private String expression; // 字段表达式,如果存在则生成查询sql的时候表达式的?使用字段代替

	private String dataType; // 列的数据类型,对应FieldEnum

	private Integer size; // 字段长度

	private Integer decimal; // 字段如果是小数,这个是小数位数

	private Boolean nullable; // 字段是否可空

	private String value; // 字段对应的值

	private String comment; // 字段注释

	private String describe; // 列的显示名称

	private String type; // 列的类型,对应ColumnEnum

	private String clazz; // 列的css

	private Integer width; // 列的宽度

	private boolean search; // 是否作为搜索页，默认false，TEXT查询为like

	private boolean display; // 查询页面是否显示列，默认true

	private boolean detail; // 显示详情页面是否显示列，默认true

	private boolean add; // 添加页面是否需要添加列，默认true

	private boolean edit; // 编辑页面是否需要列，默认true

	private String defaultValue; // 默认值，now当前时间，user.id登录用户id，user.name登录用户名，其他则填值

	private boolean required; // 新增和修改的时候是否必填项，默认false

	private boolean rowFilter; // 是否行级过滤，默认false，只有当类型为ITEM或者DATAITEM的时候才有效

	public ModelColumn() {

	}

	public ModelColumn(String name, String joinName, String alias, String expression, String dataType, Integer size,
			Integer decimal, Boolean nullable, String value, String comment, String describe, String type, String clazz,
			Integer width, boolean search, boolean display, boolean detail, boolean add, boolean edit,
			String defaultValue, boolean required, boolean rowFilter) {
		this.name = name;
		this.joinName = joinName;
		this.alias = alias;
		this.expression = expression;
		this.dataType = dataType;
		this.size = size;
		this.decimal = decimal;
		this.nullable = nullable;
		this.value = value;
		this.comment = comment;
		this.describe = describe;
		this.type = type;
		this.clazz = clazz;
		this.width = width;
		this.search = search;
		this.display = display;
		this.detail = detail;
		this.add = add;
		this.edit = edit;
		this.defaultValue = defaultValue;
		this.required = required;
		this.rowFilter = rowFilter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJoinName() {
		return joinName;
	}

	public void setJoinName(String joinName) {
		this.joinName = joinName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getDecimal() {
		return decimal;
	}

	public void setDecimal(Integer decimal) {
		this.decimal = decimal;
	}

	public Boolean getNullable() {
		return nullable;
	}

	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
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

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public boolean isDetail() {
		return detail;
	}

	public void setDetail(boolean detail) {
		this.detail = detail;
	}

	public boolean isAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isRowFilter() {
		return rowFilter;
	}

	public void setRowFilter(boolean rowFilter) {
		this.rowFilter = rowFilter;
	}
}
