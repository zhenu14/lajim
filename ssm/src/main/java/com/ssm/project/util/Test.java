package com.ssm.project.util;

import com.ssm.project.dao.RedPacketDao;
import com.ssm.project.pojo.RedPacket;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Timestamp;

public class Test {
    public static void main(String[] args){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        RedPacketDao redPacketDao = applicationContext.getBean(RedPacketDao.class);
        RedPacket redPacket = new RedPacket();
        redPacket.setUserId(new Long(2));
        redPacket.setSendDate(new Timestamp(System.currentTimeMillis()) );
        redPacket.setAmount(1000.0);
        redPacket.setTotal(1000);
        redPacket.setUnitAmount(10.0);
        redPacket.setStock(1);
        redPacket.setVersion(1);
        redPacket.setNote("123");
        redPacketDao.decreaseRedPacket(new Long(1));
    }
}
