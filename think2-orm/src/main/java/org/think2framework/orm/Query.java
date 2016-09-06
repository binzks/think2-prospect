package org.think2framework.orm;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.think2framework.common.exception.NonsupportException;
import org.think2framework.orm.bean.Entity;
import org.think2framework.orm.bean.Filter;
import org.think2framework.orm.core.*;
import org.think2framework.orm.core.ClassUtils;

/**
 * Created by zhoubin on 16/2/29. 查询生成器
 */
public class Query {

	private static final Logger logger = LogManager.getLogger(Query.class);

	private List<Filter> filters; // 过滤条件

	private int page; // 分页第几页

	private int size; // 每页大小

	private List<String> group; // 分组字段

	private Map<List<String>, String> order; // 排序

	private Entity entity; // 查询实体

	private JdbcTemplate jdbcTemplate; // spring JdbcTemplate

	private SqlHelp sqlHelp; // sql生成

	public Query(Entity entity, String type, JdbcTemplate jdbcTemplate) {
		this.entity = entity;
		this.jdbcTemplate = jdbcTemplate;
		this.filters = new ArrayList<>();
		this.order = new LinkedHashMap<>();
		if ("mysql".equalsIgnoreCase(type)) {
			sqlHelp = new MysqlHelp();
		} else if ("oracle".equalsIgnoreCase(type)) {
			sqlHelp = new OracleHelp();
		} else if ("sqlite".equalsIgnoreCase(type)) {
			sqlHelp = new SqliteHelp();
		} else if ("sqlserver".equalsIgnoreCase(type)) {
			sqlHelp = new SqlserverHelp();
		} else {
			throw new NonsupportException("Database type " + type);
		}
	}

	/**
	 * 获取数量
	 *
	 * @return 数量
	 */
	public int queryForCount() {
		Object[] sqlValues = this.sqlHelp.toSelect(entity.getTable(), entity.getJoinSql(), "COUNT(*)", this.filters,
				entity.getColumns(), group, order, page, size);
		Integer result = queryForObject((String) sqlValues[0], Integer.class, (Object[]) sqlValues[1]);
		if (null == result) {
			result = 0;
		}
		return result;
	}

	/**
	 * 获取单体map数据
	 *
	 * @return map数据
	 */
	public Map<String, Object> queryForMap() {
		Object[] sqlValues = this.sqlHelp.toSelect(entity.getTable(), entity.getJoinSql(), entity.getColumnSql(),
				this.filters, entity.getColumns(), group, order, page, size);
		return queryForMap((String) sqlValues[0], (Object[]) sqlValues[1]);
	}

	/**
	 * 获取单体对象数据
	 *
	 * @param requiredType
	 *            数据对象类
	 * @return 数据对象
	 */
	public <T> T queryForObject(Class<T> requiredType) {
		Object[] sqlValues = this.sqlHelp.toSelect(entity.getTable(), entity.getJoinSql(), entity.getColumnSql(),
				this.filters, entity.getColumns(), group, order, page, size);
		return queryForObject((String) sqlValues[0], (Object[]) sqlValues[1], requiredType);
	}

	/**
	 * 获取数据对象数组
	 *
	 * @param elementType
	 *            数据对象类
	 * @return 数据数组
	 */
	public <T> List<T> queryForList(Class<T> elementType) {
		Object[] sqlValues = this.sqlHelp.toSelect(entity.getTable(), entity.getJoinSql(), entity.getColumnSql(),
				this.filters, entity.getColumns(), group, order, page, size);
		return queryForList((String) sqlValues[0], (Object[]) sqlValues[1], elementType);
	}

	/**
	 * 获取map数据数组
	 *
	 * @return map数组
	 */
	public List<Map<String, Object>> queryForList() {
		Object[] sqlValues = this.sqlHelp.toSelect(entity.getTable(), entity.getJoinSql(), entity.getColumnSql(),
				this.filters, entity.getColumns(), group, order, page, size);
		return queryForList((String) sqlValues[0], (Object[]) sqlValues[1]);
	}

	/**
	 * 执行一个自定义查询,获取简单字段对象(String Integer等)
	 * 
	 * @param sql
	 *            sql语句
	 * @param requiredType
	 *            类型
	 * @param args
	 *            参数
	 * @return 参数对象
	 */
	public <T> T queryForObject(String sql, Class<T> requiredType, Object... args) {
		logger.debug("queryForObject sql: {} values: {} requiredType: {}", sql, args, requiredType);
		try {
			return this.jdbcTemplate.queryForObject(sql, requiredType, args);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * 执行一个自定义sql,获取单体类对象
	 * 
	 * @param sql
	 *            sql语句
	 * @param args
	 *            参数值
	 * @param requiredType
	 *            类
	 * @return 类对象
	 */
	public <T> T queryForObject(String sql, Object[] args, Class<T> requiredType) {
		logger.debug("queryForObject sql: {} values: {} requiredType: {}", sql, args, requiredType);
		try {
			return this.jdbcTemplate.query(sql, args, rs -> {
				rs.next();
				return ClassUtils.createInstance(requiredType, rs);
			});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * 执行一个自定义sql,获取map对象
	 * 
	 * @param sql
	 *            sql语句
	 * @param args
	 *            参数值
	 * @return map
	 */
	public Map<String, Object> queryForMap(String sql, Object... args) {
		logger.debug("queryForMap sql: {} values: {}", sql, args);
		try {
			return this.jdbcTemplate.queryForMap(sql, args);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * 执行一个自定义sql,获取对象数组
	 * 
	 * @param sql
	 *            sql语句
	 * @param args
	 *            参数值
	 * @param elementType
	 *            类
	 * @return 类对象数组
	 */
	public <T> List<T> queryForList(String sql, Object[] args, Class<T> elementType) {
		logger.debug("queryForList Object sql: {} values: {} elementType: {}", sql, args, elementType);
		try {
			List<T> list = this.jdbcTemplate.query(sql, args, (rs, rowNum) -> {
				return ClassUtils.createInstance(elementType, rs);
			});
			return list;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * 执行一个自定义sql,获取map数组
	 * 
	 * @param sql
	 *            sql语句
	 * @param args
	 *            参数
	 * @return map数组
	 */
	public List<Map<String, Object>> queryForList(String sql, Object... args) {
		logger.debug("queryForList Map sql: {} values: {}", sql, args);
		try {
			return this.jdbcTemplate.queryForList(sql, args);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * 清空所有查询设置
	 */
	public void clear() {
		this.filters.clear();
	}

	/**
	 * 添加分页
	 *
	 * @param page
	 *            第几页
	 * @param size
	 *            每页大小
	 */
	public void page(int page, int size) {
		this.page = page;
		this.size = size;
	}

	/**
	 * 添加group by条件，没有顺序，并且字段名不能重复,多次调用以最后一次为准
	 *
	 * @param keys
	 *            group by字段名称
	 */
	public void group(String... keys) {
		this.group = Arrays.asList(keys);
	}

	/**
	 * 添加倒叙排序，按照添加先后顺序排序
	 *
	 * @param keys
	 *            order by字段名称
	 */
	public void desc(String... keys) {
		this.order.put(Arrays.asList(keys), "DESC");
	}

	/**
	 * 添加正序排序，按照添加先后顺序排序
	 *
	 * @param keys
	 *            order by字段名称
	 */
	public void asc(String... keys) {
		this.order.put(Arrays.asList(keys), "ASC");
	}

	/**
	 * 追加一个过滤条件
	 *
	 * @param key
	 *            过滤字段名称
	 * @param type
	 *            过滤类型
	 * @param values
	 *            过滤值
	 */
	public void filter(String key, String type, Object... values) {
		this.filters.add(new Filter(key, type, Arrays.asList(values)));
	}

	/**
	 * and主键等于条件
	 *
	 * @param value
	 *            过滤值
	 */
	public void eq(Object value) {
		this.filters.add(new Filter(entity.getPk(), "=", Arrays.asList(value)));
	}

	/**
	 * and等于过滤条件
	 *
	 * @param key
	 *            过滤字段名
	 * @param value
	 *            过滤值
	 */
	public void eq(String key, Object value) {
		this.filters.add(new Filter(key, "=", Arrays.asList(value)));
	}

	/**
	 * and不等于过滤条件
	 *
	 * @param key
	 *            过滤字段名
	 * @param value
	 *            过滤值
	 */
	public void ne(String key, Object value) {
		this.filters.add(new Filter(key, "<>", Arrays.asList(value)));
	}

	/**
	 * and大于过滤条件
	 *
	 * @param key
	 *            过滤字段名
	 * @param value
	 *            过滤值
	 */
	public void gt(String key, Object value) {
		this.filters.add(new Filter(key, ">", Arrays.asList(value)));
	}

	/**
	 * and大于等于过滤条件
	 *
	 * @param key
	 *            过滤字段名
	 * @param value
	 *            过滤值
	 */
	public void ge(String key, Object value) {
		this.filters.add(new Filter(key, ">=", Arrays.asList(value)));
	}

	/**
	 * and小于过滤条件
	 *
	 * @param key
	 *            过滤字段名
	 * @param value
	 *            过滤值
	 */
	public void lt(String key, Object value) {
		this.filters.add(new Filter(key, "<", Arrays.asList(value)));
	}

	/**
	 * and小于等于过滤条件
	 *
	 * @param key
	 *            过滤字段名
	 * @param value
	 *            过滤值
	 */
	public void le(String key, Object value) {
		this.filters.add(new Filter(key, "<=", Arrays.asList(value)));
	}

	/**
	 * and null过滤条件
	 *
	 * @param key
	 *            过滤字段名
	 */
	public void isNull(String key) {
		this.filters.add(new Filter(key, "is null", null));
	}

	/**
	 * and not null过滤条件
	 *
	 * @param key
	 *            过滤字段名
	 */
	public void notNull(String key) {
		this.filters.add(new Filter(key, "is not null", null));
	}

	/**
	 * and in过滤条件
	 *
	 * @param key
	 *            过滤字段名
	 * @param values
	 *            过滤值
	 */
	public void in(String key, Object... values) {
		this.filters.add(new Filter(key, "in", Arrays.asList(values)));
	}

	/**
	 * and not in过滤条件
	 *
	 * @param key
	 *            过滤字段名
	 * @param values
	 *            过滤值
	 */
	public void notIn(String key, Object... values) {
		this.filters.add(new Filter(key, "not in", Arrays.asList(values)));
	}

	/**
	 * and between过滤条件
	 *
	 * @param key
	 *            过滤字段名
	 * @param begin
	 *            开始值
	 * @param end
	 *            结束值
	 */
	public void between(String key, Object begin, Object end) {
		this.filters.add(new Filter(key, "between", Arrays.asList(begin, end)));
	}

	/**
	 * and like过滤条件，过滤值两边都加上%过滤
	 *
	 * @param key
	 *            过滤字段名
	 * @param value
	 *            过滤值
	 */
	public void like(String key, Object value) {
		this.filters.add(new Filter(key, "like", Arrays.asList(value)));
	}

	/**
	 * and not like过滤条件，过滤值两边都加上%过滤
	 *
	 * @param key
	 *            过滤字段名
	 * @param value
	 *            过滤值
	 */
	public void notLike(String key, Object value) {
		this.filters.add(new Filter(key, "not like", Arrays.asList(value)));
	}

	/**
	 * and like过滤条件，左匹配
	 *
	 * @param key
	 *            过滤字段名
	 * @param value
	 *            过滤值
	 */
	public void leftLike(String key, Object value) {
		this.filters.add(new Filter(key, "like", Arrays.asList("%" + value)));
	}

	/**
	 * and like过滤条件，右匹配
	 *
	 * @param key
	 *            过滤字段名
	 * @param value
	 *            过滤值
	 */
	public void rightLike(String key, Object value) {
		this.filters.add(new Filter(key, "like", Arrays.asList(value + "%")));
	}

}
