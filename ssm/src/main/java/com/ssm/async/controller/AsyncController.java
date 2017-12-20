package com.ssm.async.controller;

import com.ssm.async.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AsyncController {
    @Autowired
    private AsyncService asyncService;


    @RequestMapping(value = "async/{key}")
    public String asyncTest(HttpServletRequest req, HttpServletResponse resp, @PathVariable String key) throws Exception {
        asyncService.asyncMethod(key);
        System.out.println("async key1:" + key);
        return "index";
    }

    @RequestMapping(value = "async/{key}/status")
    @ResponseBody
    public Map showAsyncStatus(HttpServletRequest req, HttpServletResponse resp, @PathVariable String key) throws Exception {
        System.out.println("async key2:" + key);
        String status = asyncService.getProcess(key);
        System.out.println("async status:" + status);
        Map map = new HashMap();
        map.put("status",status);
        return map;
    }

    @RequestMapping(value = "async/{key}/clear")
    public Map clearAsyncStatus(HttpServletRequest req,HttpServletResponse resp, @PathVariable String key) throws Exception {
        System.out.println("async key3:" + key);
        asyncService.clearCache(key);
        Map map = new HashMap();
        map.put("status","clearAsyncStatus");
        return map;
    }
}
