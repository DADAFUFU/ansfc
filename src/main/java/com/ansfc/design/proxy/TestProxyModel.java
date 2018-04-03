package com.ansfc.design.proxy;

import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * Created by dafu on 2018/4/3.
 * 马士兵老师动态代理讲解：https://www.cnblogs.com/weiqinshian/p/5997983.html
 */
public class TestProxyModel {


    @Test
    public void testProxy(){

        ProxyDemoService proxyDemoService = new ProxyDemoServiceImpl();
        AuthInvocationHandler handler = new AuthInvocationHandler(proxyDemoService);

        ProxyDemoService proxyDemoService1 = (ProxyDemoService) Proxy.newProxyInstance(handler.getClass().getClassLoader(),proxyDemoService.getClass().getInterfaces(),handler);

        proxyDemoService1.sayHello();


    }

}
