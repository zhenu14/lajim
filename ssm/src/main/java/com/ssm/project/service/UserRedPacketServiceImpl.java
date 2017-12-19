package com.ssm.project.service;

import com.ssm.project.dao.RedPacketDao;
import com.ssm.project.dao.UserRedPacketDao;
import com.ssm.project.pojo.RedPacket;
import com.ssm.project.pojo.UserRedPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

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
//      时间戳重入乐观锁，100毫秒
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

    @Autowired
    private RedisTemplate redisTemplate = null;

    @Autowired
    private RedisService redisService = null;

    // Lua脚本
    String script = "local listKey = 'red_packet_list_'..KEYS[1] \n"
            + "local redPacket = 'red_packet_'..KEYS[1] \n"
            + "local stock = tonumber(redis.call('hget', redPacket, 'stock')) \n"
            + "if stock <= 0 then return 0 end \n"
            + "stock = stock -1 \n"
            + "redis.call('hset', redPacket, 'stock', tostring(stock)) \n"
            + "redis.call('rpush', listKey, ARGV[1]) \n"
            + "if stock == 0 then return 2 end \n"
            + "return 1 \n";
    // 在缓存LUA脚本后，使用该变量保存Redis返回的32位的SHA1编码，使用它去执行缓存的LUA脚本[加入这句话]
    String sha1 = null;

    @Override
    public Long grapRedPacketByRedis(Long redPacketId, Long userId) {
        // 当前抢红包用户和日期信息
        String args = userId + "-" + System.currentTimeMillis();
        Long result = null;
        // 获取底层Redis操作对象
        Jedis jedis = (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
        try{
            // 如果脚本没有加载过，那么进行加载，这样就会返回一个sha1编码
            if(sha1 == null){
                sha1 = jedis.scriptLoad(script);
            }
            // 执行脚本，返回结果
            Object res = jedis.evalsha(sha1,1,redPacketId+"",args);
            result = (Long)res;
            // 返回2时为最后一个红包，此时将抢红包信息通过异步保存到数据库中
            if(result == 2){
                // 获取单个小红包金额
                String unitAmountStr = jedis.hget("red_packet_" + redPacketId, "unit_amount");
                System.out.println("unitAmountStr：" + unitAmountStr);
                // 触发保存数据库操作
                Double unitAmount = Double.parseDouble(unitAmountStr);
                System.err.println("thread_name = " + Thread.currentThread().getName());
                redisService.saveUserRedPacketByRedis(redPacketId, unitAmount);
            }
        }finally {
            // 确保jedis顺利关闭
            if (jedis != null && jedis.isConnected()) {
                jedis.close();
            }
        }
        return result;
    }
}
