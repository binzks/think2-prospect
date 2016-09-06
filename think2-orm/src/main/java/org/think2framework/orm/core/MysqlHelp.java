package org.think2framework.orm.core;

import org.think2framework.common.StringUtils;
import org.think2framework.orm.bean.EntityColumn;
import org.think2framework.orm.bean.Filter;
import org.think2framework.orm.bean.Table;
import org.think2framework.orm.bean.TableColumn;

import java.util.List;
import java.util.Map;

/**
 * Created by zhoubin on 16/7/12. mysql
 */
public class MysqlHelp implements SqlHelp {

	@Override
	public String toCreate(Table table) {
		// 基本字段
		StringBuilder sql = new StringBuilder();
		String pk = table.getPk();
		sql.append("CREATE TABLE `").append(table.getName()).append("` (`").append(pk).append("`");
		// 主键
		if (table.getAutoIncrement()) {
			sql.append(" int(11) NOT NULL AUTO_INCREMENT COMMENT '主键'");
		} else {
			sql.append(" varchar(32) NOT NULL COMMENT '主键'");
		}
		// 字段
		for (TableColumn column : table.getColumns()) {
			// 忽略主键
			if (pk.equals(column.getName())) {
				continue;
			}
			sql.append(",").append(getMysqlColumnCreateSql(column));
		}
		// 主键
		sql.append(", PRIMARY KEY (`").append(table.getPk()).append("`)");
		// 唯一性约束
		for (String unique : table.getUniques()) {
			if (StringUtils.isBlank(unique)) {
				continue;
			}
			sql.append(",UNIQUE KEY `unique_").append(table.getName()).append("_").append(unique).append("` (`")
					.append(unique.replace(",", "`,`")).append("`)");
		}
		// 索引
		for (String index : table.getIndexes()) {
			if (StringUtils.isBlank(index)) {
				continue;
			}
			sql.append(",KEY `index_").append(table.getName()).append("_").append(index).append("` (`")
					.append(index.replace(",", "`,`")).append("`)");
		}
		sql.append(")");
		String comment = table.getComment();
		if (StringUtils.isNotBlank(comment)) {
			sql.append(" COMMENT='").append(comment).append("';");
		}
		return sql.toString();
	}

	@Override
	public Object[] toSelect(String table, String joinSql, String fields, List<Filter> filters,
			Map<String, EntityColumn> columns, List<String> group, Map<List<String>, String> order, Integer page,
			Integer size) {
		Object[] result = new Object[2];
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(fields).append(" FROM `").append(table).append("` ").append(SelectHelp.TABLE_ALIAS)
				.append(" ").append(joinSql).append(" WHERE 1=1 ");
		Object[] sqlValues = SelectHelp.generateFilters(filters, columns);
		sql.append(sqlValues[0]);
		result[1] = sqlValues[1];
		if (null != group && group.size() > 0) {
			StringBuilder groupSql = new StringBuilder();
			for (String key : group) {
				groupSql.append(",").append(SelectHelp.getColumnKeySql(key, columns));
			}
			sql.append(" GROUP BY ").append(groupSql.substring(1));
		}
		if (null != order && order.size() > 0) {
			StringBuilder orderSql = new StringBuilder();
			for (Map.Entry<List<String>, String> entry : order.entrySet()) {
				List<String> orders = entry.getKey();
				for (String key : orders) {
					orderSql.append(",").append(SelectHelp.getColumnKeySql(key, columns));
				}
				orderSql.append(" ").append(entry.getValue());
			}
			sql.append(" ORDER BY ").append(orderSql.substring(1));
		}
		if (page >= 0 && size > 0) {
			int begin = (page - 1) * size;
			if (begin < 0) {
				begin = 0;
			}
			sql.append(" LIMIT ").append(begin).append(",").append(size);
		}
		result[0] = sql.toString();
		return result;
	}

	/**
	 * 根据字段定义获取mysql字段创建sql
	 *
	 * @param column
	 *            字段
	 * @return 创建sql
	 */
	private String getMysqlColumnCreateSql(TableColumn column) {
		StringBuilder sql = new StringBuilder();
		sql.append("`").append(column.getName()).append("`");
		String type = column.getType();
		if (ClassUtils.TYPE_STRING.equalsIgnoreCase(type)) {
			sql.append(" varchar(").append(column.getLength()).append(")");
		} else if (ClassUtils.TYPE_INTEGER.equalsIgnoreCase(type) || ClassUtils.TYPE_LONG.equalsIgnoreCase(type)) {
			sql.append(" int(").append(column.getLength()).append(")");
		} else if (ClassUtils.TYPE_BOOLEAN.equalsIgnoreCase(type)) {
			sql.append(" varchar(5)");
		} else if (ClassUtils.TYPE_DOUBLE.equalsIgnoreCase(type) || ClassUtils.TYPE_FLOAT.equalsIgnoreCase(type)) {
			sql.append(" decimal(").append(column.getLength()).append(",").append(column.getScale()).append(")");
		} else if (ClassUtils.TYPE_JSON.equalsIgnoreCase(type) || ClassUtils.TYPE_TEXT.equalsIgnoreCase(type)) {
			sql.append(" text");
		} else {
			sql.append(" varchar(").append(column.getLength()).append(")");
		}
		if (!column.getNullable()) {
			sql.append(" NOT NULL");
		}
		String defaultValue = String.valueOf(column.getDefaultValue());
		if (StringUtils.isNotBlank(defaultValue)) {
			sql.append(" DEFAULT ").append(defaultValue);
		}
		if (StringUtils.isNotBlank(column.getComment())) {
			sql.append(" COMMENT '").append(column.getComment()).append("'");
		}
		return sql.toString();
	}

}
