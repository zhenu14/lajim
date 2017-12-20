package com.ssm.async.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class AsyncServiceImpl implements AsyncService{
    @Autowired
    RedisTemplate redisTemplate = null;

    @Override
//    @Async
    public void asyncMethod(String cacheKey) throws Exception {
        //模拟总有20个步骤，每个步骤耗时2秒
        int maxStep = 20;
        for (int i = 0; i < maxStep; i++) {
//            Thread.sleep(2000);
            //将执行进度放入缓存
            redisTemplate.opsForValue().set(cacheKey, (i + 1) + "/" + maxStep);
        }
    }

    @Override
    public String getProcess(String cacheKey) throws Exception {
        return (String) redisTemplate.opsForValue().get(cacheKey);
    }

    @Override
    public void clearCache(String cacheKey) throws Exception {
        //完成后，清空缓存
        redisTemplate.delete(cacheKey);
    }
}
