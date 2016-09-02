package com.youyou.zllibrary.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 张磊 on 2016/4/29.
 * 介绍：
 */
public class ThreadPoolUtil {
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    public static void execute(Runnable runnable) {
        fixedThreadPool.execute(runnable);
    }
}
