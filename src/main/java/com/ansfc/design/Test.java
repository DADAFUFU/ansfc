package com.ansfc.design;

import com.ansfc.design.cglibproxy.CglibProxy;
import com.ansfc.design.cglibproxy.UserService;

/**
 * Created by dafu on 2018/4/3.
 */
public class Test {

    public static void main(String[] args){
        CglibProxy cglibProxy = new CglibProxy();
        UserService userService = new UserService();
        UserService proxyService = cglibProxy.getInstance(userService,UserService.class);
        proxyService.save();
        proxyService.select();
    }

}
