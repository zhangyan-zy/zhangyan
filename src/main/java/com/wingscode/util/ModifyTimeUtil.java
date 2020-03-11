package com.wingscode.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Author：张延
 * Date：2020-03-11 10:22
 * Description：<描述>
 */
public class ModifyTimeUtil {


    // 默认日期格式
    public static final String DATE_DEFAULT_FORMAT = "yyyy-MM-dd";
    // 默认时间格式
    public static final String DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_DEFAULT_FORMAT = "HH:mm:ss";
    // 日期格式化
    private static DateFormat dateFormat = null;
    // 时间格式化
    private static DateFormat dateTimeFormat = null;
    private static DateFormat timeFormat = null;
    private static Calendar gregorianCalendar = null;


    static {
        dateFormat = new SimpleDateFormat(DATE_DEFAULT_FORMAT);
        dateTimeFormat = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
        timeFormat = new SimpleDateFormat(TIME_DEFAULT_FORMAT);
        gregorianCalendar = new GregorianCalendar();
        }



    /**
     248      * 获取日期前15天
     249      *
     250      * @param date
     251      * @return
     252      */
   public static Date getDayBefore(Date date) {
                gregorianCalendar.setTime(date);
               int day = gregorianCalendar.get(Calendar.DATE);
               gregorianCalendar.set(Calendar.DATE, day - 15);




               return gregorianCalendar.getTime();
           }

}
