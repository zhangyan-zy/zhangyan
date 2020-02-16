package com.wingscode.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author CrazyHuskar
 * caeat at 2020/2/16  15:43
 */
public class MyTimeUtil {
    public static String addDay(String s, int n) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            cd.add(Calendar.DATE, n);//增加一天
            //cd.add(Calendar.MONTH, n);//增加一个月

            return sdf.format(cd.getTime());

        } catch (Exception e) {
            return null;
        }
    }
}
