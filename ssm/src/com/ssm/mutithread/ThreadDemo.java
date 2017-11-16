package com.ssm.mutithread;

class Info{
    private String title;
    private String content;
    private boolean flag;
    //flag == true 表示可以生产，不能取出
    //flag == false 表示可以取出，不能生产
    public synchronized void set(String title,String content){
        if(flag == false){
            try{
                super.wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        this.title = title;
        try {
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        this.content = content;
        this.flag = false;
        super.notify();
    }

    public synchronized void get(){
        if(flag == true){
            try{
                super.wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(this.title + " --- " + this.content);
        this.flag = true;
        super.notify();
    }
}

class Productor implements Runnable{
    private Info info;
    public Productor(Info info){
        this.info = info;
    }
    @Override
    public void run() {
        for(int x = 0;x < 100;x++){
            if(x % 2 == 0){
                this.info.set("Hello鸡米","好次");
            }else{
                this.info.set("Hi汤","好喝");
            }
        }
    }
}

class Customer implements Runnable{
    private Info info;
    public Customer(Info info){
        this.info = info;
    }
    @Override
    public void run() {
        for(int x = 0;x < 100;x++){
            this.info.get();
        }
    }
}

public class ThreadDemo {
    public static void main(String[] args){
        Info info = new Info();
        new Thread(new Productor(info)).start();
        new Thread(new Customer(info)).start();
    }
}
