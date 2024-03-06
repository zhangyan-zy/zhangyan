package com.wingscode.modules.common.controller.thread;

/**
 * Author：zhangyan
 * Date：2023-05-04 21:10
 * Description：<今天又是元气满满的一天>
 */
public class zyThreadTest2 {

    public static void main(String[] args) {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello yanyan");
            }
        });

        thread.start();


        Thread thread1=new Thread(() -> System.out.println("hello yanyan"));
        thread1.start();
    }

}