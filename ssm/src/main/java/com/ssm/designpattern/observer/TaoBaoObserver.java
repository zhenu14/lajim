package com.ssm.designpattern.observer;

import java.util.Observable;
import java.util.Observer;

public class TaoBaoObserver implements Observer{
    @Override
    public void update(Observable o, Object arg) {
        String newProduct = arg.toString();
        System.out.println("TB 发送新产品【" + newProduct + "】  同步到了TB");
    }
}
