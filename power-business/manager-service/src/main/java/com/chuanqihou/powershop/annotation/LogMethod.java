package com.chuanqihou.powershop.annotation;

import java.lang.annotation.*;

/**
 * @author 传奇后
 * @date 2023/6/26 19:09
 * @description 日志注解（方法级别）
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogMethod {

    /**
     * 日志描述
     * @return 日志描述
     */
    String value();

}
