package com.example.commonlibrary.widget.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by 张磊 on 2016/5/10.
 * 介绍：
 */
public class LogFactory {
    private static class MyLogUtilInterface implements LogUtilInterface {

        String Tag;

        public MyLogUtilInterface(String tag) {
            Tag = tag;
        }

        @Override
        public void i(String message) {
            i(message, null);
        }

        @Override
        public void d(String message) {
            d(message, null);
        }

        @Override
        public void w(String message) {
            w(message, null);
        }

        @Override
        public void e(String message) {
            e(message, null);
        }

        @Override
        public void i(String message, Throwable ex) {
            if (!(isUseful(message))) {
                Log.i(Tag, message, ex);
            }
        }

        @Override
        public void d(String message, Throwable ex) {
            if (!(isUseful(message))) {
                Log.d(Tag, message, ex);
            }
        }

        @Override
        public void w(String message, Throwable ex) {
            if (!(isUseful(message))) {
                Log.w(Tag, message, ex);
            }
        }

        @Override
        public void e(String message, Throwable ex) {
            if (!(isUseful(message))) {
                Log.e(Tag, message, ex);
            }
        }


        private boolean isUseful(String message) {
            return TextUtils.isEmpty(message);
        }
    }

    public static LogUtilInterface getSimpleLog(String tag) {
        return new MyLogUtilInterface(tag);
    }
}



