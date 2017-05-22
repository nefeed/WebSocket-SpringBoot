package com.xbongbong.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DateUtil {
    private static final Logger LOG = LogManager.getLogger(DateUtil.class);

    public static SimpleDateFormat SDF = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat SDF_NoFormat = new SimpleDateFormat(
            "yyyyMMddHHmmss");
    public static SimpleDateFormat SDFYMDHM = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");
    public static SimpleDateFormat SDFDate = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat SDFDate_NoFormat = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat SDFYears = new SimpleDateFormat("yyyy");
    public static SimpleDateFormat SDFTime = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat SDFMonthDay = new SimpleDateFormat("MM-dd");
    public static SimpleDateFormat SDFMonth = new SimpleDateFormat("yyyy-MM");

    // long
    public static long getLong() {
        Date dt = new Date();
        long nowtTme = dt.getTime();
        return nowtTme;
    }

    public static long getLong(Date date) {
        if(date==null) return 0;

        return date.getTime();
    }

    public static long getLong(String dateString) {
        return getLong(dateString,SDFYMDHM);
    }

    public static long getLong(String dateString,SimpleDateFormat format) {
        if(StringUtil.isEmpty(dateString)) return 0;

        Date dt =getDate(dateString,format);
        if(dt == null) return 0;

        return dt.getTime();
    }

    public static long getLong(String dateString,String formatStr) {
        return getLong(dateString , new SimpleDateFormat(formatStr));
    }
    // int

    /**
     * 获取当天00：00 ，东八区修正，与格林威治时间相差8个小时 28800秒
     * @return
     */
    public static int getTodayInt() {
        return getTodayInt(getInt())  ;
    }
    /**
     * 获取当天00：00 ，东八区修正，与格林威治时间相差8个小时 28800秒
     * @return
     */
    public static int getTodayInt(int day) {
        return (int)(day+28800) / 86400 * 86400 - 28800;
    }

    public static int getInt() {
        return (int)(getLong()/1000);
    }
    public static int getInt(Date date) {
        return (int)(getLong( date)/1000);
    }

    public static int getInt(String dateString) {

        return (int)(getLong( dateString)/1000);
    }

    public static int getInt(String dateString,SimpleDateFormat format) {
        if(StringUtil.isEmpty(dateString)) return 0;

        return (int)( getLong( dateString,format)/1000) ;
    }
    public static int getInt(String dateString,String formatStr) {
        return getInt( dateString , new SimpleDateFormat(formatStr));
    }

    public static int getNowSDFYears() {
        int years = 0;
        Date dt = new Date();
        String addtime = SDFYears.format(dt);
        years = StringUtil.StringToInteger(addtime, 0);
        return years;
    }

    // String

    public static String getString(SimpleDateFormat format) {
        Date dt = new Date();
        String addtime = format.format(dt);
        return addtime;
    }

    public static String getString(String formatStr) {

        return getString(new SimpleDateFormat(formatStr));
    }

    public static String getString(Date dt,SimpleDateFormat format) {
        if (dt == null) return null;

        String addtime = format.format(dt);
        return addtime;
    }
    public static String getString(Date dt,String formatStr) {
        return  getString(dt,new SimpleDateFormat(formatStr));
    }
    public static String getString(Long datetime,SimpleDateFormat format) {
        if(datetime == null) return null;

        if(datetime == 0) return "";

        Date dt = new Date(datetime);
        String addtime = format.format(dt);
        return addtime;
    }

    public static String getString(Long datetime,String formatStr) {
        return  getString(datetime,new SimpleDateFormat(formatStr));
    }

    public static String getString(Integer time,SimpleDateFormat format) {
        if(time == null) return null;
        return getString(1000L*time,format);
    }

    public static String getString(Integer time,String formatStr) {
        return  getString(time,new SimpleDateFormat(formatStr));
    }


    public static String getString() {
        return getString(SDFYMDHM);
    }

    //	public static String getString(Date dt) {
//		return getString(  dt,SDFYMDHM) ;
//	}
    public static String getString(Date dt) {
        return getString(  dt,SDF) ;
    }

    public static String getString(long time) {
        return getString(time,SDFYMDHM);
    }

    public static String getString(int time) {
        return getString(time,SDFYMDHM);
    }

    public static String getDateString(int time) {
        return getString(time,SDFDate);
    }
    public static String getDateString(Date time) {
        return getString(time,SDFDate);
    }

    public static String getLeftTime(int startTime,int limitTime) {
        String leftTime = null;
        Integer now = DateUtil.getInt();
        //LOG.info("now:"+now);
        Integer differenceTime = now - startTime;
        if(differenceTime>=0){
            //LOG.info("limitTime:"+limitTime+"   differenceTime:"+differenceTime);
            Integer leftTimeInt = limitTime - differenceTime;
            if(leftTimeInt<0){
                leftTimeInt = 0;
            }
            String h = (leftTimeInt/3600)+"";
            String m = (leftTimeInt%3600)/60+"";
            //LOG.info(h+"="+leftTimeInt+"/3600");
            //LOG.info(m+"="+(leftTimeInt%3600)+"/60");
            leftTime = h+"小时"+m+"分钟";
        }
        //LOG.info("leftTime:"+leftTime);
        return leftTime;
    }

    // date
    public static Date getDate(Long time) {
        if(time == null ) return null;
        return new Date(time);

    }
    public static Date getDate(Integer time) {
        if(time == null) return null;

        return new Date(1000L*time);
    }
    public static Date getDate( ) {
        return new Date();
    }

    public static Date getDate(String dateString,SimpleDateFormat format) {
        try {
            if(StringUtil.isEmpty(dateString)){
                dateString = getDateString(0);

            }
            Date d = format.parse(dateString);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getDate(String dateString ) {
        return getDate(  dateString, SDFYMDHM);
    }


    //获取当前年份1月1日0点0时0分
    public static final int getFirstDateOfYear(){
        return getFirstDateOfYear(getInt());
    }
    //获取当前年份12月31日24点24时24分 即 下一年的1月1日0点0时0分
    public static final int getLastDateOfYear(){
        return getLastDateOfYear(getInt());
    }
    //获取当前年份1月1日0点0时0分
    public static final int getFirstDateOfYear(Integer someday){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long)(someday) * 1000));
        calendar.set(calendar.get(Calendar.YEAR), 0, 1,0,0,0);
        return (int)(( calendar.getTimeInMillis())/1000);
    }

    //获取前一年的1月1日0点0时0分
    public static final int getFirstDateOfPreviousYear(){
        return getFirstDateOfPreviousYear(getInt());
    }

    //获取前一年的1月1日0点0时0分
    public static final int getFirstDateOfPreviousYear(Integer someday){
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date( (long)(someday)* 1000));
        calendar.set(calendar.get(Calendar.YEAR)-1, 0, 1,0,0,0);
        return (int)(( calendar.getTimeInMillis())/1000);
    }

    //获取当前年份12月31日24点24时24分 即 下一年的1月1日0点0时0分
    public static final int getLastDateOfYear(Integer someday){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long)(someday) * 1000));
        calendar.set(calendar.get(Calendar.YEAR)+1, 0, 1,0,0,0);
        return (int)(( calendar.getTimeInMillis())/1000);
    }

    public static final int getLastDateOfFirstSeason(){
        return getLastDateOfFirstSeason(getInt());
    }
    //获取当前年份第一季度结束时间
    public static final int getLastDateOfFirstSeason(Integer someday){
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date( (long)(someday)* 1000));
        calendar.set(calendar.get(Calendar.YEAR), 3, 1,0,0,0);
        return (int)(( calendar.getTimeInMillis())/1000);
    }

    /**
     *
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     *
     * @param someday
     * @return
     */
    public static int getSeason(Integer someday) {

        int season = 0;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long)(someday) * 1000));
        int month = calendar.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    /**
     *
     * 获得当前季度的第一天
     *
     * @param someday
     * @return
     */
    public static int getFirstDateOfSeason(Integer someday) {
        int m = 0;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long)(someday) * 1000));
        int month = calendar.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                m = 0;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                m = 3;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                m = 6;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                m = 9;
                break;
            default:
                break;
        }
        calendar.set(calendar.get(Calendar.YEAR), m, 1,0,0,0);
        return (int)(( calendar.getTimeInMillis())/1000);
    }

    /**
     *
     * 获得当前季度的最后一天
     *
     * @param someday
     * @return
     */
    public static int getLastDateOfSeason(Integer someday) {
        int m = 0;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long)(someday) * 1000));
        int month = calendar.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                m = 3;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                m = 6;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                m = 9;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                m = 0;
                break;
            default:
                break;
        }
        int year = calendar.get(Calendar.YEAR);
        //第四季度截止时间跨年问题
        if(m == 0) {
            year = year + 1;
        }
        calendar.set(year, m, 1,0,0,0);
        return (int)(( calendar.getTimeInMillis())/1000);
    }

    //获取当前月份的1日0时0分0秒
    public static final int getFirstDateOfThisMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),1,0,0,0);
        return (int)(( calendar.getTimeInMillis())/1000);
    }
    //获取某天所在月份的1日0时0分0秒
    public static final int getFirstDateOfMonthInSomeday(Integer someday){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long)(someday) * 1000));
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),1,0,0,0);
        return (int)(( calendar.getTimeInMillis())/1000);
    }

    /**
     * 同比， 获取上年同月的第一天
     * 比如传入的时间是2016年6月，则该方法得到2015年6月1日0时0分0秒
     * @param someday
     * @return
     */
    public static final int getFirstDateOfLastYear(Integer someday){

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date( (long)(someday)* 1000));
        calendar.set(calendar.get(Calendar.YEAR)-1, calendar.get(Calendar.MONTH), 1,0,0,0);
        return (int)(( calendar.getTimeInMillis())/1000);
    }
    /**
     * 同比，获取上年同月的最后一天
     * 比如传入的时间是2016年6月，则该方法得到2015年7月1日0时0分0秒（即6月末）
     * @param someday
     * @return
     */
    public static final int getLastDateOfLastYear(Integer someday){

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date( (long)(someday)* 1000));
        calendar.set(calendar.get(Calendar.YEAR)-1, calendar.get(Calendar.MONTH)+1, 1,0,0,0);
        return (int)(( calendar.getTimeInMillis())/1000);
    }

    //获取某天所在月份下个月的1日0时0分0秒
    public static final int getLastDateOfMonthInSomeday(Integer someday){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long)(someday) * 1000));
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 1,1,0,0,0);
        return (int)(( calendar.getTimeInMillis())/1000);
    }

    //获取当前月份的上一个月份的1日0时0分0秒
    public static final int getFirstDateOfLastMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) - 1,1,0,0,0);
        return (int)(( calendar.getTimeInMillis())/1000);
    }

    //获取某天所在月份的上一个月份的1日0时0分0秒
    public static final int getFirstDateOfLastMonth(Integer someday){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long)(someday) * 1000));
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) - 1,1,0,0,0);
        return (int)(( calendar.getTimeInMillis())/1000);
    }

    //获取当前月份的最后一天的24时24分24秒 即 下一个月的1日0时0分0秒
    public static final int getLastDateOfThisMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 1,1,0,0,0);
        return (int)(( calendar.getTimeInMillis())/1000);
    }
    //获取某天所在月份的下一个月的最后一天的23时59分59秒 即 下两个个月的1日0时0分0秒
    public static final int getLastDateOfNextMonthInSomeday(Integer someday){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long)(someday) * 1000));
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 2,1,0,0,0);
        return (int)(( calendar.getTimeInMillis())/1000);
    }

    //获取今年2月1日的0时0分0秒     用于推算其他月份的时间
    public static final int getFirstDateOfFeb(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),1,1,0,0,0);
        return (int)(( calendar.getTimeInMillis())/1000);
    }

    //获取今年3月1日的0时0分0秒     用于推算其他月份的时间
    public static final int getFirstDateOfMar(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),2,1,0,0,0);
        return (int)(( calendar.getTimeInMillis())/1000);
    }

    //获得本周一0点时间
    public static int getThisWeekMorning(){
        return getWeekMorningInSomeday(getInt());
    }
    //获取某天所在周的周一的0点
    public static final int getWeekMorningInSomeday(Integer someday){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long)(someday) * 1000));
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        calendar.add(Calendar.DATE, -day_of_week + 1);
        return  (int) (calendar.getTimeInMillis()/1000);
    }

    //获取某天所在周是周几， 0-6,代表周日到周六
    public static final int getWeekInSomeday(Integer someday){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long)(someday) * 1000));
        Integer week = calendar.get(Calendar.DAY_OF_WEEK);
        return  week-1;
    }

    //获取某个月有多少天
    public static final int getDaysOfSomeMonth(int someday){
        Date date = new Date((long)(someday) * 1000);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    //获取某个月有多少周
    public static final int getWeeksOfSomeMonth(int someday){
        Date date = new Date((long)(someday) * 1000);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取当天是该月的第几周
     * @param someday
     * @return
     */
    public static final int getWeekOfMonth(int someday) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long)(someday) * 1000));
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取当天是该年的第几周
     * @param someday
     * @return
     */
    public static final int getWeekOfYear(int someday) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long)(someday) * 1000));
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    //获得两个时间点之间跨度几月   例如：2015-5-12 2015-5-15 return 0
    public static final int getMonthBetweenSomeday(int startTime,int endTime){
        Date startDate = new Date((long)(startTime) * 1000);
        Date endDate = new Date((long)(endTime) * 1000);
        int yearNum = endDate.getYear() - startDate.getYear();
        int monthNum = endDate.getMonth() - startDate.getMonth();
        return yearNum*12+monthNum;
    }


    //获得两个时间点之间跨度几季度   例如：2015-5-12 2015-5-15 return 0
    public static final int getSeasonBetweenSomeday(int startTime,int endTime){
        Date startDate = new Date((long)(startTime) * 1000);
        Date endDate = new Date((long)(endTime) * 1000);
        int yearNum = endDate.getYear() - startDate.getYear();
        int seasonNum = getSeason(endTime) - getSeason(startTime);
        return yearNum*4+seasonNum;
    }

    //获得两个时间点之间跨度几年   例如：2015-5-12 2015-5-15 return 0
    public static final int getYearBetweenSomeday(int startTime,int endTime){
        Date startDate = new Date((long)(startTime) * 1000);
        Date endDate = new Date((long)(endTime) * 1000);
        int yearNum = endDate.getYear() - startDate.getYear();
        return yearNum;
    }

    //获得两个时间点之间已满几个月
    public static final int getMonthNum(int startTime,int endTime){
        Date startDate = new Date((long)(startTime) * 1000);
        Date endDate = new Date((long)(endTime) * 1000);
        int yearNum = endDate.getYear() - startDate.getYear();
        int monthNum = endDate.getMonth() - startDate.getMonth();
        int dayNum = endDate.getDate() - startDate.getDate();

        int ret = 0;
        LOG.info("dayNum========="+dayNum);
        if(dayNum<0){
            ret = yearNum*12+monthNum-1;
        }else{
            ret = yearNum*12+monthNum;
        }

        return ret;
    }

    //日程
    /**
     * 返回年 如 2015
     * @param time
     * @return
     */
    public static String getYear(Integer time) {
        if (time == null) {
            return null;
        }

        return getString(time).substring(0, 4);
    }
    /**
     * 返回月 如 04
     * @param time
     * @return
     */
    public static String getMonth(Integer time) {
        if (time == null) {
            return null;
        }

        return getString(time).substring(5, 7);
    }
    /**
     * 返回日 如 14
     * @param time
     * @return
     */
    public static String getDay(Integer time) {
        if (time == null) {
            return null;
        }

        return getString(time).substring(8, 10);
    }
    /**
     * 返回 HH:mm
     * @param time
     * @return HH:mm
     */
    public static String getHour(Integer time) {
        if (time == null) {
            return null;
        }

        return getString(time).substring(11);
    }

    //start + l毫秒
    public static Date addDate(Date start,long l) {

        return new Date(start.getTime()+l);

    }
    /**
     * 获得一个前num个周一的日期数组，如：{"2015-4-26",...,"2015-3-15"}
     * @param num
     * @return
     */
    public static String[] getWeekArray(Integer num){
        String[] weekArray = new String[num];
        Integer weekInt = getThisWeekMorning();

        for(int i = 0;i < num;i++){
            Integer week = weekInt - 86400*7*i;
            weekArray[i] = getDateString(week);
        }
        return weekArray;
    }
    /**
     * 获得前num个月的1月1号，如：{"2015-4-1",......,"2014-1-1"}
     * @param num
     * @return
     */
    public static String[] getMonthArray(Integer num){
        String[] monthArray = new String[num];
        int now = getInt();
        for(int i = 0;i < num;i++){
            now = getFirstDateOfLastMonth(now);
            monthArray[i] = getDateString(now);
        }
        return monthArray;
    }
    /**
     * 获得前num年，如：{"2015",...,"2010"}
     * @param num
     * @return
     */
    public static String[] getYearArray(Integer num){
        String[] yearArray = new String[num];
        Integer now = getInt();
        for(int i = 0;i < num;i++){
            now = getFirstDateOfPreviousYear(now);
            yearArray[i] = getString(now,SDFYears);
            LOG.info("now:"+getString(now));
        }
        return yearArray;
    }

    /**
     * 计算两个时间 相差天数
     * @param beforeDay
     * @param lastDay
     * @return
     */
    public static int betweenDays(int beforeDay, int lastDay) {
        int day = (lastDay - beforeDay) / (24 * 3600);
        /**----不返回小时数了----------*/
//        if (day == 0) {//小于一天的  按照小时
//            Calendar calendarB = Calendar.getInstance();
//            calendarB.setTime(getDate(beforeDay));
//            int hourB = calendarB.get(Calendar.HOUR_OF_DAY);
//            int dayB = calendarB.get(Calendar.DAY_OF_MONTH);
//
//            Calendar calendarL = Calendar.getInstance();
//            calendarL.setTime(getDate(lastDay));
//            int dayL = calendarL.get(Calendar.DAY_OF_MONTH);
//            int hourL = calendarL.get(Calendar.HOUR_OF_DAY);
//
////            day = dayL - dayB;
//            day = hourL - hourB;
//        }
        return day;
    }

    /**
     *
     * @param lastConnectTime
     * @param now
     * @return
     */
    public static int lastConnect(int lastConnectTime, int now) {
        int day = (now - lastConnectTime) / (24 * 3600);

        if (day == 0 && lastConnectTime < getTodayInt(now)) {
            return 1;
        }
        return day;
    }

    //获取某时间点的这一年的第month个月的月初
    public static final int getFirstDateOfMonth(Integer someday,int month){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long)(someday) * 1000));
        calendar.set(calendar.get(Calendar.YEAR), month-1, 1,0,0,0);
        return (int)(( calendar.getTimeInMillis())/1000);
    }
    //获取某时间点的这一年的第month个月的月末
    public static final int getEndDateOfMonth(Integer someday,int month){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long)(someday) * 1000));
        calendar.set(calendar.get(Calendar.YEAR), month, 1,0,0,0);
        return (int)(( calendar.getTimeInMillis())/1000);
    }
    /**
     * 获取excel里的时间格式，一般是1900年以来的天数 ，50000以内
     * @param date
     * @return
     */
    public static Integer getExcelTime(String date){//获取excel中的格式各样的时间格式，统一格式解析
        Integer result=0;
        if(StringUtil.isEmpty(date)){
            return 0;
        }
        Integer dateInt= StringUtil.toInt(date);
        if(dateInt!=null && dateInt<50000)
        {
            result=(dateInt-25569)*86400-28800;
            if(dateInt==0){
                result=0;
            }


        }
        else{
            //excel中时间不是数字
            if(date.contains(".")){
                //2015.09.01格式
                date=date.replace(".", "-");
            }
            if(date.contains("/")){
                //2015/09/01格式
                date=date.replace("/", "-");
            }
            if(date.length()==8){
                //20150901格式
                date=date.substring(0,4)+"-"+date.substring(4, 6)+"-"+date.substring(6, 8);
            }
            if(date.contains("-")){
                try{
                    result= DateUtil.getInt(date, DateUtil.SDFDate);
                }catch(Exception e){
                    e.printStackTrace();
                    result=0;
                }

            }
        }
        return result;
    }

    /**
     * 获取某个时间的半年开始时间
     * 比如，someday在前6个月，则该方法得到someday所在年的1月1日0点
     * 		someday在后6个月，则该方法得到someday所在年的6月1日0点
     * @param someday
     * @return
     * @author chuanpeng.zhang
     * @time 2016-8-4 上午10:59:20
     */
    public static int getHalfYearStartTime(Integer someday) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long)(someday) * 1000));
        int someMonth = calendar.get(Calendar.MONTH) + 1;

        Integer startTime = null;
        if (someMonth >= 1 && someMonth <= 6){
            startTime = getFirstDateOfMonth(someday, 1);
        }else if (someMonth >= 7 && someMonth <= 12){
            startTime = getFirstDateOfMonth(someday, 7);
        }

        return startTime;
    }
    /**
     * 获取某个时间的半年结束时间
     * 比如，someday在前6个月，则该方法得到someday所在年的6月30日24点--即7月1日0点
     * 		someday在后6个月，则该方法得到someday所在年的12月31日24点--即下年的1月1日0点
     * @param someday
     * @return
     * @author chuanpeng.zhang
     * @time 2016-8-4 上午11:01:01
     */
    public static int getHalfYearEndTime(Integer someday) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long)(someday) * 1000));
        int someMonth = calendar.get(Calendar.MONTH) + 1;

        Integer endTime = null;
        if (someMonth >= 1 && someMonth <= 6){
            endTime = getEndDateOfMonth(someday, 6);
        }else if (someMonth >= 7 && someMonth <= 12){
            endTime = getEndDateOfMonth(someday, 12);
        }

        return endTime;
    }

    public static void main(String[]args){

        DateUtil.getDate( null , DateUtil.SDF);

        Integer weekCount = getWeeksOfSomeMonth(DateUtil.getInt());

        LOG.info(getWeekOfMonth(getInt()));
        LOG.info(getFirstDateOfSeason(getInt()));
        LOG.info(getLastDateOfSeason(1475251200));
        LOG.info(getSeason(getInt()));
        LOG.info(getLastDateOfThisMonth());
        LOG.info(getLastDateOfMonthInSomeday(1475251200));

        LOG.info(getFirstDateOfLastMonth());
        LOG.info(getFirstDateOfThisMonth());
        LOG.info(getLastDateOfThisMonth());


        Integer someday = getInt("2015-1-1", SDFDate);
        LOG.info(getFirstDateOfLastYear(someday));
        LOG.info(getLastDateOfLastYear(someday));

        LOG.info(getYear(getFirstDateOfLastYear(someday)).substring(2));

        LOG.info("---getHalfYearStartTime:"+ getHalfYearStartTime(someday));
        LOG.info("---getHalfYearEndTime:"+ getHalfYearEndTime(someday));

        LOG.info("getLastDateOfFirstSeason:"+ getLastDateOfFirstSeason(someday));

        LOG.info(lastConnect(1488330000, getInt()));
    }
}