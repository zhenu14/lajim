package com.ssm.designpattern.singleton;

public class TestConfigManager {
    public static void main(String [] args){
        ConfigManager configManager = ConfigManager.getInstance();
        Object val1 = configManager.getConfigItem("sdf","sdf");
        Object val2 = configManager.getConfigItem("user","root");
        System.out.println(val1);
        System.out.println(val2);
    }
}
