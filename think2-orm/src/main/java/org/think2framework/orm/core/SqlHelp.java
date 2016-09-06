package org.think2framework.orm.core;

import org.think2framework.orm.bean.EntityColumn;
import org.think2framework.orm.bean.Filter;
import org.think2framework.orm.bean.Table;

import java.util.List;
import java.util.Map;

/**
 * Created by zhoubin on 16/7/12. sql语句生成接口
 */
public interface SqlHelp {

	/**
	 * 根据表定义获取mysql创建sql语句
	 *
	 * @param table
	 *            表
	 * @return 创建sql
	 */
	String toCreate(Table table);

	Object[] toSelect(String table, String joinSql, String fields, List<Filter> filters,
			Map<String, EntityColumn> columns, List<String> group, Map<List<String>, String> order, Integer page,
			Integer size);
}
