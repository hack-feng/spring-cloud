package com.maple.common.core.config;

public class GlobalConfig {

    /**
     * 日志-日志类型
     */
    public static enum logTypeEnum{
        //登录
        LOGIN,
        //接口
        INTERFACE,
        //业务
        BUSINESS

    }

    /**
     * 日志-操作类型
     */
    public static enum operTypeEnum{
        SELECT,
        INSERT,
        UPDATE,
        DELETE
    }
}
