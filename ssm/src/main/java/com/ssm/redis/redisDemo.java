package com.ssm.redis;

import redis.clients.jedis.Jedis;

public class redisDemo {
    public static void main(String[] args){
        Jedis jedis = new Jedis("127.0.0.1",6379,100000);
        int i =0;
        try{
            long start = System.currentTimeMillis();
            while (true){
                long end = System.currentTimeMillis();
                if(end - start >= 1000){
                    break;
                }
                i++;
                jedis.set("test" + i,i + "");
            }
        }finally {
            jedis.close();
        }
        System.out.println("redis每秒操作 " + i + "次");
    }
}
