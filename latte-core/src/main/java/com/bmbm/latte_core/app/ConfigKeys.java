package com.bmbm.latte_core.app;

/**
 * 配置的分类
 * Created by wxm on 2017/9/17.
 */

public enum  ConfigKeys {
    API_HOST,   // wxm:配置服务器主机地址
    APPLICATION_CONTEXT,    // wxm:全局上下文
    CONFIG_READY,    // wxm:配置完成状态(true/false)
    ICON,    // wxm:图标配置
    INTERCEPTOR // wxm:okhttp拦截器
    ;
}
