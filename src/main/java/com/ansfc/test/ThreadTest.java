package com.ansfc.test;

/**
 * Created by dafu on 2018/3/11.
 */
public class ThreadTest {

    public void test01(){
        Thread thread01 = new Thread(new ThreadTest01());
        Thread thread02 = new ThreadTest02();
        thread01.start();
        thread02.start();
    }
}


class ThreadTest01 implements Runnable{

    @Override
    public void run() {
        System.out.println("ThreadTest01");
    }
}

class ThreadTest02 extends Thread{
    @Override
    public void run() {
        System.out.println("ThreadTest02");
    }
}
