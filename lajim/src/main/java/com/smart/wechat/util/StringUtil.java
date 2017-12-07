package com.smart.wechat.util;

/**
 * StringUtil  工具类
 * @类名	StringUtil.java
 */
public class StringUtil {

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }
}
