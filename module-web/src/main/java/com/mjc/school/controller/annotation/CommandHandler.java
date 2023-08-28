package com.mjc.school.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE,ElementType.METHOD})
public @interface CommandHandler {
    String mainEntityClass() default "";
    RequestMethod[] method() default {};

    enum RequestMethod {

        GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE

    }
}
