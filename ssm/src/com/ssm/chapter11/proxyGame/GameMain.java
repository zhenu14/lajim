package com.ssm.chapter11.proxyGame;

public class GameMain {
    public static void main(String []args){
        RoleService roleService = new RoleServiceImpl();
        Interceptor interceptor = new RoleInterceptor();
        RoleService proxy = (RoleService) ProxyBeanUtil.getBean(roleService,interceptor);
        Role role = new Role();
        proxy.Hello();
//        Role role = new Role(1,"god","hello?");
//        proxy.printRole(role);
//
//        System.out.print("######测试######异常######");
//        role = null;
//        proxy.printRole(role);

    }
}
