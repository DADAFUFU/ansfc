package com.ansfc.thread;

/**
 * Created by dafu on 2018/3/18.
 * 实验证明yield方法不会释放对象锁
 * 实验证明wait方法会释放对象锁
 */
public class YieldLockTest {

    private static Object obj = new Object();

    public static void main(String[] args){
//        testYield();
        testWait();
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

}
