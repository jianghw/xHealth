package com.kaurihealth.utilslib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jianghw on 2016/8/31.
 * <p>
 * 描述：
 */
public class RegularUtils {

    /**
     * 身份证验证
     *
     * @param content
     * @return
     */
    public static boolean matcherIdentityCard(String content) {

        return matcherIdentityCard("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$", content);
    }

    private static boolean matcherIdentityCard(String condition, String content) {
        Pattern pattern = Pattern.compile(condition);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }

    public static boolean matcherMyContent(String condition, String content) {
        Pattern pattern = Pattern.compile(condition);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }
}
