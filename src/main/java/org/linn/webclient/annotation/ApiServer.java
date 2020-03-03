package org.linn.webclient.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//target 注解需要加到那个位置
@Target(ElementType.TYPE)
//运行时生效
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiServer {

    String value() default "";

}
