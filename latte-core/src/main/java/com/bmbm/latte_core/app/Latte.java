package com.bmbm.latte_core.app;

import android.content.Context;

/**
 * 提供外部Model调用的工具类
 * Created by wxm on 2017/9/17.
 */

public final class Latte {

    // wxm:配置全局上下文
    public static Configurator init(Context context){
        Configurator.getInstance()
                .getConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT,context.getApplicationContext());

        return Configurator.getInstance();
    }

    // wxm:Configurator.getInstance()方法非public，而通过此方法获得Configurator
    public static Configurator getConfigurator(){
        return Configurator.getInstance();
    }

    public static <T> T getConfig(Object key){
        return getConfigurator().getConfig(key);
    }

    public static Context getApplicationContext(){
        return getConfigurator().getConfig(ConfigKeys.APPLICATION_CONTEXT);
    }

}
