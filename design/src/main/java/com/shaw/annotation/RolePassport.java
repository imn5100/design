package com.shaw.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RolePassport {
	boolean validate() default true;
	//权限等级，1：所有登录用户 2：教师及以上权限 3：管理员权限
	int roleLv() default 1;
}
