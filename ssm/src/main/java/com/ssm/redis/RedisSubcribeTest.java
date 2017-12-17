package com.ssm.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisSubcribeTest {
    public static void main(String[] args){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-redis-subcribe.xml");
        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
        String channel = "chat";
        redisTemplate.convertAndSend(channel,"hello i'm a shuaige");
    }
}
