package com.ssm.project.service;

import com.ssm.project.dao.RedPacketDao;
import com.ssm.project.dao.UserRedPacketDao;
import com.ssm.project.pojo.RedPacket;
import com.ssm.project.pojo.UserRedPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRedPacketServiceImpl implements UserRedPacketService{
    @Autowired
    private UserRedPacketDao userRedPacketDao = null;
    @Autowired
    private RedPacketDao redPacketDao = null;
    // 失败
    private static final int FAILED = 0;


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int grapRedPacket(Long redPacketId, Long userId) {
        // 获取红包信息
        // RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);
        // 悲观锁
        RedPacket redPacket = redPacketDao.getRedPacketForUpdate(redPacketId);
        // 当前小红包库存大于0
        if (redPacket.getStock() > 0) {
            redPacketDao.decreaseRedPacket(redPacketId);
            // 生成抢红包信息
            UserRedPacket userRedPacket = new UserRedPacket();
            userRedPacket.setRedPacketId(redPacketId);
            userRedPacket.setUserId(userId);
            userRedPacket.setAmount(redPacket.getUnitAmount());
            userRedPacket.setNote("抢红包 " + redPacketId);
            // 插入抢红包信息
            int result = userRedPacketDao.grapRedPacket(userRedPacket);
            return result;
        }
        // 失败返回
        return FAILED;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int getRedPacketForUpdate(Long redPacketId, Long userId) {
        // 获取红包信息
        // RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);
        // 悲观锁
        RedPacket redPacket = redPacketDao.getRedPacketForUpdate(redPacketId);
        // 当前小红包库存大于0
        if (redPacket.getStock() > 0) {
            redPacketDao.decreaseRedPacket(redPacketId);
            // 生成抢红包信息
            UserRedPacket userRedPacket = new UserRedPacket();
            userRedPacket.setRedPacketId(redPacketId);
            userRedPacket.setUserId(userId);
            userRedPacket.setAmount(redPacket.getUnitAmount());
            userRedPacket.setNote("抢红包 " + redPacketId);
            // 插入抢红包信息
            int result = userRedPacketDao.grapRedPacket(userRedPacket);
            return result;
        }
        // 失败返回
        return FAILED;
    }

    //重试次数3，重入乐观锁
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int grapRedPacketForVersion(Long redPacketId, Long userId) {
        for (int i = 0;i <3;i++){
            RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);
            if (redPacket.getStock() > 0) {
                int update = redPacketDao.decreaseRedPacketForVersion(redPacketId, redPacket.getVersion());
                if (update == 0) {
                    continue;
                }
                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setUserId(userId);
                userRedPacket.setAmount(redPacket.getUnitAmount());
                userRedPacket.setNote("抢红包啊" + redPacketId);
                int result = userRedPacketDao.grapRedPacket(userRedPacket);
                return result;
            }else
                return FAILED;
        }
        return FAILED;
    }

    //时间戳重入乐观锁，100毫秒
//    @Override
//    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//    public int grapRedPacketForVersion(Long redPacketId, Long userId) {
//        long start = System.currentTimeMillis();
//        while (true){
//            long end = System.currentTimeMillis();
//            if(end - start > 100){
//                return FAILED;
//            }
//            RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);
//            if(redPacket.getStock() > 0){
//                int update = redPacketDao.decreaseRedPacketForVersion(redPacketId,redPacket.getVersion());
//                if(update == 0){
//                    return FAILED;
//                }
//                UserRedPacket userRedPacket = new UserRedPacket();
//                userRedPacket.setRedPacketId(redPacketId);
//                userRedPacket.setUserId(userId);
//                userRedPacket.setAmount(redPacket.getAmount());
//                userRedPacket.setNote("抢红包啊" + redPacketId);
//                int result = userRedPacketDao.grapRedPacket(userRedPacket);
//                return result;
//            }else
//                return 0;
//        }
//    }
}
