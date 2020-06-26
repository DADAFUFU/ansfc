package com.ansfc.thread;

/**
 * Created by dafu on 2018/3/18.
 * 实验证明yield方法不会释放对象锁
 * 实验证明wait方法会释放对象锁
 */
public class ThreadTest {

    private static Object obj = new Object();

    public static void main(String[] args) throws Exception {
//        testYield();
//        testWait1();
//        testWait2();
        testWait3();
//        testJoin();
    }


    /**
     * 自线程结束后才能执行主线程
     */
    public static void testJoin(){
        System.out.println("--主线程执行开始--");
        ThreadC tHreadC = new ThreadC("t1");
        tHreadC.start();
        try {
            tHreadC.join();
        } catch (InterruptedException e) {
            tHreadC.interrupt();
        }
        System.out.println("--主线程执行结束--");
    }

    public static void testYield(){
        ThreadA thread1 = new ThreadA("t1");
        ThreadA thread2 = new ThreadA("t2");
        thread1.start();
        thread2.start();
    }

    public static void testWait(){
        ThreadB thread1 = new ThreadB("t1");
        ThreadB thread2 = new ThreadB("t2");
        thread1.start();
        thread2.start();
    }

    static class ThreadA extends Thread{

        public ThreadA(String name){
            super(name);
        }

        @Override
        public void run() {
            synchronized (obj){
                for(int i=0;i<10;i++){
                    System.out.println(this.getName()+" "+this.getPriority()+":"+i);
                    //将线程变成就绪状态
                    if(i%4 == 0){
                        Thread.yield();
                    }
                }

            }
        }
    }

    static class ThreadB extends Thread{

        public ThreadB(String name){
            super(name);
        }

        @Override
        public void run() {
            synchronized (obj){
                for(int i=0;i<10;i++){
                    System.out.println(this.getName()+" "+this.getPriority()+":"+i);
                    //将线程变成就绪状态
                    if(i%4 == 0){
                        try {
                            obj.notify();//唤醒当前线程  获取obj对象锁
                            System.out.println(this.getName()+" "+this.getPriority()+":"+i+"开始阻塞");
                            obj.wait();//阻塞当前线程  释放obj对象锁
                            System.out.println("唤醒"+this.getName()+" "+this.getPriority()+":"+i+"");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
    }

    static class ThreadC extends Thread{

        public ThreadC(String name){
            super(name);
        }

        @Override
        public void run() {
            System.out.println("自线程执行完毕");
        }
    }

    public static void testDeadLock(){

        Object object1 = new Object();
        Object object2 = new Object();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {

                synchronized (object1){
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (object2){
                        System.out.println("lock object2");
                    }
                }

            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object2){

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (object1){
                        System.out.println("lock object1");
                    }

                }
            }
        });
        thread1.start();
        thread2.start();

    }


    /**
     * 使用Object的wait机制实现两个线程交替打印
     */
    public static void testWait1(){

        Object lock = new Object();
        Thread thread1 = new Thread(() -> {
            synchronized (lock){
                for(int i=0;i<5;i++){
                    System.out.println("a");
                    lock.notifyAll();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            synchronized (lock){
                for(int i=0;i<5;i++){
                    System.out.println("b");
                    lock.notifyAll();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread1.start();
        thread2.start();
    }

    static volatile boolean printA = true;
    static volatile boolean printB = false;

    /**
     * 使用valatile实现两个线程交替打印
     */
    public static void testWait2(){



        Thread thread1 = new Thread(() -> {

            for(int i=0;i<5;i++){
                while (printB){

                }
                System.out.println("a");
                printB = true;
                printA = false;
            }

        });

        Thread thread2 = new Thread(() -> {

            for(int i=0;i<5;i++){
                while (printA){

                }
                System.out.println("b");
                printB = false;
                printA = true;
            }

        });
        thread1.start();
        thread2.start();

    }

    static volatile int count = 0;


    /**
     * 利用volatile线程可见性实现交替打印
     * @throws InterruptedException
     */
    public static void testWait3() throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            while(count<10){
                if(count % 2 == 0){
                    System.out.println("a");
                    count++;
                }
            }
        });

        Thread thread2 = new Thread(() -> {
             while(count<10){
                 if(count%2 == 1){
                     System.out.println("b");
                     count++;
                 }
             }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

    }

}
