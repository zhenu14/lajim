package com.ssm.project.dao;

import com.ssm.project.pojo.UserRedPacket;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRedPacketDao {
    public int grapRedPacket(UserRedPacket userRedPacket);
}
