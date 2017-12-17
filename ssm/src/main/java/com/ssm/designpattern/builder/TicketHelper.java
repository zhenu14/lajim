package com.ssm.designpattern.builder;

public class TicketHelper {

    public void buildAdult(String info){
        System.out.println("构建成年人票逻辑：" + info);
    }

    public void buildSoldier(String info){
        System.out.println("构建军人票逻辑：" + info);
    }

    public void buildElderly(String info){
        System.out.println("构建老年人票逻辑：" + info);
    }

    public void buildChildForSeat(String info){
        System.out.println("构建儿童有座票逻辑：" + info);
    }

    public void buildChildForNoSeat(String info){
        System.out.println("构建儿童无座票逻辑：" + info);
    }
}
