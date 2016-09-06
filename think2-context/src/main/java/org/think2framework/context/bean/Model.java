package org.think2framework.context.bean;

import org.think2framework.common.StringUtils;
import org.think2framework.common.exception.NonExistException;
import org.think2framework.common.exception.SimpleException;
import org.think2framework.common.exception.UndefinedException;
import org.think2framework.orm.bean.*;
import org.think2framework.orm.core.SelectHelp;
import org.think2framework.orm.core.SqlHelp;
import org.think2framework.orm.persistence.Column;
import org.think2framework.orm.persistence.JoinTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoubin on 16/7/11. 模型
 */
public class Model {

	private String name; // 模型名称

	private String datasource; // 数据源名称

	private String clazz; // 类名

	private Table table; // 模型的表

	private Entity entity; // 模型的dao实体

	public Model() {

	}

	public Model(String name, String clazz) {
		this.name = name;
		this.clazz = clazz;
	}

	public Model(Class<?> clazz, String datasource) {
		this.name = clazz.getName();
		this.clazz = clazz.getName();
		this.datasource = datasource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public Table getTable() {
		if (null == table) {
			initClazz();
		}
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Entity getEntity() {
		if (null == entity) {
			initClazz();
		}
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	private void initClazz() {
		Class<?> clazz;
		try {
			clazz = Class.forName(this.clazz);
		} catch (ClassNotFoundException e) {
			throw new NonExistException("Append Model Error, Class " + this.clazz);
		}
		org.think2framework.orm.persistence.Table table = clazz
				.getAnnotation(org.think2framework.orm.persistence.Table.class);
		if (null == table) {
			throw new UndefinedException(
					"Class " + clazz.getName() + " " + org.think2framework.orm.persistence.Table.class.getName());
		}
		this.name = clazz.getName();
		List<TableColumn> tableColumns = new ArrayList<>();
		List<Join> joins = new ArrayList<>();
		Map<String, EntityColumn> entityColumns = new LinkedHashMap<>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			Column column = field.getAnnotation(Column.class);
			if (null == column) {
				continue;
			}
			if (StringUtils.isNotBlank(column.name())) {
				name = column.name();
			}
			// 如果字段没有关联名称或者为主表别名则表示为表的字段,如果有关联名称表示关联表,不设置到表
			if (StringUtils.isBlank(column.join()) || column.join().equals(SelectHelp.TABLE_ALIAS)) {
				tableColumns.add(new TableColumn(name, column.type(), column.nullable(), column.length(),
						column.scale(), column.defaultValue(), column.comment()));
			}
			String key = StringUtils.isBlank(column.alias()) ? name : column.alias();
			entityColumns.put(key, new EntityColumn(name, column.join(), column.type(), column.alias()));
		}
		JoinTable[] joinTables = clazz.getDeclaredAnnotationsByType(JoinTable.class);
		for (JoinTable joinTable : joinTables) {
			if (joinTable.name().equals(SelectHelp.TABLE_ALIAS)) {
				throw new SimpleException(SelectHelp.TABLE_ALIAS
						+ " is retained as the main table alias, can not be used as a join name");
			}
			joins.add(new Join(joinTable.name(), joinTable.database(), joinTable.table(), joinTable.type(),
					joinTable.key(), joinTable.joinName(), joinTable.joinKey()));
		}
		this.table = new Table(table.name(), table.pk(), table.autoIncrement(), table.uniques(), table.indexes(),
				table.comment(), tableColumns);
		this.entity = new Entity(table.name(), table.pk(), joins, entityColumns);
	}
}
