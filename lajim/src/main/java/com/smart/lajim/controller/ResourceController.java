package com.smart.lajim.controller;

import com.smart.lajim.domain.Resource;
import com.smart.lajim.domain.ResourceTree;
import com.smart.lajim.service.ResourceService;
import com.smart.lajim.util.TreeNode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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

    @RequestMapping(value = "/listResource.html", method = RequestMethod.POST)
    @ResponseBody
    public Map listResource() {
        List<ResourceTree> resources = resourceService.findAll2();
        for (ResourceTree resource:resources) {
            if(resource.get_parentId() == 0){
//                resource.set_parentId(null);
            }
        }
        Map<String,List> result = new HashMap<String, List>();
        result.put("rows",resources);
        return result;
    }

}
