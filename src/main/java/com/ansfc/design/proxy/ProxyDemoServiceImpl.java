package com.ansfc.design.proxy;

/**
 * Created by dafu on 2018/4/3.
 */
public class ProxyDemoServiceImpl implements ProxyDemoService {
    @Override
    public void sayHello() {
        System.out.println("hello");
    }
}
