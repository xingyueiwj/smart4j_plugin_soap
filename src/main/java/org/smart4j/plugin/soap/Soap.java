package org.smart4j.plugin.soap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * soap服务注解
 * Created by Administrator on 2017/4/29 0029.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Soap {
    /**
     * 服务名
     */
    String values() default "";
}
