package br.com.artechapps.app.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by arthurcordova on 7/26/16.
 */
public class UtilsDate {

    public static Date convert(String srt){
        String splited[] = srt.split("/");
        Calendar c = Calendar.getInstance();
        c.set(Integer.valueOf(splited[2]), Integer.valueOf(splited[1]), Integer.valueOf(splited[0]));
        return c.getTime();
    }

    public static String getCurrentDate(){
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR);
    }

    public static boolean between (Date target, Date start, Date end){
        return (target.after(start) || target.equals(start)) && (target.before(end) || target.equals(end));
    }

}
