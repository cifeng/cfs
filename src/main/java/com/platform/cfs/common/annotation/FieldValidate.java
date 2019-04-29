package com.platform.cfs.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldValidate {
    /**
     * 名称
     * @return
     */
    public String fieldName();
    /**
     *长度验证
     * @return
     */
    public int len() default 0;
    /**
     *QQ验证
     * @return
     */
    public boolean QQ() default false;
    /**
     *手机号验证
     * @return
     */
    public boolean MOBILE() default false;
    /**
     *邮箱验证
     * @return
     */
    public boolean EMAIL() default false;

    /**
     *正则表达式验证
     * @return
     */
    public String regex() default "";

}
