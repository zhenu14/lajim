package com.ssm.project.service;


public interface RedisService {
    public void saveUserRedPacketByRedis(Long redPacketId,Double unitAmount);
}
