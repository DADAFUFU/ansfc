package com.ansfc.test;

/**
 * Created by dafu on 2018/3/11.
 * 对象锁和类锁的区别
 */
public class LockTest {

    /**
     * 对象锁
     */
    public synchronized void lock01(){
        System.out.println("lock01");
    }

    /**
     * 对象锁
     */
    public void lock02(){
        synchronized (this){
            System.out.println("lock02");
        }
    }

    /**
     * 类锁
     */
    public synchronized static void lock03(){
        System.out.println("lock03");
    }

    /**
     * 类锁
     */
    public static void lock04(){
        synchronized (LockTest.class){
            System.out.println("lock04");
        }
    }
}
