package com.ssm.async;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TestAsync {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        test1();
        test2();
    }


    public static void test1()throws InterruptedException, ExecutionException{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-async.xml");
        TestAsyncBean testAsyncBean = applicationContext.getBean(TestAsyncBean.class);
        System.out.println("A:Hello?有人吗吗吗吗吗？");
        testAsyncBean.sayHello();
        System.out.println("A:啊啊啊啊啊啊没人吗？那我挂了啊。。。");
        Thread.sleep(3 * 1000);// 不让主进程过早结束
    }

    public static void test2()throws InterruptedException, ExecutionException{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-async.xml");
        TestAsyncBean testAsyncBean = applicationContext.getBean(TestAsyncBean.class);
        Future<String> future = null;
        System.out.println("A:Hello?有人吗吗吗吗吗？");
        future = testAsyncBean.sayHello1();
        System.out.println("A:啊啊啊啊啊啊没人吗？那我挂了啊。。。");
        Thread.sleep(2 * 1000);// 不让主进程过早结束
        System.out.println(future.get());
    }
}
