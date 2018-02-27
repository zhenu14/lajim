package com.features.stream;

import java.util.stream.Stream;


public class StreamDemo {

    public  static void main(String[] args){

        Stream<String> stream = Stream.of("ooo","oooo","ooooo");
        //foreach方法
        stream.forEach((str) -> System.out.println(str));
    }
}
