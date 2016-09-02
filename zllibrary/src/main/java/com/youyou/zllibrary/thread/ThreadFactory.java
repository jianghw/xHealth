package com.youyou.zllibrary.thread;

/**
 * Created by 张磊 on 2016/4/13.
 * 介绍：
 */
public class ThreadFactory {
    public static Thread getThread(Runnable runnable){
        return new Thread(runnable);
    }
}
