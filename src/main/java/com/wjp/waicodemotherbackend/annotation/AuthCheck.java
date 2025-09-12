package com.wjp.waicodemotherbackend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 */
@Target(ElementType.METHOD) // 作用于方法，无法用于类、字段等其他元素
@Retention(RetentionPolicy.RUNTIME) // 运行时保留，供AOP反射读取
public @interface AuthCheck {

    /**
     * 必须有某个角色
     * @return
     */
    String mustRole() default "";

}


