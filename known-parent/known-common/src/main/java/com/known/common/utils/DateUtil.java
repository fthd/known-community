package com.known.common.utils;

import com.known.common.enums.DateTimePatternEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {
	
	public final static SimpleDateFormat dateFormater = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public final static SimpleDateFormat dateFormater2 = new SimpleDateFormat(
            "yyyy-MM-dd");

    public final static SimpleDateFormat dateFormater3 = new SimpleDateFormat(
            "yyyy");
    private static final SimpleDateFormat dateFormater4 = new SimpleDateFormat(
            "HH:mm");

    private static final SimpleDateFormat dateFormater5 = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");
    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;

    private static final String JUST_NOW = "刚刚";
    // private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";

    public static void main(String[] args) {
//        System.out.println(friendly_time("2016-11-24 10:12:12"));
    	SimpleDateFormat sdf = new SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern());
    	System.out.println(friendly_time(sdf.format(new Date())));
    	
    }

    public static String friendly_time(String dateStr) {
        Date date = null;
        Date day = null;
        Date curDate = new Date();
        Date curDay = null;
        try {
            date = dateFormater.parse(dateStr);
            day = dateFormater2.parse(dateStr);
            curDay = dateFormater2.parse(dateFormater2.format(curDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long delta = curDay.getTime() - day.getTime();
        // 如果是当天，精确到时分秒
        if (delta == 0) {
            delta = curDate.getTime() - date.getTime();
        }
        if (delta < 1L * ONE_MINUTE) {
            // long seconds = toSeconds(delta);
            // return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
            return JUST_NOW;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天" + "  " + dateFormater4.format(date);
        }
        if (delta < 72L * ONE_HOUR) {
            return "前天" + "  " + dateFormater4.format(date);
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            if (days > 2 && days <= 7) {
                return days + ONE_DAY_AGO;
            }
        }
        return dateFormater5.format(date);
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }


	  /** 存放不同的日期模板格式的sdf的Map */
	  private static ThreadLocal<Map<String, SimpleDateFormat>> sdfMap = new ThreadLocal<Map<String, SimpleDateFormat>>() {
	    @Override
	    protected Map<String, SimpleDateFormat> initialValue() {
	      return new HashMap<String, SimpleDateFormat>();
	    }
	  };

	  /**
	   * 返回一个SimpleDateFormat,每个线程只会new一次pattern对应的sdf
	   * 
	   * @param pattern
	   * @return
	   */
	  private static SimpleDateFormat getSdf(final String pattern) {
	    Map<String, SimpleDateFormat> tl = sdfMap.get();
	    SimpleDateFormat sdf = tl.get(pattern);
	    if (sdf == null) {
	      sdf = new SimpleDateFormat(pattern);
	      tl.put(pattern, sdf);
	    }
	    return sdf;
	  }

	  /**
	   * 这样每个线程只会有一个SimpleDateFormat
	   * 
	   * @param date
	   * @param pattern
	   * @return
	   */
	  public static String format(Date date, String pattern) {
	    return getSdf(pattern).format(date);
	  }

	  public static Date parse(String dateStr, String pattern)
	       {
	    try {
			return getSdf(pattern).parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	  }
	 /**
	  * 计算两个Date之间差了多少天
	  */
	  public static int daysBetween(Date smdate,Date bdate) 
	    {    
	        SimpleDateFormat sdf=new SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD.getPattern());  
	        try {
				smdate=sdf.parse(sdf.format(smdate));
		        bdate=sdf.parse(sdf.format(bdate));  
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(smdate);    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(bdate);    
	        long time2 = cal.getTimeInMillis();         
	        long between_days=(time2-time1)/(1000*3600*24);  
	            
	       return Integer.parseInt(String.valueOf(between_days));           
	    }    
	}
