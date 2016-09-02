package com.kaurihealth.utilslib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jianghw on 2016/8/31.
 * <p/>
 * 描述：
 */
public class RegularUtils {

    public static boolean matcher(String condition, String content) {
        Pattern pattern = Pattern.compile(condition);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }
}
