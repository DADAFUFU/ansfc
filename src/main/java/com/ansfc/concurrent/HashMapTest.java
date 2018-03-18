package com.ansfc.concurrent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dafu on 2018/3/18.
 */
public class HashMapTest {

    public static void main(String[] args){
        Map<String,Object> param = new HashMap<>();
        Collections.synchronizedMap(param);
    }
}
