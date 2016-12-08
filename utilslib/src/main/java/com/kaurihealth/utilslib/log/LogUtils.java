package com.kaurihealth.utilslib.log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaurihealth.utilslib.constant.Global;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by jianghw on 2016/8/11.
 * <p/>
 * 描述：log工具类简单封装
 */
public class LogUtils {

    public static void initLogUtils(String environment) {
        if (environment.equals(Global.Environment.PREVIEW)) {
            Logger.init().logLevel(LogLevel.NONE);
        } else {
            Logger.init("Logger")
                    .logLevel(LogLevel.FULL)
                    .methodCount(2)
                    .methodOffset(0)
                    .logAdapter(new AndroidLogAdapter());
        }
    }

    public static void i(String msg) {
        Logger.i(msg);
    }

    public static void i(String tag, String msg) {
        Logger.i(tag, msg);
    }

    public static void d(String msg) {
        Logger.d(msg);
    }

    public static void d(String tag, String msg) {
        Logger.d(tag, msg);
    }

    public static void e(String msg) {
        Logger.e(msg);
    }

    public static void e(String tag, String msg) {
        Logger.e(tag, msg);
    }

    public static void json(String msg) {
        Logger.json(msg);
    }

    public static void jsonDate(Object src) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Logger.json(gson.toJson(src));
    }

}
