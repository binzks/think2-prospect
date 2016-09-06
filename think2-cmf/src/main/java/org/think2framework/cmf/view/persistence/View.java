package org.think2framework.cmf.view.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhoubin on 16/2/23. 视图注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface View {

	String title(); // 视图标题

	int rowSize() default 10; // view每页显示行数，默认10

	int optWidth() default 0; // view的操作列的宽度

}
