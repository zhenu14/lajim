package com.ssm.designpattern.builder;

public class Test {
    public static void main(String[]a){
        TicketHelper helper = new TicketHelper();
        helper.buildAdult("成人票");
        helper.buildElderly("老人票");
        helper.buildSoldier("军人票");
        helper.buildChildForNoSeat("儿童无座");
        helper.buildChildForSeat("儿童有座");
        Object ticket = TicketBuilder.builder(helper);
    }
}
