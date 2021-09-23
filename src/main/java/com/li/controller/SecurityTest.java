package com.li.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author liql
 * @date 2021/5/12
 */
@Controller
@RequestMapping("/test")
public class SecurityTest {

    @GetMapping("hello")
    @ResponseBody
    public String test(){

        return "hello security";
    }

    @GetMapping("hello2")
    @ResponseBody
    public String test2(){

        return "hello security  2";
    }
    @GetMapping("/index")
    @ResponseBody
    public String index(){

        return "登录成功";
    }

    @GetMapping("/unauthen")
    @ResponseBody
    public String unauthen(){
        return "无权访问";
    }

    @GetMapping("/logout")
    @ResponseBody
    public String logout(){
        return "退出登录";
    }
    @GetMapping("hello3")
    @ResponseBody
    public String test3(){

        return "hello security  2";
    }
}
