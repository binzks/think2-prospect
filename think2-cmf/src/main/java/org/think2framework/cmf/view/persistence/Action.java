package org.think2framework.cmf.view.persistence;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zhoubin on 16/2/23. 视图操作按钮注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = Actions.class)
public @interface Action {

	String name() default "";// 操作按钮的名称，用于按钮授权，本view唯一

	String[] attribute() default ""; // 操作按钮的html标签属性

	String title() default ""; // 操作按钮标题

	String clazz() default ""; // 按钮的css类

	String type(); // 按钮类型0-表示view级别的按钮 1-表示每行数据的按钮

	String href() default ""; // 按钮的href

	String comment() default ""; // 按钮的注释

}
