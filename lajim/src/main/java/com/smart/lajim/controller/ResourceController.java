package com.smart.lajim.controller;

import com.smart.lajim.service.ResourceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @RequiresPermissions("resource:view")
    @RequestMapping(value = "/resourceManage.html", method = RequestMethod.GET)
    public String list() {
        return "resource/list";
    }

    @RequestMapping(value = "/listRole.html", method = RequestMethod.GET)
    @ResponseBody
    public List showCreateForm(Model model) {
        return resourceService.findAll();
    }

}
