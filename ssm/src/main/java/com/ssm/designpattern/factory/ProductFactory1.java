package com.ssm.designpattern.factory;

public class ProductFactory1 implements IProductFactory {
    @Override
    public String createProduct(String productNo) {
        String product = "1号佳丽啊  " + productNo;
        return product;
    }
}
