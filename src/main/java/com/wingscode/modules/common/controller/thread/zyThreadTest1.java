package com.wingscode.modules.common.controller.thread;

/**
 * Author：zhangyan
 * Date：2023-05-04 21:10
 * Description：<今天又是元气满满的一天>
 */
public class zyThreadTest1 implements Runnable{


    public static void main(String[] args) {
       Thread thread=new Thread(new zyThreadTest1());
        thread.start();
     }

    @Override
    public void run() {
        System.out.println("hello  yanyan");
    }
}