package com.ssm.aoptest.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AopMain {
    public static void main(String []args){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AopConfig.class);
        RoleService roleService = ctx.getBean(RoleService.class);
        Role role = new Role(1,"HelloKitty","Noteeeeeeeeeee");
        roleService.printRole(role);
        //测试异常通知
        role = null;
        roleService.printRole(role);
    }
}
