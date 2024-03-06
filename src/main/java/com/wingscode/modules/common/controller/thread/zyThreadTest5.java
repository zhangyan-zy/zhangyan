package com.wingscode.modules.common.controller.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author：zhangyan
 * Date：2023-05-04 21:53
 * Description：<今天又是元气满满的一天>
 */
public class zyThreadTest5{


    public static ExecutorService newFixThreadPool(int nthreads) {


        return new ThreadPoolExecutor(nthreads, nthreads, 0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
    }



}
