package com.platform.cfs.common.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class DateUtils {
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String MONTH_DAY_TINY_FORMAT = "MMdd";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String UNIX_TIME_FORMAT = "yyyyMMddHHmmss";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIMESTAMP_FORMAT_WEBSERVICE = "yyyyMMddHHmmssSSS";

    /**
     *
     * @Description: yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrDateTime() {
        return DateUtils.getCurrDate(DateUtils.DATE_TIME_FORMAT);
    }

    /****
     * uinx时间戳转换为标准日期格式 格式为 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     *            单位：秒
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String fromUinxTime(Integer date) {
        if (date == null || date.intValue() == 0) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        try {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(date * 1000L);
            return sdf.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /****
     * uinx时间戳转换为Date
     */
    public static Date dateFromUinxTime(Long date) {
        if (date == null || date.intValue() == 0) {
            return null;
        }
        Date d = new Date(date*1000);
        return d;
    }

    /****
     * uinx时间戳转换为标准日期格式 格式为 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     *            单位：秒
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String fromMillisTime(Long date) {
        if (date == null || date.intValue() == 0) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        try {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(date);
            return sdf.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *
     * @Description:返回当前秒
     * @return Integer 单位：秒
     * @throws
     */
    public static Integer getUnixTime() {
        return (int) (System.currentTimeMillis() / 1000);
    }
    /**
     * 获取指定日期的时间戳
     *yyyy-MM-dd HH:mm:ss
     */
    public static Integer getAppointedUnixTime(String time){
        SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date date = null;
        try {
            date = df.parse(time);
            return   (int)(date.getTime()/1000);
        } catch (ParseException e) {

        }
        return null;

    }


    public static String getWsTime() {
        SimpleDateFormat sf = new SimpleDateFormat(TIMESTAMP_FORMAT_WEBSERVICE);
        return sf.format(new Date());
    }

    /**
     * 是否是昨天
     *
     * @return
     */
    public static Boolean isYestday(Integer unixTime) {

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String todayfm = format.format(new Date());

        try {
            Date today = format.parse(todayfm);
            int todayStart = (int) (today.getTime() / 1000);
            int yestdayStart = (int) ((today.getTime() - 24 * 60 * 60 * 1000) / 1000);
            if (unixTime >= yestdayStart && unixTime < todayStart) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 是否是今天
     * @return
     */
    public static Boolean isToday(Integer unixTime) {

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String todayfm = format.format(new Date());

        try {
            Date today = format.parse(todayfm);
            int todayStart = (int) (today.getTime() / 1000);
            int tomorrowStart = (int) ((today.getTime() + 24 * 60 * 60 * 1000) / 1000);
            if (unixTime >= todayStart && unixTime < tomorrowStart) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 获得当前日期
     *
     * @param
     * @return
     */
    public static String getCurrDate(String formatStr) {
        SimpleDateFormat df = new SimpleDateFormat(formatStr);// 设置日期格式
        String currDateTime = df.format(new Date());// 获取当前日期
        return currDateTime;
    }


    /**
     * 获取今天开始时间
     * @return
     */
    public static Integer getCurrDayStartUnixTime(){
        SimpleDateFormat format = new SimpleDateFormat(UNIX_TIME_FORMAT);
        String todayfm = format.format(new Date());
        todayfm = todayfm.substring(0, 8) + "000000";
        try {
            int millionSeconds = (int) (format.parse(todayfm).getTime() / 1000);
            return millionSeconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }//毫秒
        return null;
    }

    /**
     * 获取今天结束时间
     * @return
     */
    public static Integer getCurrDayEndUnixTime(){
        SimpleDateFormat format = new SimpleDateFormat(UNIX_TIME_FORMAT);
        String todayfm = format.format(new Date());
        todayfm = todayfm.substring(0, 8) + "235959";
        try {
            int millionSeconds = (int) (format.parse(todayfm).getTime() / 1000);
            return millionSeconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }// 毫秒
        return null;
    }
    /**
     * 获取今天结束时间 并加上一个按分钟的随机数
     * @return
     */
    public static Integer getCurrDayEndUnixTimeAddRamdomMin(Integer minutes){
        if(null == minutes){
            return null;
        }
        Random r = new Random();
        Integer ramdomMin= r.nextInt( minutes*60);
        SimpleDateFormat format = new SimpleDateFormat(UNIX_TIME_FORMAT);
        String todayfm = format.format(new Date());
        todayfm = todayfm.substring(0, 8) + "235959";
        try {
            int millionSeconds = (int) (format.parse(todayfm).getTime() / 1000);
            return millionSeconds+ramdomMin;
        } catch (ParseException e) {
            e.printStackTrace();
        }// 毫秒
        return null;
    }

    /**
     * 获取之后或之前多少天的开始时间戳
     * @param days
     * @return
     */
    public static Integer getTodayBeforeOrAfterStartUnixTime(Integer days){
        SimpleDateFormat format = new SimpleDateFormat(UNIX_TIME_FORMAT);
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,days);
        date=calendar.getTime();
        String todayfm = format.format(date);
        todayfm = todayfm.substring(0, 8) + "000000";
        try {
            int millionSeconds = (int) (format.parse(todayfm).getTime() / 1000);
            return millionSeconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }//毫秒
        return null;
    }
    /**
     * 获取之后或之前多少天的结束时间戳
     * @param days
     * @return
     */
    public static Integer getTodayBeforeOrAfterEndUnixTime(Integer days){
        SimpleDateFormat format = new SimpleDateFormat(UNIX_TIME_FORMAT);
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,days);
        date=calendar.getTime();
        String todayfm = format.format(date);
        todayfm = todayfm.substring(0, 8) + "235959";
        try {
            int millionSeconds = (int) (format.parse(todayfm).getTime() / 1000);
            return millionSeconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }// 毫秒
        return null;
    }
    /**
     * 获取之后多少天的毫秒值
     * @param days
     * @return
     */
    public static Integer getTodayAfterUnixTime(Integer days){
        SimpleDateFormat format = new SimpleDateFormat(UNIX_TIME_FORMAT);
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,days);
        date=calendar.getTime();
        String todayfm = format.format(date);
        try {
            int millionSeconds = (int) (format.parse(todayfm).getTime() / 1000);
            return millionSeconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }//毫秒
        return null;
    }



    public static Integer getTomorrowStartUnixTime(){
        SimpleDateFormat format = new SimpleDateFormat(UNIX_TIME_FORMAT);
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,1);
        date=calendar.getTime();
        String todayfm = format.format(date);
        todayfm = todayfm.substring(0, 8) + "000000";
        try {
            int millionSeconds = (int) (format.parse(todayfm).getTime() / 1000);
            return millionSeconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }//毫秒
        return null;
    }

    public static Integer getTomorrowEndUnixTime(){
        SimpleDateFormat format = new SimpleDateFormat(UNIX_TIME_FORMAT);
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,1);
        date=calendar.getTime();
        String todayfm = format.format(date);
        todayfm = todayfm.substring(0, 8) + "235959";
        try {
            int millionSeconds = (int) (format.parse(todayfm).getTime() / 1000);
            return millionSeconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }// 毫秒
        return null;
    }

    public static Integer getCurrMonthStartUnixTime(){
        SimpleDateFormat format = new SimpleDateFormat(UNIX_TIME_FORMAT);
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        last = last.substring(0, 8) + "000000";
        try {
            int millionSeconds = (int) (format.parse(last).getTime() / 1000);
            return millionSeconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }//毫秒
        return null;
    }

    public static Integer getCurrMonthEndUnixTime(){
        SimpleDateFormat format = new SimpleDateFormat(UNIX_TIME_FORMAT);
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        last = last.substring(0, 8) + "235959";
        try {
            int millionSeconds = (int) (format.parse(last).getTime() / 1000);
            return millionSeconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }// 毫秒
        return null;
    }

    public static Integer getCurrYearStartUnixTime(){
        SimpleDateFormat format = new SimpleDateFormat(UNIX_TIME_FORMAT);
        Calendar ca = Calendar.getInstance();
        String last = ca.get(Calendar.YEAR) + "0101000000";
        try {
            int millionSeconds = (int) (format.parse(last).getTime() / 1000);
            return millionSeconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }//毫秒
        return null;
    }

    public static Integer getCurrYearEndUnixTime(){
        SimpleDateFormat format = new SimpleDateFormat(UNIX_TIME_FORMAT);
        Calendar ca = Calendar.getInstance();
        String last = ca.get(Calendar.YEAR) + "1231235959";
        try {
            int millionSeconds = (int) (format.parse(last).getTime() / 1000);
            return millionSeconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }// 毫秒
        return null;
    }

    public static Integer getUnixTimeByDateStr(String DateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            int millionSeconds = (int) (sdf.parse(DateStr).getTime() / 1000);
            return millionSeconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }// 毫秒
        return null;
    }

    public static Integer getTodayStartUnitTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayfm = format.format(new Date());
        try {
            Date today = format.parse(todayfm);
            int todayStart = (int) (today.getTime() / 1000);
            return todayStart;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Integer getTodayEndUnitTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayfm = format.format(new Date());
        try {
            Date today = format.parse(todayfm);
            int tomorrowStart = (int) ((today.getTime() + 24 * 60 * 60 * 1000) / 1000);
            return (tomorrowStart - 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /****
     * uinx时间戳转换为标准日期格式 格式为formatStr
     *
     *            单位：秒
     * @return
     */
    public static String fromUinxTime(Integer unixTime, String formatStr) {
        if (unixTime == null || unixTime.intValue() == 0 || formatStr == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        try {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(unixTime * 1000L);
            return sdf.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getCurrentDate() {
        return DateUtils.getCurrDate(DateUtils.MONTH_DAY_TINY_FORMAT);
    }

    public static Integer getCurrWeekStartUnixTime(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        long millis = cal.getTimeInMillis();
        return (int) (millis / 1000);
    }

    public static Date getCurrWeekStartDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    public static Integer getCurrWeekEndUnixTime(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrWeekStartDate());
        cal.add(Calendar.DAY_OF_WEEK, 7);
        return (int) ((cal.getTimeInMillis() / 1000) - 1);
    }
    public static Date getDate(String date ,String formatStr) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        try {
            return sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getFormatDate(Date date ,String formatStr) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        try {
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *
     * @Title setTime
     * @Description 复制时间-该方法用于替换所有service实现类中setTime方法
     * @author 刘非
     * @param source 要操作的对象(从这个对象中获取要赋值的数据)
     * @param target 被操作的对象(给这个对象里的stratTime和endTime赋值)
     */
    public static  void setTime(Object  source, Object target){
        if(Utils.isNull(source)||Utils.isNull(target)){
            return;
        }
        //source中类型是字符串类型，target中的类型是日期类型
        String[] fields=new String[]{"StartTime","EndTime"};
        try {
            Class<?> t1=source.getClass();
            Class<?> t2=target.getClass();
            for (String field : fields) {
                //得到get方法名称
                String getField="get".concat(field);
                //获取source中的get方法
                Method gm=t1.getMethod(getField);
                //得到source对象里get字段的值
                Object obj=gm.invoke(source);
                //验证是否为空
                if(Utils.isNotNull(obj)){
                    //获取设置的字段类型
                    Method gm2=t2.getMethod(getField);
                    if(gm2.getReturnType().equals(Date.class)){
                        obj= DateUtils.getDate(Utils.nulltostr(obj), "yyyy-MM-dd HH:mm:ss");
                    }
                    //得到target中的set方法名称
                    String setField="set".concat(field);
                    //获取target中的set方法
                    Method sm=t2.getMethod(setField,gm2.getReturnType());
                    //将source中获取的值放入target中的set方法
                    sm.invoke(target,obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date date = null;
        try {
            date = df.parse("2017-08-30 24:00:00");
            System.out.println(  date.getTime()/1000);
        } catch (ParseException e) {

        }
    }

}
