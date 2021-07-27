package com.lightyear.leasybase.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Created by freed
 * Created by freed on 2019/2/24.
 * Date:2019/2/24
 * @description
 */
@Target(ElementType.METHOD) //放在什么位置 ElementType.MEIHOD
@Retention(RetentionPolicy.RUNTIME) //编译时检测 还是 运行时检测
public @interface PermissionSuccess {
    public int requestCode();//请求码
}
