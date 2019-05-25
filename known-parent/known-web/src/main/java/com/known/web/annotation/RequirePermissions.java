package com.known.web.annotation;
import com.known.common.enums.LogicalEnum;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * 自定义权限注解
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-25 23:32
 */
/**
 * @Retention 在什么级别保存该注解信息
 * RUNTIME 在VM运行期间保存注解
 * 可以通过反射机制读取该注解信息
 */
@Retention(RUNTIME)
/**
 * @Target 表示该注解用在什么地方
 * TYPE 类, 枚举, 接口
 * METHOD 方法声明
 */
@Target({TYPE, METHOD })
public @interface RequirePermissions {
	
	 String[] key() default "";
	 LogicalEnum logical() default LogicalEnum.AND;
	 
}
