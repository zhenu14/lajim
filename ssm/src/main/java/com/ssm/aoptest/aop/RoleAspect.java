package com.ssm.aoptest.aop;

import org.aspectj.lang.annotation.*;

@Aspect
public class RoleAspect {
    @Pointcut
    public void print(){

    }

    @Before("execution(* com.ssm.aoptest.aop.RoleServiceImpl.printRole(..))")
    public void Before(){
        System.out.println("#Before.......");
    }

    @After("execution(* com.ssm.aoptest.aop.RoleServiceImpl.printRole(..))")
    public void After(){
        System.out.println("#After.......");
    }

    @AfterReturning("execution(* com.ssm.aoptest.aop.RoleServiceImpl.printRole(..))")
    public void AfterReturning(){
        System.out.println("#AfterReturning.......");
    }

    @AfterThrowing("execution(* com.ssm.aoptest.aop.RoleServiceImpl.printRole(..))")
    public void AfterThrowing(){
        System.out.println("#AfterThrowing.......");
    }
}
