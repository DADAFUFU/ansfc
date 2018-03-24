package com.ansfc.concurrent;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class ConditionTest {

    /**
     * Created by dafu on 2018/3/21.
     * 案例：包饺子
     * 两个人包饺子，但是只有一个勺子来挑馅子，a用完了之后，将勺子放下，b开始用。这样依次进行着
     */
    @Test
    public void testCondition01(){
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("张三拿到了勺子");
                System.out.println("张三用勺子挑馅子。。");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("张三用完了勺子，放回了原处");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("李四拿到了勺子");
                System.out.println("李四用勺子挑馅子。。");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("李四用完了勺子，放回了原处");
                try {
                    //唤醒等待的线程
                    condition.signal();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        });

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("饺子包完了");
    }

    /**
     * 上一个例子实现了轮流包1个饺子，现在有一100个饺子需要张三李四两个人交错着包完，该如何实现
     */
    @Test
    public void testCondition02() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition zhangsanCondition = lock.newCondition();
        Condition lisiCondition = lock.newCondition();
        AtomicInteger size = new AtomicInteger(0);
        Zhangsan zhangsan = new Zhangsan(lock,zhangsanCondition,lisiCondition,"张三",size);
        Lisi lisi = new Lisi(lock,zhangsanCondition,lisiCondition,"李四",size);
        zhangsan.start();
        lisi.start();
        zhangsan.join();
        lisi.join();
        System.out.println("饺子包完了");
    }


    @Test
    public void testConditionQueue() throws InterruptedException {

        Queue<String> queue = new Queue<>(5);
        CountDownLatch countDownLatch = new CountDownLatch(15);
        Thread produce  = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        queue.add(String.valueOf(Math.random()));
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        produce.start();

        for(int i=0;i<5;i++){
            Thread consumer = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){
                            queue.remove();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            });
            consumer.start();
        }

        countDownLatch.await();
        System.out.println("测试完毕");
    }


    /**
     * 张三包饺子
     */
    static class Zhangsan extends Thread{

        String name;
        ReentrantLock lock;
        Condition zhangsanCondition;
        Condition lisiCondition;
        AtomicInteger size;
        public Zhangsan(ReentrantLock reentrantLock,Condition zhangsanCondition,Condition lisiCondition,String name,AtomicInteger size){
            this.name = name;
            this.lock = reentrantLock;
            this.zhangsanCondition = zhangsanCondition;
            this.lisiCondition = lisiCondition;
            this.size = size;
            this.setName(name);
        }

        @Override
        public void run() {
            lock.lock();
            while(size.get()<100) {
                int x = size.incrementAndGet();
                System.out.println("张三包了第"+x+"个饺子");
                try {
                    zhangsanCondition.await();
                    lisiCondition.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lock.unlock();
        }
    }

    /**
     * 李四包饺子
     */
    static class Lisi extends Thread{

        String name;
        ReentrantLock lock;
        Condition zhangsanCondition;
        Condition lisiCondition;
        AtomicInteger size;
        public Lisi(ReentrantLock reentrantLock,Condition zhangsanCondition,Condition lisiCondition,String name,AtomicInteger size){
            this.name = name;
            this.lock = reentrantLock;
            this.lisiCondition = lisiCondition;
            this.zhangsanCondition = zhangsanCondition;
            this.size = size;
            this.setName(name);
        }

        @Override
        public void run() {
            lock.lock();
            while(size.get()<100){
                int x = size.incrementAndGet();
                System.out.println("李四包了第"+x+"个饺子");
                try {
                    zhangsanCondition.signal();
                    lisiCondition.await();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            lock.unlock();
        }
    }


    /**
     * 分布式并发队列实现
     * @param <T>
     */
    public class Queue<T>{
        private ReentrantLock lock = new ReentrantLock();
        private Condition unEmpty = lock.newCondition();//不为空
        private Condition unFull = lock.newCondition();//没有满
        private Object[] elements;

        private int length = 0,addIndex = 0,removeIndex = 0;
        public Queue(int size){
            elements = new Object[size];
        }

        public void add(T element) throws InterruptedException {
            lock.lock();
            try{
                //队列已满
                if(length == elements.length){
                    unFull.await();
                }
                elements[addIndex] = element;
                System.out.println("[produce]"+Thread.currentThread().getName()+":"+element.toString());
                if(++addIndex == elements.length){
                    addIndex = 0;
                }
                length++;
                unEmpty.signal();
            }finally {
                lock.unlock();
            }
        }

        public T remove() throws InterruptedException {
            lock.lock();
            try {

                if(length == 0){
                    System.out.println("[consumer]"+Thread.currentThread().getName()+" waiting");
                    unEmpty.await();
                }
                Object element = elements[removeIndex];
                System.out.println("[consumer]"+Thread.currentThread().getName()+":"+element.toString());
                if(++removeIndex == elements.length){
                    removeIndex = 0;
                }
                length--;
                unFull.signal();//删除一个后，释放没有满的锁
                return (T)element;
            }finally {
                lock.unlock();
            }
        }



    }


}
