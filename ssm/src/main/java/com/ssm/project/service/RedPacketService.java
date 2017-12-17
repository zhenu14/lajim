package com.ssm.project.service;

import com.ssm.project.pojo.RedPacket;

public interface RedPacketService {
    public RedPacket getRedPacket(Long id);
    public int decreaseRedPacket(Long id);
    public RedPacket getRedPacketForUpdate(Long id);
    public int decreaseRedPacketForVersion(Long id,int version);
}
