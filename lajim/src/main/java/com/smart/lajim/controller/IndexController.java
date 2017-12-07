package com.smart.lajim.controller;

import com.smart.lajim.Constants;
import com.smart.lajim.domain.Resource;
import com.smart.lajim.domain.User;
import com.smart.lajim.service.ResourceService;
import com.smart.lajim.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Controller
public class IndexController {
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserService userService;

    @RequestMapping("/index.html")
    public String index(HttpServletRequest request, Model model) {
        User loginUser = (User)request.getAttribute(Constants.CURRENT_USER);
        System.out.println("---IndexController--- :" + loginUser.getUsername());
        Set<String> permissions = userService.findPermissions(loginUser.getUsername());
        List<Resource> menus = resourceService.findMenus(permissions);
        for(Resource resource : menus) {
            System.out.println(resource.toString());
        }
        model.addAttribute("menus", menus);
        return "shiroIndex";
    }

}