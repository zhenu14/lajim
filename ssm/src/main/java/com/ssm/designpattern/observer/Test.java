package com.ssm.designpattern.observer;

public class Test {
    public static void main(String[] a){
        ProductList productList = ProductList.getInstance();
        TaoBaoObserver taoBaoObserver = new TaoBaoObserver();
        JingDongObserver jingDongObserver = new JingDongObserver();
        productList.addObserver(jingDongObserver);
        productList.addObserver(taoBaoObserver);
        productList.addProduct("飞机");
    }
}
