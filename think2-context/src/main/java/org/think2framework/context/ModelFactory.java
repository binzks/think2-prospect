package org.think2framework.context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.think2framework.common.JsonUtils;
import org.think2framework.common.PackageUtils;
import org.think2framework.common.exception.ExistException;
import org.think2framework.common.exception.NonExistException;
import org.think2framework.context.bean.Datasource;
import org.think2framework.context.bean.Model;
import org.think2framework.orm.Query;
import org.think2framework.orm.Writer;
import org.think2framework.orm.persistence.Table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoubin on 16/7/11. 模型工厂
 */
public class ModelFactory {

	private static final Logger logger = LogManager.getLogger(ModelFactory.class);

	private static Map<String, Datasource> datasources = new HashMap<>(); // 数据源map

	private static Map<String, Model> models = new HashMap<>(); // 模型map

	/**
	 * 创建一个模型工厂,先清除数据源和模型,只初始化数据源
	 * 
	 * @param datasourceList
	 *            初始化数据源
	 */
	public static synchronized void build(List<Datasource> datasourceList) {
		int dsSize = datasources.size();
		datasources.clear();
		if (dsSize > 0) {
			logger.debug("Clear " + dsSize + " datasources");
		}
		int modelSize = models.size();
		models.clear();
		if (modelSize > 0) {
			logger.debug("Clear " + modelSize + " models");
		}
		if (null != datasourceList) {
			datasourceList.forEach(ModelFactory::appendDatasource);
		}
		logger.debug(ModelFactory.class.getName() + " build success");
	}

	/**
	 * 创建一个模型工厂,先清除所有数据源和模型,再初始化
	 * 
	 * @param datasourceList
	 *            初始化数据
	 * @param modelList
	 *            初始化模型
	 * @param createTable
	 *            初始化模型是否自动创建表
	 */
	public static synchronized void build(List<Datasource> datasourceList, List<Model> modelList, Boolean createTable) {
		build(datasourceList);
		if (null != modelList) {
			for (Model model : modelList) {
				appendModel(model, createTable);
			}
		}
	}

	/**
	 * 追加一个数据源,如果数据源已存在则警告后忽略
	 * 
	 * @param datasource
	 *            数据源
	 */
	public static synchronized void appendDatasource(Datasource datasource) {
		if (null != datasources.get(datasource.getName())) {
			throw new ExistException(Datasource.class.getName() + " " + datasource.getName());
		}
		datasources.put(datasource.getName(), datasource);
		logger.debug("Append " + Datasource.class.getName() + " " + JsonUtils.toString(datasource));
	}

	/**
	 * 追加一个模型,不创建表,如果模型已经存在则警告后忽略
	 *
	 * @param model
	 *            模型
	 */
	public static synchronized void appendModel(Model model) {
		appendModel(model, false);
	}

	/**
	 * 追加一个模型,如果模型已经存在则警告后忽略
	 * 
	 * @param model
	 *            模型
	 * @param createTable
	 *            追加后是否创建表结构
	 */
	public static synchronized void appendModel(Model model, Boolean createTable) {
		String name = model.getName();
		if (null != models.get(name)) {
			throw new ExistException(Model.class.getName() + " " + name);
		}
		models.put(name, model);
		logger.debug("Append " + Model.class.getName() + " " + model.getName());
		if (createTable) {
			createWriter(name).createTable();
		}
	}

	/**
	 * 扫描一个包,将所有定义了模型的类追加到工厂,默认不创建表
	 * 
	 * @param datasource
	 *            模型对应数据源名称
	 * @param packageDirName
	 *            包名
	 */
	public static synchronized void scanPackage(String datasource, String packageDirName) {
		scanPackage(datasource, false, packageDirName);
	}

	/**
	 * 扫描一个包,将所有定义了模型的类追加到工厂
	 *
	 * @param datasource
	 *            模型对应数据源名称
	 * @param createTable
	 *            是否创建表结构
	 * @param packageDirName
	 *            包名
	 */
	public static synchronized void scanPackage(String datasource, Boolean createTable, String packageDirName) {
		List<Class> list = PackageUtils.scanPackage(packageDirName);
		for (Class clazz : list) {
			if (null != clazz.getAnnotation(Table.class)) {
				appendModel(new Model(clazz, datasource), createTable);
			}
		}
	}

	/**
	 * 扫描多个包,将所有定义了模型的类追加到工厂
	 * 
	 * @param datasource
	 *            模型对应数据源名称
	 * 
	 * @param packageDirNames
	 *            包名
	 */
	public static synchronized void scanPackages(String datasource, String... packageDirNames) {
		scanPackages(datasource, false, packageDirNames);
	}

	/**
	 * 扫描多个包,将所有定义了模型的类追加到工厂
	 *
	 * @param datasource
	 *            模型对应数据源名称
	 * @param createTable
	 *            是否创建表
	 * @param packageDirNames
	 *            包名
	 */
	public static synchronized void scanPackages(String datasource, Boolean createTable, String... packageDirNames) {
		for (String name : packageDirNames) {
			scanPackage(datasource, createTable, name);
		}
	}

	private static Datasource getDatasource(String name) {
		Datasource datasource = datasources.get(name);
		if (null == datasource) {
			throw new NonExistException(Datasource.class.getName() + " " + name);
		}
		return datasource;
	}

	private static Model getModel(String name) {
		Model model = models.get(name);
		if (null == model) {
			throw new NonExistException(Model.class.getName() + " " + name);
		}
		return model;
	}

	/**
	 * 根据模型名称创建一个查询生成器
	 * 
	 * @param name
	 *            模型名称
	 * @return 查询生成器
	 */
	public static Query createQuery(String name) {
		Model model = getModel(name);
		Datasource datasource = getDatasource(model.getDatasource());
		return new Query(model.getEntity(), datasource.getType(), datasource.getQueryJdbcTemplate());
	}

	/**
	 * 根据一个类创建一个查询生成器
	 * 
	 * @param clazz
	 *            类全名
	 * @return 查询生成器
	 */
	public static Query createQuery(Class<?> clazz) {
		return createQuery(clazz.getName());
	}

	/**
	 * 根据模型名称创建一个写入生成器
	 * 
	 * @param name
	 *            模型名称
	 * @return 写入生成器
	 */
	public static Writer createWriter(String name) {
		Model model = getModel(name);
		Datasource datasource = getDatasource(model.getDatasource());
		return new Writer(model.getTable(), datasource.getType(), datasource.getWriterJdbcTemplate());
	}

	/**
	 * 根据一个类创建一个写入生成器
	 * 
	 * @param clazz
	 *            类全名
	 * @return 写入生成器
	 */
	public static Writer createWriter(Class<?> clazz) {
		return createWriter(clazz.getName());
	}

}
