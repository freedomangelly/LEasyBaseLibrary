package com.lightyear.leasybase.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by freed on 2019/2/9.
 *@Target代表Annotation的位置 FIELD代表属性 TYPE放在类上 构造函数上
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)//代表什么时候生效 class编译时 Runtime运行时 source源码资源时
public @interface CheckNet {
    //@ViewById(XXX)
    int value();
}
