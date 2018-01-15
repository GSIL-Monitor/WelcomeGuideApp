package com.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by fqzhang on 2018/1/15.
 */
@Target({ElementType.FIELD,ElementType.TYPE,ElementType.METHOD})
public @interface InjectPrint {
    String value();
}
