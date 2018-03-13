package com.example.Annot.AnnotModel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Company {
    /**
     * ��˾ע����
     * @return
     */
    public int id() default -1;

    /**
     * ��˾����
     * @return
     */
    public String name() default "";

    /**
     * ��˾��ַ
     * @return
     */
    public String address() default "";
}
