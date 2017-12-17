package com.ssm.chapter11.proxyGame;

public class ProxyBeanFactory {
    public static <T> T getBean(T obj,Interceptor interceptor){
        return (T)ProxyBeanUtil.getBean(obj,interceptor);
    }
}
