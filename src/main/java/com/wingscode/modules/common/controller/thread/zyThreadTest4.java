package com.wingscode.modules.common.controller.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author：zhangyan
 * Date：2023-05-04 21:53
 * Description：<今天又是元气满满的一天>
 */
public class zyThreadTest4 implements  Runnable{


    public static void main(String[] args) {
        ExecutorService executorService= Executors.newFixedThreadPool(10);
        executorService.execute(new zyThreadTest4());

    }

    @Override
    public void run() {
        System.out.println("hello yanyan");
    }
}
