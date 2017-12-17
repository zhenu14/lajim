package com.ssm.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisHashTest {
    public static void main(String[]args){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-redis-hash.xml");
        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
        String key = "hash";
        Object value;
        Map<String,String> map = new HashMap<String, String>();
        map.put("f3","val3");
        map.put("f1","val1");
        map.put("f2","val2");
        redisTemplate.opsForHash().putAll(key,map);
        value = redisTemplate.opsForHash().get(key,"f3");
        System.out.println(value);
        redisTemplate.opsForHash().put(key,"f4","4");
        value = redisTemplate.opsForHash().get(key,"f4");
        System.out.println(value);
        List valueList = redisTemplate.opsForHash().values(key);
        Set keyList = redisTemplate.opsForHash().keys(key);
        System.out.println(valueList.toString());
        System.out.println(keyList.toString());
    }
}
