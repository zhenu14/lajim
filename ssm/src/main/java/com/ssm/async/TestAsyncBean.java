package com.ssm.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class TestAsyncBean {
    @Async
    public void sayHello() throws InterruptedException {
        Thread.sleep(1 * 1000);//网络连接中 。。。消息发送中。。。
        System.out.println("B:Hello!在的在的有什么事~");
    }

    @Async
    public Future<String> sayHello1() throws InterruptedException {
        int thinking = 1;
        Thread.sleep(thinking * 1000);//网络连接中 。。。消息发送中。。。
        System.out.println("B:Hello!在的在的有什么事~");
        return new AsyncResult<String>("发送消息用了"+thinking+"秒");
    }
}
