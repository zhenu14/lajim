package com.ssm.aoptest.proxyGame;

public class ProxyBeanFactory {
    public static <T> T getBean(T obj,Interceptor interceptor){
        return (T)ProxyBeanUtil.getBean(obj,interceptor);
    }
}
