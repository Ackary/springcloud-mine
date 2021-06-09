package com.aking.annotation;

import java.lang.annotation.*;

/**
 * @author ak
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface HandleResult {

    String desc() default "create17";

}
