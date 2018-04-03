package com.ansfc.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by dafu on 2018/4/3.
 */
public class AuthInvocationHandler implements InvocationHandler {

    //代理的对象
    private Object object;

    public AuthInvocationHandler(Object object){
        this.object = object;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        beforeMethod();//方法执行之前
        method.invoke(object,args);//执行代理的方法
        afterMethod();//方法执行之后
        return null;
    }


    private void beforeMethod(){
        System.out.println("before method ... ");
    }

    private void afterMethod(){
        System.out.println("after method ... ");
    }
}
