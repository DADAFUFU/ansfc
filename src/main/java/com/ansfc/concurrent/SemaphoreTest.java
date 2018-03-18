package com.ansfc.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created by dafu on 2018/3/18.
 * 技术信号量
 */
public class SemaphoreTest {

    @Test
    public void testSemaphore(){

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();

        //Common Thread Pool
        ExecutorService service = new ThreadPoolExecutor(5, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());        Semaphore semaphore = new Semaphore(2);
        for(int i=0;i<100;i++){
            service.submit(new MySemaphore(i,semaphore));
        }
        service.shutdown();//拒绝接受提交的任务，将提交的任务执行完成，将线程池状态变成SHUTDOWN
        //请求两个资源，请求不到一直阻塞
        semaphore.acquireUninterruptibly(2);
        //释放两个资源
        semaphore.release(2);
        System.out.println("任务执行完毕");

    }


    class MySemaphore extends Thread{
        int i;
        Semaphore semaphore;
        public MySemaphore(int i,Semaphore semaphore){
            this.i = i;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {

            try {
                //拿到许可
                semaphore.acquire();
                System.out.println("第"+this.i+"个任务进入线程池，准备执行");
                //释放资源
                Thread.sleep(1000);
                System.out.println("第"+this.i+"个任务执行完毕，释放资源");
                semaphore.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
