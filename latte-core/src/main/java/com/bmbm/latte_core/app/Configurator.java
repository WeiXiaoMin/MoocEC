package com.bmbm.latte_core.app;

import java.util.HashMap;

/**
 * 管理各个配置项
 * Created by wxm on 2017/9/17.
 */

public final class Configurator {

    // wxm:保存各项配置
    private static final HashMap<Object, Object> LATTE_CONFIGS = new HashMap<>();

    private Configurator() {
        // wxm:单例模式能保证线程安全
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY.name(), false);
    }

    // wxm:静态内部类方式创建单例，实现线程安全的懒加载
    private static class Holder{
        private static final Configurator INSTANCE = new Configurator();
    }

    // wxm:线程安全的懒加载实现原理：类加载是线程安全的，调用getInstance方法第一次使用到Holder
    // wxm:此时Holder类才加载，实现了Configurator单例的懒加载
    static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    final HashMap<Object,Object> getConfigs(){
        return LATTE_CONFIGS;
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfig(Object key){
        checkConfiguration();
        final Object value = LATTE_CONFIGS.get(key);
        if(value == null) {
            throw new NullPointerException(key.toString() + "is null");
        }
        return (T)value;
    }

    // wxm:检查配置是否完成
    private void checkConfiguration(){
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if(!isReady) {
            throw new RuntimeException("配置尚未就绪，请调用configure方法");
        }
    }

    // wxm:配置完成
    public void cofigure(){
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY,true);
    }

    // wxm:配置主机地址
    public  Configurator withApiHost(String host){
        LATTE_CONFIGS.put(ConfigKeys.API_HOST,host);
        return this;
    }

}
