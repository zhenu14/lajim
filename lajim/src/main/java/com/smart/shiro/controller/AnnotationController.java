package com.smart.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AnnotationController {

    @RequestMapping("/hello1.html")
    public String hello1() {
        SecurityUtils.getSubject().checkRole("admin");
        return "success";
    }

    @RequiresRoles("admin")
    @RequestMapping("/hello2.html")
    public String hello2() {
        return "success";
    }

    @RequestMapping("/shiroLogin.html")
    public String toLogin(){
        return "shiroLogin";
    }

    @RequestMapping("/unauthorized.html")
    public String unauthorized(){
        return "unauthorized";
    }

}
