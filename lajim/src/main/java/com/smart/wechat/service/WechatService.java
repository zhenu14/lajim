package com.smart.wechat.service;

import com.smart.wechat.Constants;
import com.smart.wechat.util.MessageUtil;
import com.smart.wechat.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

@Service
public class WechatService {

    /**
     * 处理微信发来的请求
     *
     * @param request
     * @return
     */
//    public String processRequest(HttpServletRequest request) {
//        String respMessage = null;
//        try {
//            // 默认返回的文本消息内容
//            String textContent = "";
//            // xml请求解析
//            Map<String, String> requestMap = MessageUtil.parseXml(request);
//            // 消息类型
//            String msgType = requestMap.get("MsgType");
//            String fromUserName = requestMap.get("FromUserName");
//            baseUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
//            // 文本消息  ---- 自动回复
//            if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(msgType)) {
//                String Content = requestMap.get("Content");
//                textContent = initReply(Content);
//                if (StringUtil.isEmpty(textContent)){
//                    respMessage = initArticle(requestMap, Content);
//                }
//            }
//            // 事件推送
//            else if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgType)){
//                // 事件类型
//                String eventType = requestMap.get("Event");
//                //订阅
//                if (MessageUtil.EVENT_TYPE_SUBSCRIBE.equals(eventType)){
//                    textContent = initReply("subscribe");
//                }
//                // 取消订阅
//                else if (MessageUtil.EVENT_TYPE_UNSUBSCRIBE.equals(eventType)) {
//                    //  取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
//                }
//                // 自定义菜单点击事件
//                else if (MessageUtil.EVENT_TYPE_CLICK.equals(eventType)) {
//                    // 事件KEY值，与创建自定义菜单时指定的KEY值对应
//                    String eventKey = requestMap.get("EventKey");
//                    if ("btn_31".equals(eventKey)){// 进入微信商城
//                        List<Article> articleList = new ArrayList<Article>();
//                        String title = "欢迎进入微信商城";
//                        String description = "欢迎进入微信商城";
//                        String picUrl = "";
//                        String url = baseUrl + "/shop/index?uuid=" + fromUserName;
//                        Article article = new Article(title, description, picUrl, url);
//                        articleList.add(article);
//                        respMessage = formatArticle(requestMap, articleList);
//                    } else {
//                        textContent = initReply(eventKey);
//                    }
//                }
//            }
//            if (StringUtil.isEmpty(respMessage)){
//                // 错误消息
//                if (StringUtil.isEmpty(textContent)){
//                    textContent = initReply("error");
//                }
//                requestMap.put("Content", textContent);
//                respMessage = formatText(requestMap);
//            }
//        } catch (Exception e) {
//        }
//        return respMessage;
//    }

    /**
     * 验证签名
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[] {Constants.ACCESS_TOKEN, timestamp, nonce };
        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }
}
