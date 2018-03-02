package com.example.jason.constellation.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {

    public static String getDateFromMillis(long millis) {
        if (millis == 0) {
            return "";
        }
        Date date = new Date(millis);
        Date dateNow = new Date();
        if ((dateNow.getTime() - date.getTime()) / (24 * 60 * 60 * 1000) < 1) {
            return new SimpleDateFormat("HH:mm").format(date);
        } else {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
        }
    }

    public static String getMessageDateFromMillis(long millis) {
        if (millis == 0) {
            return "";
        }
        Date date = new Date(millis);
        Date dateNow = new Date();
        if ((dateNow.getTime() - date.getTime()) / (24 * 60 * 60 * 1000) < 1) {
            return new SimpleDateFormat("HH:mm").format(date);
        } else {
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        }
    }

    private static Date timeStampToDate(long pt) {
        if (pt == 0) {
            return null;
        }
        Long timestamp = (long) pt * 1000;
        Date date = new Date(timestamp);
        return date;
    }

    public static long getCurrentTimeSeconds(){
        return System.currentTimeMillis() / 1000;
    }

    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static String getBirthday(int age){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH)+1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        year -= age;
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int min = now.get(Calendar.MINUTE);
        return year+"-"+month+"-"+day+" "+hour+":"+min;
    }

    public static String millsToTimeText(long mills) {
        int seconds = (int) (mills / 1000 % 60);
        int minute = (int) (mills / 1000 / 60 % 60);
        int hour = (int) (mills / 1000 / 60/ 60);
        if (hour > 99) {
            return "99:59:59";
        }

        return String.format("%02d:%02d:%02d", hour, minute, seconds);
    }

    public static boolean isMoreOneDay(long before) {
        long current = System.currentTimeMillis();
        return (current - before) / (24 * 60 * 60 * 1000) >= 1;
    }

    public static boolean isMoreThreeDay(long before) {
        long current = System.currentTimeMillis();
        return (current - before) / (24 * 60 * 60 * 1000) >= 3;
    }

    public static boolean isMoreThirtyMinute(long before) {
        long current = System.currentTimeMillis();
        return (current - before) / (60 * 1000) >= 30;
    }

    public static boolean isLessOneDayFromStamp(long before) {
        long current = System.currentTimeMillis();
        return (current - before * 1000) / (24 * 60 * 60 * 1000) < 1;
    }

    public static String getCurrentUTCTime() {
        Date myDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTime(myDate);
        Date time = calendar.getTime();
        SimpleDateFormat outputFmt = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateAsString = outputFmt.format(time);
        return dateAsString;
    }
}
