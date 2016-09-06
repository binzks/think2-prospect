package org.think2framework.cmf.support;

/**
 * Created by zhoubin on 16/8/17. 系统常量
 */
public class Constants {

	public static final String STATUS_GROUP = "status"; // 通用状态常量分组名称

	public static final Integer STATUS_VALID = 0; // 通用状态,审核通过

	public static final Integer STATUS_NEW = 1; // 通用状态,新建

	public static final Integer STATUS_DELETE = 99; // 通用状态,已删除

	public static final Integer MODULE_TYPE_GROUP = 0; // 系统模块表,状态字段,0-表示分组

	public static final Integer MODULE_TYPE_NODE = 1; // 系统模块表,状态字段,1-表示节点

}
