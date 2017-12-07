package com.smart.lajim.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @RequestMapping(value = "/login.html")
    public String showLoginForm(HttpServletRequest req, Model model) {
        System.out.println("LoginController");
        String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");
        String error = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        model.addAttribute("error", error);
        return "shiroLogin";
    }

//    private UserService userService;
//
//    @Autowired void setUserService(UserService userService){
//        this.userService = userService;
//    }
//
//    @RequestMapping(value = "/toLogin.html")
//    public String toLogin(){
//        return "login";
//    }
//
//    @RequestMapping(value = "/demo.html")
//    public String demo(){
//        return "jqForm";
//    }
//
//    @RequestMapping(value = "/login.html")
//    public ModelAndView login(HttpServletRequest request,LoginCommand loginCommand){
//        boolean isValid = userService.hasMatchUser(loginCommand.getUserName(),loginCommand.getPassword());
//        if(!isValid){
//            return new ModelAndView("login","error","用户名密码错误");
//
//        }else{
//            User user = userService.findUserByUserName(loginCommand.getUserName());
//            user.setLastIp(request.getLocalAddr());
//            user.setLastVisit(new Date());
//            userService.loginSuccess(user);
//            request.getSession().setAttribute("user",user);
//            return new ModelAndView("index");
//        }
//    }
//
//    @RequestMapping(value = "/toMain.html")
//    public String toMain(){
//        return "index";
//    }
}
