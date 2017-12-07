package com.smart.wechat.controller;

import com.smart.wechat.service.WechatService;
import com.smart.wechat.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Controller
public class WechatController {
    public static final Logger logger = Logger.getLogger(WechatController.class);

    @Autowired
    private WechatService wechatService;

    private String msg;

    @RequestMapping(value = "index.do",method = RequestMethod.GET)
    public void wechatGet(HttpServletRequest request, HttpServletResponse response){
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");

        String echostr = request.getParameter("echostr");


        PrintWriter out = null;
        try {
            out = response.getWriter();
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (StringUtil.isEmpty(signature) || StringUtil.isEmpty(timestamp) || StringUtil.isEmpty(nonce)){
                msg =  "请判断是否为微信接入";
            } else {
                if (wechatService.checkSignature(signature, timestamp, nonce)) {
                    // 随机字符串
                    msg = request.getParameter("echostr");
                }
            }
            out.print(msg);
            out.close();
            out = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
//        request.setAttribute("msg",msg);
//        return "msg";
    }

    @RequestMapping(value = "index.do",method = RequestMethod.POST)
    public String wechatPost(HttpServletRequest request, HttpServletResponse response){
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        response.setCharacterEncoding("UTF-8");
        try {
            request.setCharacterEncoding("UTF-8");
            msg = "testttttttt";
//            msg = weiXinService.processRequest(request);
        } catch (UnsupportedEncodingException e) {
        }
        request.setAttribute("msg",msg);
        return "msg";
    }
}

