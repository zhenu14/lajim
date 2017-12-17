package com.ssm.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisTemplateTest {
    public static void main(String[] args){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-redis-template.xml");
        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
        Role role = new Role();
        role.setId(1L);
        role.setRoleName("hhh");
        role.setNote("qwe");
        redisTemplate.opsForValue().set("role1",role);
        Role role1 = (Role)redisTemplate.opsForValue().get("role1");
        System.out.println(role1.getRoleName());
    }

}
