package com.smart.lajim.controller;

import com.smart.lajim.domain.User;
import com.smart.lajim.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class LoginController {
    private UserService userService;

    @Autowired void setUserService(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/toLogin.html")
    public String toLogin(){
        return "login";
    }

    @RequestMapping(value = "/demo.html")
    public String demo(){
        return "jqForm";
    }

    @RequestMapping(value = "/login.html")
    public ModelAndView login(HttpServletRequest request,LoginCommand loginCommand){
        boolean isValid = userService.hasMatchUser(loginCommand.getUserName(),loginCommand.getPassword());
        if(!isValid){
            return new ModelAndView("login","error","用户名密码错误");

        }else{
            User user = userService.findUserByUserName(loginCommand.getUserName());
            user.setLastIp(request.getLocalAddr());
            user.setLastVisit(new Date());
            userService.loginSuccess(user);
            request.getSession().setAttribute("user",user);
            return new ModelAndView("index");
        }
    }

    @RequestMapping(value = "/toMain.html")
    public String toMain(){
        return "index";
    }
}
