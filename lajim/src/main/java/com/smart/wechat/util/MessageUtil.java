package com.smart.wechat.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信消息工具类
 * @类名	MessageUtil.java
 */
public class MessageUtil {

    /**
     * 返回消息类型：文本
     */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * 返回消息类型：图文
     */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * 请求消息类型：文本
     */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * 请求消息类型：图片
     */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 请求消息类型：链接
     */
    public static final String REQ_MESSAGE_TYPE_LINK = "link";


    /**
     * 请求消息类型：推送
     */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * 事件类型：CLICK(自定义菜单点击事件)
     */
    public static final String EVENT_TYPE_CLICK = "CLICK";

    /**
     * 解析微信发来的请求（XML）
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        @SuppressWarnings("unchecked")
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList)  {
            map.put(e.getName(), e.getText());
        }
        // 释放资源
        inputStream.close();

        return map;
    }

//    /**
//     * 文本消息对象转换成xml
//     *
//     * @param textMessage 文本消息对象
//     * @return xml
//     */
//    public static String textMessageToXml(TextMessage textMessage) {
//        xstream.alias("xml", textMessage.getClass());
//        return xstream.toXML(textMessage);
//    }

//    /**
//     * 图文消息对象转换成xml
//     *
//     * @param newsMessage 图文消息对象
//     * @return xml
//     */
//    public static String newsMessageToXml(NewsMessage newsMessage) {
//        xstream.alias("xml", newsMessage.getClass());
//        xstream.alias("item", new Article().getClass());
//        return xstream.toXML(newsMessage);
//    }

//    /**
//     * 扩展xstream，使其支持CDATA块
//     *
//     * @date 2013-05-19
//     */
//    private static XStream xstream = new XStream(new XppDriver() {
//        @Override
//        public HierarchicalStreamWriter createWriter(Writer out) {
//            return new PrettyPrintWriter(out) {
//                // 对所有xml节点的转换都增加CDATA标记
//                boolean cdata = true;
//
//                @Override
//                public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {
//                    super.startNode(name, clazz);
//                }
//
//                @Override
//                protected void writeText(QuickWriter writer, String text) {
//                    if (cdata) {
//                        writer.write("<![CDATA[");
//                        writer.write(text);
//                        writer.write("]]>");
//                    } else {
//                        writer.write(text);
//                    }
//                }
//            };
//        }
//    });
}