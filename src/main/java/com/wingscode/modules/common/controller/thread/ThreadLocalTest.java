package com.wingscode.modules.common.controller.thread;

/**
 * Author：zhangyan
 * Date：2023-05-05 21:26
 * Description：<今天又是元气满满的一天>
 */
public class ThreadLocalTest {


    private static  ThreadLocal<String> threadLocal=new ThreadLocal<>();


    public static void main(String[] args) {
        // a();
        c();
    }

    private static void a() {
        threadLocal.set("yanyan");
        b();
    }


    static void b() {
        String s = threadLocal.get();
        System.out.println(s);
        threadLocal.remove();
    }



    public static void c(){
        threadLocal.set("xiaoyanyan");
        threadLocal.remove();
        System.out.println(threadLocal.get());
    }


}
