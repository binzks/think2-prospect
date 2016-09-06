package org.think2framework.cmf.support;

import org.think2framework.cmf.bean.*;
import org.think2framework.common.EncryptUtils;
import org.think2framework.context.ConstantFactory;
import org.think2framework.context.ModelFactory;
import org.think2framework.context.bean.Constant;
import org.think2framework.context.bean.Datasource;
import org.think2framework.orm.Query;
import org.think2framework.orm.Writer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoubin on 16/8/18. 系统初始化
 */
public class CmfSystem {

	/**
	 * 初始化cmf系统
	 * 
	 * @param datasource
	 *            默认数据源
	 * @param systemName
	 *            系统名称
	 * @param systemAbbreviation
	 *            系统简称
	 * @param systemProfile
	 *            系统说明
	 * @param systemVersion
	 *            系统版本号
	 */
	public void build(Datasource datasource, String systemName, String systemAbbreviation, String systemProfile,
			String systemVersion) {
		ConstantFactory
				.append(new Constant("valid", "系统名称", Constants.STATUS_GROUP, Constants.STATUS_VALID.toString()));
		ConstantFactory
				.append(new Constant("delete", "系统名称", Constants.STATUS_GROUP, Constants.STATUS_DELETE.toString()));
		ConstantFactory.append(new Constant("new", "系统名称", Constants.STATUS_GROUP, Constants.STATUS_NEW.toString()));
		ConstantFactory.append(new Constant("name", "系统名称", "system", systemName));
		ConstantFactory.append(new Constant("abbreviation", "系统简称", "system", systemAbbreviation));
		ConstantFactory.append(new Constant("profile", "系统描述", "system", systemProfile));
		ConstantFactory.append(new Constant("version", "系统版本", "system", systemVersion));
		ModelFactory.appendDatasource(datasource);
		ModelFactory.scanPackage(datasource.getName(), Admin.class.getPackage().getName());
		Writer moduleWriter = ModelFactory.createWriter(Module.class);
		if (moduleWriter.createTable()) {
			List<Module> modules = new ArrayList<>();
			modules.add(new Module("系统管理", 0, Constants.STATUS_VALID, Constants.MODULE_TYPE_GROUP, "#", "fa fa-desktop",
					1, "", "系统管理模块组"));
			modules.add(new Module("模型管理", 1, Constants.STATUS_VALID, Constants.MODULE_TYPE_NODE, "/tpl/list", "", 1,
					Model.class.getName(), "模型管理模块"));
			modules.add(new Module("模块管理", 1, Constants.STATUS_VALID, Constants.MODULE_TYPE_NODE, "/tpl/list", "", 2,
					Module.class.getName(), "模块管理模块"));
			modules.add(new Module("角色管理", 1, Constants.STATUS_VALID, Constants.MODULE_TYPE_NODE, "/tpl/list", "", 3,
					AdminRole.class.getName(), "角色管理模块"));
			modules.add(new Module("管理员", 1, Constants.STATUS_VALID, Constants.MODULE_TYPE_NODE, "/tpl/list", "", 4,
					Admin.class.getName(), "管理员管理模块"));
			moduleWriter.batchInsert(modules);
		}
		Writer roleWriter = ModelFactory.createWriter(AdminRole.class);
		if (roleWriter.createTable()) {
			Map<String, AdminRolePermissions> roleModules = new LinkedHashMap<>();
			Query query = ModelFactory.createQuery(Module.class);
			query.asc("module_order");
			List<Module> modules = query.queryForList(Module.class);
			for (Module module : modules) {
				roleModules.put(module.getId().toString(), new AdminRolePermissions());
			}
			roleWriter.insert(new AdminRole("root", "超级管理员", Constants.STATUS_VALID, roleModules, "系统初始化的超级管理员角色"));
		}
		Writer adminWriter = ModelFactory.createWriter(Admin.class);
		if (adminWriter.createTable()) {
			adminWriter.save(new Admin("root", "超级管理员", EncryptUtils.md5(EncryptUtils.md5("ks215300")), "15962663095",
					"zhoubin@wiseks.net", 1, Constants.STATUS_VALID, "系统初始化超级管理员"));
		}
	}
}
