package com.mufeng;

import java.util.HashMap;
import java.util.Map;

public enum DataSourceEnum {
    CONTEXT_DEFINE,

    // A数据源
    MASTER("master", "master"),

    //B数据源
    SLAVER("slaver", "slaver");

    private String appName;

    private String db;

    private static final Map<String, DataSourceEnum> map = new HashMap<>();

    DataSourceEnum() {
    }

    DataSourceEnum(String appName, String db) {
        this.appName = appName;
        this.db = db;
    }

    static {
        DataSourceEnum[] values = DataSourceEnum.values();
        for(DataSourceEnum ds : values) {
            map.put(ds.name(), ds);
        }
    }

    public String getAppName() {
        return appName;
    }

    public static DataSourceEnum ofName(String name) {
        return map.get(name);
    }
}
