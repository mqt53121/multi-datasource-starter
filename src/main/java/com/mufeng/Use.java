package com.mufeng;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Use {
    DataSourceEnum value() default DataSourceEnum.CONTEXT_DEFINE;

    /**
     * dataSource格式：@Use(dataSource = "${dataSourceName}")
     * 需要在spring配置文件中指定，dataSourceName=XXX
     * 其中XXX取值范围为DataSource的所有枚举。
     *
     * 重要：此配置将覆盖value中指定的配置！！！
     */
    String dataSource() default "";
}
