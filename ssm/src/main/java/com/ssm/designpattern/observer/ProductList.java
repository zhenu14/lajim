package com.ssm.designpattern.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ProductList extends Observable{
    private List<String> productList = null;
    private static ProductList instance;    //唯一实例，单例模式
    private ProductList(){}     //构造方法私有化，单例模式的一种实现方式

    /**
     * 获取唯一实例
     * @return 产品列表实例
     */
    public static ProductList getInstance(){
        if(instance == null){
            instance = new ProductList();
            instance.productList = new ArrayList<String>();
        }
        return instance;
    }

    /**
     * 增加观察者
     * @param observer
     */
    public void addProductListObserver(Observer observer){
        this.addObserver(observer);
    }

    /**
     * 新增产品，通知观察者
     * @param newProduct
     */
    public void addProduct(String newProduct){
        productList.add(newProduct);
        System.out.println("产品列表新增了产品" + newProduct);
        this.setChanged();
        this.notifyObservers(newProduct);
    }
}
