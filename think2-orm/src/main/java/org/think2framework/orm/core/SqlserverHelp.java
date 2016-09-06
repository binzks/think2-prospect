package org.think2framework.orm.core;

import org.think2framework.orm.bean.EntityColumn;
import org.think2framework.orm.bean.Filter;
import org.think2framework.orm.bean.Table;

import java.util.List;
import java.util.Map;

/**
 * Created by zhoubin on 16/7/12. sqlserver
 */
public class SqlserverHelp implements SqlHelp {

	@Override
	public String toCreate(Table table) {
		return null;
	}

	@Override
	public Object[] toSelect(String table, String joinSql, String fields, List<Filter> filters,
			Map<String, EntityColumn> columns, List<String> group, Map<List<String>, String> order, Integer page,
			Integer size) {
		return new Object[0];
	}
}
