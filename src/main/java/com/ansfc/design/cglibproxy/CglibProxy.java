package com.ansfc.design.cglibproxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {

    public <T> T getInstance(Object target, Class<T> clazz) {
        //字节码加强器：用来创建动态代理类
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass()); //代理的目标对象
        enhancer.setCallback(this); //回调类，在代理类方法调用时会回调Callback类的intercept方法

        Object result = enhancer.create(); //创建代理类
        return (T)result;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("start...");
        Object result = methodProxy.invokeSuper(o, objects); //调用目标类（父类）的方法
        System.out.println("end");
        return result;
    }
}
