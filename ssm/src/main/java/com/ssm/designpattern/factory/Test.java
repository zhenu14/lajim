package com.ssm.designpattern.factory;

public class Test {
    public static void main(String [] args){
        String product1 = "1号佳丽";
        String product2 = "2号佳丽";
        String product3 = "3号佳丽";
        ProductFactory productFactory = new ProductFactory();
        product1 = productFactory.createProduct(product1);
        product2 = productFactory.createProduct(product2);
        product3 = productFactory.createProduct(product3);
        System.out.println(product1);
        System.out.println(product2);
        System.out.println(product3);
    }
}
