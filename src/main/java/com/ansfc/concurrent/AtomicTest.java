package com.ansfc.concurrent;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dafu on 2018/3/18.
 */
public class AtomicTest {

   @Test
   public void testAtomicInteger(){
      AtomicInteger atomicInteger = new AtomicInteger(0);
      CountDownLatch countDownLatch = new CountDownLatch(100);
      ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
              .setNameFormat("demo-pool-%d").build();
      ExecutorService pool = new ThreadPoolExecutor(5, 200,
              0L, TimeUnit.MILLISECONDS,
              new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
      for(int i=0;i<100;i++){
         pool.execute(new Runnable() {
            @Override
            public void run() {
               int a = atomicInteger.getAndIncrement();
               System.out.println(a);
               countDownLatch.countDown();
            }
         });
      }

      //等待所有的线程执行完毕后
      try {
         countDownLatch.await();
      } catch (InterruptedException e) {
         e.printStackTrace();
      }

      System.out.println(atomicInteger.get());
      pool.shutdown();

   }


   @Test
   public void testAtomicBoolean(){
      AtomicBoolean atomicBoolean = new AtomicBoolean(true);
      ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
              .setNameFormat("demo-pool-%d").build();
      ExecutorService pool = new ThreadPoolExecutor(5, 200,
              0L, TimeUnit.MILLISECONDS,
              new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
      List<Thread> list = new ArrayList<>();
      for(int i=0;i<100;i++){
         Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
               boolean flag = atomicBoolean.getAndSet(false);
               System.out.println(flag);
            }
         });

         pool.execute(thread);
         list.add(thread);
      }

      //等待所有的线程执行完毕
      for(Thread thread:list){
         try {
            thread.join();
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      System.out.println("执行完毕，result："+atomicBoolean.get());
      pool.shutdown();
   }

}
