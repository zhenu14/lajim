package com.ssm.project.dao;

import com.ssm.project.pojo.RedPacket;
import org.springframework.stereotype.Repository;

@Repository
public interface RedPacketDao {
    public RedPacket getRedPacket(Long id);
    public int decreaseRedPacket(Long id);
    public RedPacket getRedPacketForUpdate(Long id);
    public int decreaseRedPacketForVersion(Long id,int version);
}
