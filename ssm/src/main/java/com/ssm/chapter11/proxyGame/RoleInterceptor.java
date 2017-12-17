package com.ssm.chapter11.proxyGame;

public class RoleInterceptor implements  Interceptor {
    @Override
    public void before(Object obj) {
        System.out.println("#准备打印信息");
    }

    @Override
    public void after(Object obj) {
        System.out.println("#已经打印完成信息");
    }

    @Override
    public void afterReturning(Object obj) {
        System.out.println("#after returnning");
    }

    @Override
    public void afterThrowing(Object obj) {
        System.out.println("#打印异常信息");
    }
}
