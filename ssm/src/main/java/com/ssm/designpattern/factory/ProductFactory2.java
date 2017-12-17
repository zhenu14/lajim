package com.ssm.designpattern.factory;

public class ProductFactory2 implements IProductFactory{
    @Override
    public String createProduct(String productNo) {
        String product = "2号佳丽啊  " + productNo;
        return product;
    }
}
