package com.ansfc.concurrent;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by dafu on 2018/3/20.
 * 可重入锁
 */
public class ReentrantLockTest {

    /**
     * 通过构造函数来确定是用公平锁还是非公平锁
     */
    ReentrantLock lock = new ReentrantLock(true);

    @Test
    public void testReentrantLock01(){

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("thread1获取到锁");
                System.out.println("thread1正在处理。。。");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread1释放锁");
                lock.unlock();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("thread2获取到锁");
                System.out.println("thread2正在处理。。。");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread2释放锁");
                lock.unlock();
            }
        });

        thread1.start();
        thread2.start();
        //等待子线程执行完毕之后，主线程再执行
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("测试完毕");
    }
}
