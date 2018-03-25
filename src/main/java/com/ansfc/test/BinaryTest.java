package com.ansfc.test;

import org.junit.Test;

/**
 * Created by dafu on 2018/3/25.
 * 位运算
 */
public class BinaryTest {


    @Test
    public void testBinary(){
        int i = 5;
        //00000101
        System.out.println(Integer.toBinaryString(i));//
        //i左移
        System.out.println("========左移========");
        System.out.println(i<<1);// 5*2  10  00001010
        System.out.println(i<<2);// 5*4  20  00010100
        System.out.println(i<<3);// 5*8  40  00101000
        System.out.println(i<<4);// 5*16 80  01010000
        System.out.println(i<<5);// 5*32 160 10100000
        System.out.println(i<<6);// 5*32 320 101000000
        //结论 m << n = m * 2^n

        //i右移
        System.out.println("========右移========");
        System.out.println(i>>1);// 5/2  2 00000010
        System.out.println(i>>2);// 5/4  1 00000001
        System.out.println(i>>3);// 5/8  0 00000000
        System.out.println(i>>4);// 5/16 0 00000000
        //结论 m >> n = m / 2^n

        //无符号右移
        System.out.println(Integer.toBinaryString(-5));
        System.out.println(Integer.toBinaryString(-5>>>2));
    }
}
