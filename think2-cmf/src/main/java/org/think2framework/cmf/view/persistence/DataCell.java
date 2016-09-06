package org.think2framework.cmf.view.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhoubin on 16/2/23. 列的注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataCell {

	String describe(); // 列描述，在页面显示列名

	String type() default "text"; // 列的类型

	String clazz() default "align-center"; // 列的list页面的css类

	int width() default 0; // 列的宽度

	boolean search() default false; // 是否作为搜索页，默认false，TEXT查询为like

	boolean display() default true; // 查询页面是否显示列，默认true

	boolean detail() default true; // 显示详情页面是否显示列，默认true

	boolean add() default true; // 添加页面是否需要添加列，默认true

	boolean edit() default true; // 编辑页面是否需要列，默认true

	String defaultValue() default ""; // 默认值，now当前时间，user.id登录用户id，user.name登录用户名，其他则填值

	boolean required() default false; // 新增和修改的时候是否必填项，默认false

	boolean rowFilter() default false; // 是否行级过滤，默认false，只有当类型为ITEM或者DATAITEM的时候才有效

}
