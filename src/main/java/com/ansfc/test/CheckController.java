package com.ansfc.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by dafu on 2018/3/10.
 */
@Controller
public class CheckController {

    @RequestMapping("/check")
    @ResponseBody
    public String check(){
        return "Ogit puK";
    }
}
