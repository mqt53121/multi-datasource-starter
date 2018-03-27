package com.mufeng;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ConfigurationProperties(prefix = "multi.datasource")
public class DataSourceConfig {

    public DataSourceProperty master;

    public DataSourceProperty slaver;

    public static class DataSourceProperty{
        private String url;

        private String username;

        private String password;

        private String driverClassName;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }
    }

    public DataSourceProperty getMaster() {
        return master;
    }

    public void setMaster(DataSourceProperty master) {
        this.master = master;
    }

    public DataSourceProperty getSlaver() {
        return slaver;
    }

    public void setSlaver(DataSourceProperty slaver) {
        this.slaver = slaver;
    }

    //    @Bean(name = "masterDataSource")
//    public DriverManagerDataSource masterDataSource(){
//        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
//        driverManagerDataSource.setDriverClassName(master.getDriverClassName());
//        driverManagerDataSource.setUrl(master.getUrl());
//        driverManagerDataSource.setUsername(master.getUsername());
//        driverManagerDataSource.setPassword(master.getPassword());
//        return driverManagerDataSource;
//    }

//    @Bean(name = "slaverDataSource")
//    public DriverManagerDataSource slaverDataSource(){
//        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
//        driverManagerDataSource.setDriverClassName(slaver.getDriverClassName());
//        driverManagerDataSource.setUrl(slaver.getUrl());
//        driverManagerDataSource.setUsername(slaver.getUsername());
//        driverManagerDataSource.setPassword(slaver.getPassword());
//        return driverManagerDataSource;
//    }
}
