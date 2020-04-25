package com.wingscode.util;

import java.util.UUID;

public class MyUtilUUID {

    public static String createUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getCode() {
        String randomcode = "";
        // 用字符数组的方式随机
        String model = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] m = model.toCharArray();
        for (int j = 0; j < 6; j++) {
            char c = m[(int) (Math.random() * 36)];
            // 保证六位随机数之间没有重复的
            if (randomcode.contains(String.valueOf(c))) {
                j--;
                continue;
            }
            randomcode = randomcode + c;
        }
        return randomcode;
    }

    public static String getCodeInt(int num) {
        String randomcode = "";
        // 用字符数组的方式随机
        String model = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] m = model.toCharArray();
        for (int j = 0; j < num; j++) {
            char c = m[(int) (Math.random() * 36)];
            // 保证六位随机数之间没有重复的
            if (randomcode.contains(String.valueOf(c))) {
                j--;
                continue;
            }
            randomcode = randomcode + c;
        }
        return randomcode;
    }


    public static String getCodeNum() {
        String randomcode = "";
        // 用字符数组的方式随机
        String model = "0123456789";
        char[] m = model.toCharArray();
        for (int j = 0; j < 6; j++) {
            char c = m[(int) (Math.random() * 10)];
            // 保证六位随机数之间没有重复的
            if (randomcode.contains(String.valueOf(c))) {
                j--;
                continue;
            }
            randomcode = randomcode + c;
        }
        return randomcode;
    }

    public static String getOrderSN() {
        String sn = MyUtilTime.getDate(4, "yyyyMMddHHmmss");
        sn += getCodeNum();
        return sn;
    }
    public static String getCodeLetter(){
        String randomcode = "";
        // 用字符数组的方式随机
        String model = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] m = model.toCharArray();
        for (int j = 0; j < 16; j++) {
            char c = m[(int) (Math.random() * 10)];

            randomcode = randomcode + c;
        }
        return randomcode;
    }
}
