package com.ssm.project.service;

import com.ssm.project.dao.RedPacketDao;
import com.ssm.project.pojo.RedPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RedPacketServiceImpl implements RedPacketService {
    @Autowired
    private RedPacketDao redPacketDao = null;

    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public RedPacket getRedPacket(Long id) {
        return redPacketDao.getRedPacket(id);
    }

    @Override
    @Transactional(isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int decreaseRedPacket(Long id) {
        return redPacketDao.decreaseRedPacket(id);
    }

    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public RedPacket getRedPacketForUpdate(Long id) {
        return redPacketDao.getRedPacketForUpdate(id);
    }

    @Override
    public int decreaseRedPacketForVersion(Long id, int version) {
        return redPacketDao.decreaseRedPacketForVersion(id,version);
    }
}
