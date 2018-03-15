package com.ansfc.test;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by dafu on 2018/3/11.
 * 生产者消费者模式
 */
public class QueueTest {

    public static void main(String[] args){
        Queue<String> queue = new ArrayBlockingQueue<String>(10);
        Thread provider = new Thread(new Provider(queue));
        Thread consumer = new Thread(new Consumer(queue));
        provider.start();
        consumer.start();
    }
}

class Provider extends Thread{
    private Queue<String> queue;

    public Provider(Queue<String> queue){
        this.queue = queue;
    }
    int i = 0;
    @Override
    public void run() {

        while (true){
            if(queue.size() == 10){
                System.out.println("provider waiting..");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            i++;
            queue.add(String.valueOf(i));
            System.out.println("provider create :"+i);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread{
    private Queue<String> queue;

    public Consumer(Queue<String> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true){
            if(queue.isEmpty()){
                try {
                    System.out.println("consumer waiting..");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            System.out.println("consumer get :"+queue.poll());
        }
    }
}
