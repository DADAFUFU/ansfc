package com.ansfc.memory;

import java.nio.ByteBuffer;

/**
 * Created by dafu on 2018/3/15.
 * 非直接内存IO操作
 * 本地IO  -->直接内存-->非直接内存-->直接内存-->本地IO
 * 直接内存IO操作
 * 本地IO  ——>直接内存-->本地IO
 */
public class DirectMemory {


    /**
     * 直接内存和堆内存分配空间比较
     *
     * 结论:在数据量提升时，直接内存相比非直接内的申请，有很严重的性能问题
     */
    public static void allocateCampure(){
        int time = 1000000;//操作次数
        long begin = System.currentTimeMillis();

        for(int i=0;i<time ;i++){
            //非直接内存分配
            ByteBuffer buffer = ByteBuffer.allocate(2);
        }
        long end = System.currentTimeMillis();
        System.out.println("在进行"+time+"次分配操作时，堆内存 分配耗时:" + (begin-end) +"ms" );

        long begin1 = System.currentTimeMillis();

        for(int i=0;i<time ;i++){
            //直接内存分配
            ByteBuffer buffer = ByteBuffer.allocateDirect(2);
        }
        long end1 = System.currentTimeMillis();
        System.out.println("在进行"+time+"次分配操作时，直接内存 分配耗时:" + (begin1-end1) +"ms" );
    }

    /**
     * 直接内存 和 堆内存的 读写性能比较
     *
     * 结论：直接内存在直接的IO 操作上，在频繁的读写时 会有显著的性能提升
     *
     */
    public static void operateCompare(){
        int time = 1000000000;

        ByteBuffer buffer = ByteBuffer.allocate(2*time);
        long st = System.currentTimeMillis();
        for (int i = 0; i < time; i++) {

            //  putChar(char value) 用来写入 char 值的相对 put 方法
            buffer.putChar('a');
        }
        buffer.flip();
        for (int i = 0; i < time; i++) {
            buffer.getChar();
        }
        long et = System.currentTimeMillis();

        System.out.println("在进行"+time+"次读写操作时，非直接内存读写耗时：" + (et-st) +"ms");

        ByteBuffer buffer_d = ByteBuffer.allocateDirect(2*time);
        long st_direct = System.currentTimeMillis();
        for (int i = 0; i < time; i++) {

            //  putChar(char value) 用来写入 char 值的相对 put 方法  一个char 两个子节
            buffer_d.putChar('a');
        }
        buffer_d.flip();
        for (int i = 0; i < time; i++) {
            buffer_d.getChar();
        }
        long et_direct = System.currentTimeMillis();

        System.out.println("在进行"+time+"次读写操作时，直接内存读写耗时:" + (et_direct - st_direct) +"ms");
    }


}
