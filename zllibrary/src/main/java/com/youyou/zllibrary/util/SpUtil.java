package com.youyou.zllibrary.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.youyou.zllibrary.jacksonutil.JsonUtil;

import java.io.IOException;

/**
 * 创建时间: 2015/12/7
 * 创建人:   张磊
 * 描述:
 * 修订时间:
 */
public class SpUtil {
    private static SpUtil spUtil;
    private String name = "config";
    private final SharedPreferences sharedPreferences;

    private SpUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static SpUtil getInstance(Context context) {
        if (spUtil == null) {
            spUtil = new SpUtil(context);
        }
        return spUtil;
    }

    public synchronized <T> void put(String key, T value) {
        if (String.class.isInstance(value)) {
            sharedPreferences.edit().putString(key, (String) value).commit();
        } else if (Integer.class.isInstance(value)) {
            sharedPreferences.edit().putInt(key, (Integer) value).commit();
        } else if (Float.class.isInstance(value)) {
            sharedPreferences.edit().putFloat(key, (Float) value).commit();
        } else if (Long.class.isInstance(value)) {
            sharedPreferences.edit().putLong(key, (Long) value).commit();
        } else if (Boolean.class.isInstance(value)) {
            sharedPreferences.edit().putBoolean(key, (Boolean) value).commit();
        }else {
            sharedPreferences.edit().putString(key, JsonUtil.getJson(value)).commit();
        }
        //return null;
    }

    public synchronized <T> T get(String key, Class<T> tClass) throws IllegalAccessException, IllegalArgumentException {
        if (tClass == String.class) {
            String result1 = sharedPreferences.getString(key, null);
            if (result1 == null) {
                IllegalAccessException exception = new IllegalAccessException("不存在这个值");
                throw exception;
            }
            return (T) result1;
        } else if (tClass == Integer.class) {
            Integer result2 = sharedPreferences.getInt(key, -10000);
            if (result2 == -10000) {
                throw new IllegalAccessException("不存在这个值");
            }
            return (T) result2;
        } else if (tClass == Float.class) {
            Float result3 = sharedPreferences.getFloat(key, -10000f);
            if (result3 == -10000f) {
                throw new IllegalAccessException("不存在这个值");
            }
            return (T) result3;
        } else if (tClass == Long.class) {
            Long result3 = sharedPreferences.getLong(key, -1000);
            if (result3 == -1000) {
                throw new IllegalAccessException("不存在这个值");
            }
            return (T) result3;
        } else if (tClass == Boolean.class) {
            Boolean result3 = sharedPreferences.getBoolean(key, false);

            return (T) result3;
        }else {
            String value = sharedPreferences.getString(key, null);
            if (value != null) {
                try {
                    T t = JsonUtil.readValue(value, tClass);
                    return t;
                } catch (IOException e) {
                    e.printStackTrace();
                    IllegalArgumentException ex = new IllegalArgumentException("不存在获取这种类型数据的方法");
                    ex.initCause(e);
                    throw ex;
                }
            }else{
                throw new IllegalAccessException("不存在这个值");
            }
//            throw new IllegalArgumentException("不存在获取这种类型数据的方法");
        }
    }
}
