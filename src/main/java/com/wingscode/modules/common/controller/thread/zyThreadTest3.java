package com.wingscode.modules.common.controller.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Author：zhangyan
 * Date：2023-05-04 21:10
 * Description：<今天又是元气满满的一天>
 */
public class zyThreadTest3  implements Callable<String> {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask  <String> futureTask=new FutureTask<>(new zyThreadTest3());
        Thread thread=new Thread(futureTask);
        thread.start();
        String s = futureTask.get();
        System.out.println(s);

    }


    @Override
    public String call() throws Exception {
        return "hello yanyan";
    }
}