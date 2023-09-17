package com.mjc.school.repository.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Length {
    String constraintViolationErrorMessage = "%s should have length from %d to %d.";

    int min();

    int max();
}
