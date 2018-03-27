package com.mufeng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//标识为spring自动配置类
@Configuration
//优先加载，免得mybatis找不到
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
//凡是这个包下面的mapper，都可以直接扫描到
@MapperScan({"com.mufeng.dao"})
//可以实现AbstractRoutingDataSource，因为它已经实现了偷梁换柱的功能
public class RoutingDataSource extends AbstractRoutingDataSource {

    @Autowired
    private DataSource masterDataSource;

    //使用ThreadLocal存储使用哪个数据源
    private static final ThreadLocal<String> DATA_SOURCE_KEY = new InheritableThreadLocal<>();

    //设置所有的数据源
    public RoutingDataSource() {
        Map<Object, Object> map = getAllDataSources();
        setTargetDataSources(map);
        setDefaultTargetDataSource(map.get(DataSourceEnum.MASTER.name()));
        afterPropertiesSet();
    }

    //暴露出静态方法，可以让用户在程序中指定数据源
    public static void setDataSource(DataSourceEnum dataSource) {
        if (DataSourceEnum.CONTEXT_DEFINE.equals(dataSource)) {
            return;
        }
        DATA_SOURCE_KEY.set(dataSource == null ? null : dataSource.name());
    }

    //这里就是找数据源的地方
    @Override
    protected Object determineCurrentLookupKey() {
        return DATA_SOURCE_KEY.get();
    }

    private Map<Object, Object> getAllDataSources() {
        return Stream.of(DataSourceEnum.values()).collect(Collectors.toMap(Enum::toString, DataSourceFactory::getDataSource));
    }

}
