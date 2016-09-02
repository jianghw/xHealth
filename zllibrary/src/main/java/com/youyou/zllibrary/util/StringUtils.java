package com.youyou.zllibrary.util;

/**
 * Created by 张磊 on 2016/2/23.
 * 介绍：
 */
public class StringUtils {
    public static boolean isEmpty(String content) {
        if (content == null || content.length() == 0 || content.equals("null")) {
            return true;
        } else {
            return false;
        }
    }

}
