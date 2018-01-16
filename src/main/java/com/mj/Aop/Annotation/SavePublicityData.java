package com.mj.Aop.Annotation;

import java.lang.annotation.*;

/**
 * @author MJ
 * @Description:
 * @Date: create 2018/1/15
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SavePublicityData {
    String value() default "";
}