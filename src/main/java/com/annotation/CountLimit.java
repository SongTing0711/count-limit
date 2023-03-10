package com.annotation;

import com.core.CountLimitCommonUtil;

import java.lang.annotation.*;

/**
 * 接口限流
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CountLimit {
    String objectName();

    String paramName() default "";

    int limit() default 10000;

    int waitTime() default 2;

    String countFactoryEnum() default CountLimitCommonUtil.LOCAL_LOCK_STORE;
}