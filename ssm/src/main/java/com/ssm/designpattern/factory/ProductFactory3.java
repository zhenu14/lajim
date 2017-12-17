package com.ssm.designpattern.factory;

public class ProductFactory3  implements IProductFactory{
    @Override
    public String createProduct(String productNo) {
        String product = "3号佳丽啊  " + productNo;
        return product;
    }
}
