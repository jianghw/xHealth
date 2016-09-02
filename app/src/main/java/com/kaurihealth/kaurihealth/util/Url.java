package com.kaurihealth.kaurihealth.util;


import com.kaurihealth.kaurihealth.BuildConfig;

/**
 * Created by KauriHealth-WEBDEV on 2016/1/14.
 * 介绍：
 */
public class Url {
    public static String Environment = BuildConfig.Environment;

    public static String prefix = null;
    public static String webPrefix = null;

    static {
        switch (Environment) {
            case "Develop":
                //开发
                webPrefix = "http://test.kaurihealth.com/";
                break;
            case "Test":
                //测试
                webPrefix = "http://www.jiarenhealth.com/";
                break;
            case "Preview":
                //预览
                webPrefix = "http://www.kaurihealth.com/";
                break;
            case "Stable":
                break;
        }
    }

    static {
        switch (Environment) {
            case "Test":
                prefix = "http://internaltestapi.kaurihealth.com/";
                break;
            case "Develop":
                prefix = "http://testapi.kaurihealth.com/";
                break;
            case "Preview":
                prefix = "http://uatapi.kaurihealth.com/";
                break;
            case "Stable":
                break;
        }
    }
    //登陆
    public static String login = prefix + "api/Login";
    //医生端检查更新的地址
    public static String LoadAndroidVersionWithDoctor = "http://101.201.151.189:8080/";
    //添图片
    public static String AddImage = prefix + "api/Document/Upload";
}
