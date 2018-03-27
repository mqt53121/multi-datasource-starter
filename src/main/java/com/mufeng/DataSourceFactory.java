package com.mufeng;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Component
public class DataSourceFactory {

    @Autowired
    private DataSourceConfig dataSourceConfig;

    private static DataSourceConfig dc;

    @PostConstruct
    public void init(){
        dc = dataSourceConfig;
    }

    public static DataSource getDataSource(DataSourceEnum ds) {
        return new DatasourceWrapper(() -> {
            if(ds.getAppName().equals("master")){
                DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
                driverManagerDataSource.setDriverClassName(dc.getMaster().getDriverClassName());
                driverManagerDataSource.setUrl(dc.getMaster().getUrl());
                driverManagerDataSource.setUsername(dc.getMaster().getUsername());
                driverManagerDataSource.setPassword(dc.getMaster().getPassword());
                return driverManagerDataSource;
            }
            if(ds.getAppName().equals("slaver")){
                DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
                driverManagerDataSource.setDriverClassName(dc.getSlaver().getDriverClassName());
                driverManagerDataSource.setUrl(dc.getSlaver().getUrl());
                driverManagerDataSource.setUsername(dc.getSlaver().getUsername());
                driverManagerDataSource.setPassword(dc.getSlaver().getPassword());
                return driverManagerDataSource;
            }
            return null;
        });
    }

}
