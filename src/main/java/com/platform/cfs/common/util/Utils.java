package com.platform.cfs.common.util;

import java.io.File;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 * @author yinls
 * @date 2016-3-28
 *
 */
public final class Utils {

	public static final String EMPTY = "";
	
	private static final DateFormat df_nosplit = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	private static final DateFormat dfSSS_nosplit = new SimpleDateFormat(
			"yyyyMMddHHmmssSSS"); // 有毫秒

	private static final DateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	
	private static final DateFormat df1 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	
	private static final DateFormat df2 = new SimpleDateFormat(
			"yyyy-MM-dd");
	
	private static final DateFormat df3 = new SimpleDateFormat(
			"HH:mm:ss");
	
	private static final DateFormat df4 = new SimpleDateFormat(
			"MMddHHmmss");
	
	private static final DateFormat df5 = new SimpleDateFormat(
			"yyyyMMdd");
	
	private static final DateFormat df6 = new SimpleDateFormat(
			"HHmmss");
	private static final DateFormat df7 = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	private static final DateFormat df8 = new SimpleDateFormat(
			"yyyyMMddHHmmssSSS");
	private static final DateFormat df9 = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
	
	private static final String REG_MOBILE_NUM_STR = "^1[34578][0-9]{9}$";
	
	private static final String[] TEL_TELECOM = {"133","153","189","180","181","177","170"};
	
	private static final String REG_EMAIL_STR = "^[a-zA-Z0-9_.]+@[a-zA-Z0-9-]+[.a-zA-Z]+$";
	private static Pattern NUMBER_PATTERN = Pattern.compile("[0-9]*");
	private static Pattern NUMBER_D_PATTERN = Pattern.compile("\\d*");
	private Utils() {}


	public static boolean isMath(String str) {
		if (isEmpty(str)) {
			return false;
		}
		return NUMBER_D_PATTERN.matcher(str).matches();
	}

	/**
	 * 得到随机数
	 * @param length 随机数长度
	 * @return
	 */
	public static String getRandom(int length) {
		Random RAN = new Random();
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; i++) {
			sb.append(RAN.nextInt(10));
		}

		return sb.toString();
	}
	/**
	 * 
	 * YYYYMMddHHmmss
	 * @param sourceDate
	 * @return
	 */
	public static String getFormatTime(Date sourceDate) {
		
		try {
			
			return df_nosplit.format(sourceDate);
		
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 生成接口流水号 14位时间戳 + 3位毫秒数 + 3位随机数 再+ 3位随机数
	 * 共23位
	 * @return
	 */
	public static String generateStreamingNo() {
		return dfSSS_nosplit.format(getCurrentDate()) + getRandom(3) + getRandom(3);
	}
	
	/**
	 * 生成接口流水号 10位时间戳 + 3位毫秒数 + 3位随机数 再
	 * 共23位
	 * @return
	 */
	public static String getStreamingNo() {
		return df4.format(getCurrentDate()) + getRandom(3) + getRandom(3);
	}
	
	/**
	 * 生成correlId = 14位时间戳 + 3位毫秒数 + 3位随机数 再+ 3位随机数
	 * 共23位
	 * @return
	 */
	public static String generateCorrelId() {
		return dfSSS_nosplit.format(getCurrentDate()) + getRandom(3) + getRandom(3);
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 获取格式为"yyyy/MM/dd HH:mm:ss"的时间
	 * @param date
	 * @return
	 */
	public static String convertDateToString9(Date date) {
		return df9.format(date);
	}
	/**
	 * 获取格式为"yyyy-MM-dd HH:mm:ss"的时间
	 * @param date
	 * @return
	 */
	public static String convertDateToString(Date date) {
		return df.format(date);
	}
	/**
	 * 获取格式为"yyyyMMddHHmmssSSS"的时间
	 * @param date
	 * @return
	 */
	public static String convertDateToString8(Date date) {
		return df8.format(date);
	}
	/**
	 * 获取格式为"yyyyMMddHHmmss"的时间
	 * @param date
	 * @return
	 */
	public static String convertDateToString7(Date date) {
		return df7.format(date);
	}
	/**
	 * 获取格式为HHmmss"的时间
	 * @param date
	 * @return
	 */
	public static String convertDateToString6(Date date) {
		return df6.format(date);
	}

	/**
	 * 获取格式为"yyyy-MM-dd "的时间
	 * @param date
	 * @return
	 */
	public static String convertDateToString2(Date date) {
		return df2.format(date);
	}
	
	/**
	 * 获取格式为"yyyyMMdd "的时间
	 * @param date
	 * @return
	 */
	public static String convertDateToString3(Date date) {
		return df5.format(date);
	}
	
	/**
	 * 获取格式为"yyyy-MM-dd HH:mm"的当前时间string
	 * @param date
	 * @return
	 */
	public static String convertDateToString1(Date date) {
		return df1.format(date);
	}
	
	/**
	 * 获取格式为"yyyy-MM-dd HH:mm:ss"的当前时间string
	 * @param date
	 * @return
	 */
	public static String getTimeStampString() {
		return df.format(new Date());
	}

	
	/**
	 * 获取格式为"yyyyMMddHHmmss"的当前时间string
	 * @param date
	 * @return
	 */
	public static String getTimeStamp2String() {
		return df_nosplit.format(new Date());
	}
	
	/**
	 * 获取格式为"yyyyMMddHHmmssSSS"的当前时间string
	 * @param date
	 * @return
	 */
	public static String getTimeStampSSS2String() {
		return dfSSS_nosplit.format(new Date());
	}
	
	/**
	 * 获取格式为"HH:mm:ss"的时间
	 * @param date
	 * @return
	 */
	public static String getTimeString(Date date) {
		return df3.format(date);
	}
	
	/**
	 * 获取格式为"MMddHHmmss"的时间
	 * @param date
	 * @return
	 */
	public static String convertMMDateToStr(Date date) {
		return df4.format(date);
	}
	
	/**
	 * 获取格式为"MMddHHmmss"的时间
	 * @param date
	 * @return
	 */
	public static String convertYYDateToStr(Date date) {
		return df5.format(date);
	}
	
	/**
	 * 获取格式为"MMddHHmmss"的时间
	 * @param date
	 * @return
	 */
	public static String convertTimeToStr(Date date) {
		return df6.format(date);
	}

	/**
	 * 返回当前时间TimeStamp
	 * @return
	 */
	public static Timestamp getTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 得到时间戳 = 14位时间 + 4位随机数
	 * @return java.lang.String
	 */
	public static String getTimeStampAndRand() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(new Date()) + getRandom(4);
	}

	/**
	 * 得到时间戳 = 14位时间 + 3位毫秒数
	 * @return java.lang.String
	 */
	public static String getTimeStampSSS() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return df.format(new Date());
	}
	
	/**
	 * 根据时间戳(14位)获取毫秒数
	 * @param timestamp
	 * @return
	 */
	public static Long getTimeStampLong(String timestamp) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Long res = 0L;
		try {
			res = df.parse(timestamp).getTime();
		} catch (ParseException e) {
			return null;
		}
		return res;
	}

	/**
	 * 根据时间戳(17位)获取毫秒数
	 * @param timestamp
	 * @return
	 */
	public static Long getTimeStampSSSLong(String timestamp) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Long res = 0L;
		try {
			res = df.parse(timestamp).getTime();
		} catch (ParseException e) {
			return null;
		}
		return res;
	}
	
	/**
	 * String to Date((yyyy-MM-dd HH:mm:ss))
	 * @param dateStr
	 * @return
	 */
	public static Date convertStringToDate(String dateStr) {
		Date date = null;
		try {
			date = (Date)df.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
		return date;
	}
	
	/**
	 * 把格式为：yyyyMMddHHmmss 转成Date
	 * @param dateStr
	 * @return
	 */
	public static Date convertStringToDate6(String dateStr) {
		Date date = null;
		if(isNotEmpty(dateStr)){
			try {
				date = (Date)df_nosplit.parse(dateStr);
			} catch (ParseException e) {
				return null;
			}
		}
		return date;
	}
	/**
	 * 把格式为：yyyy/MM/dd HH:mm:ss 转成Date
	 * @param dateStr
	 * @return
	 */
	public static Date convertStringToDate9(String dateStr) {
	    Date date = null;
	    if(isNotEmpty(dateStr)){
	        try {
	            date = (Date)df9.parse(dateStr);
	        } catch (ParseException e) {
	            return null;
	        }
	    }
	    return date;
	}

	/**
	 * String to Date(yyyy-mm-dd)
	 * @param dateStr
	 * @return
	 */
	public static Date convertStringToDate2(String dateStr) {
		Date date = null;
		try {
			date = (Date)df2.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
		return date;
	}
	
	/**
	 * String to Date(yyyyMMdd)
	 * @param dateStr
	 * @return
	 */
	public static Date convertStringToDate5(String dateStr) {
		Date date = null;
		try {
			date = (Date)df5.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
		return date;
	}
	
	/**
	 * 计算时间 举例： Date date = new Date(); this.calculateDateTime(date,
	 * Calendar.DATE, 1); 表示加一天
	 * @param date
	 * @param field
	 * @param amount
	 * @return
	 */
	public static Date calculateDateTime(Date date, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);

		return cal.getTime();
	}

	/**
	 * 对字符串进行编码，这样就可以在所有的计算机上读取该字符串 返回值: 已编码的 string 的副本。其中某些字符被替换成了十六进制的转义序列。
	 * 说明: 该方法不会对 ASCII 字母和数字进行编码，也不会对下面这些 ASCII 标点符号进行编码： - _ . ! ~ * ' ( )
	 * 。其他所有的字符都会被转义序列替换。
	 * @param src
	 * @return
	 */
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);

		for (i = 0; i < src.length(); i++) {

			j = src.charAt(i);

			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j)) {
				tmp.append(j);
			} else if (j < 256) {
				tmp.append("%");
				if (j < 16) {
					tmp.append("0");
				}
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	/**
	 * 解码或反转义
	 * @param src
	 * @return
	 */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src
							.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src
							.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	public static <T> boolean in(T var, List<T> vars) {
		if (isNull(vars)) {
			return false;
		}
		for (Object temp : vars) {
			if (temp.equals(var)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isEmpty(Collection<?> c) {
		if (isNull(c)) {
			return true;
		}
		return c.size() < 1 ? true : false;
	}
	
	public static boolean isEmpty(Map<?, ?> m){
		if(isNull(m)) {
            return true;
        }
		return m.isEmpty();
	}
	
	public static boolean isEmpty(String s) {
		if (isNull(s)) {
			return true;
		}
		return s.trim().length() < 1 ? true : false;
	}

	public static <T> boolean isEmpty(T[] objs) {
		if (isNull(objs)) {
			return true;
		}
		return objs.length <= 0;
	}

	public static boolean isEqual(String s1, String s2) {
		if (isEmpty(s1) && isEmpty(s2)) {
			return true;
		}

		return s1 == null ? false : s1.equals(s2);
	}

	public static boolean isNotEmpty(Collection<?> c) {
		return !isEmpty(c);
	}
	public static boolean isNotEmpty(Map<?, ?> m){
		return !isEmpty(m);
	}
	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

	public static <T> boolean isNotEmpty(T[] objs) {
		return isNotNull(objs) && objs.length > 0;
	}

	/**
	 * 去掉字符串两端的控制符(control characters, char <= 32)  
	 * 如果输入为null或""，则返回"" 
	 * @param str
	 * @return
	 */
	public static String trimToEmpty(String str) {
        return str == null ? EMPTY : str.trim();
    }
	
	public static boolean isNotNull(Object obj) {
		return obj != null ? true : false;
	}

	public static boolean isNull(Object obj) {
		return obj == null ? true : false;
	}
	
	public static boolean isTrue(Integer obj) {
		return obj != null && obj.intValue()>0 ? true : false;
	}
	public static boolean isTrue(Boolean obj) {
		return obj != null && obj.booleanValue() ? true : false;
	}

	public static <T> boolean notIn(T var, List<T> vars) {
		return !in(var, vars);
	}

	public static List<Integer> returnNotIn(List<Integer> listA,
			List<Integer> listB) {
		final List<Integer> notIns = new ArrayList<Integer>();
		if (isEmpty(listA)) {
			return notIns;
		}
		if (isEmpty(listB)) {
			notIns.addAll(listA);
			return notIns;
		}
		for (Integer itemA : listA) {
			boolean notIn = true;

			for (Integer itemB : listB) {
				if (itemB.equals(itemA)) {
					notIn = false;
					break;
				}
			}

			if (notIn) {
				notIns.add(itemA);
			}
		}
		return notIns;
	}

	public static boolean hasSingleElement(Collection<?> c) {
		if (isEmpty(c)) {
			return false;
		}
		return c.size() == 1 ? true : false;
	}

	public static boolean hasManyElements(Collection<?> c) {
		if (isEmpty(c)) {
			return false;
		}
		return c.size() > 1 ? true : false;
	}

	public static void split(List<String> list, StringBuffer sb, String sep) {

		if (sb.indexOf(sep) == -1) {
			if (sb.length() != 0) {
                list.add(sb.toString());
            }
			return;
		}
		if (sb.indexOf(sep) == 0) {
			list.add("");
			sb.delete(0, 1);
		} else {
			list.add(sb.substring(0, sb.indexOf(sep)));
			if (sb.indexOf(sep) + 1 != sb.length()) {
                sb.delete(0, sb.indexOf(sep) + 1);
            } else {
                sb.delete(0, sb.indexOf(sep));
            }
		}
		split(list, sb, sep);
	}

	public static String getStr(String str) {
		return str == null ? "" : str.trim();
	}
	public static int getInt(Integer count) {
		return count == null ? 0 : count.intValue();
	}

	public static String getUUID() {
		UUID uid = UUID.randomUUID();
		return uid.toString();
	}
	/**
	 * 日期操作
	 * @param date 操作的日期
	 * @param unit 操作的单位  @see日期Calendar.DATE,Calendar
	 * @param count 操作的数量，可为负数
	 * @return
	 * 如果取2012-08-01前一天，则unit = Calendar.DATE，count=1
	 */
	public static Date dateAdd(Date date,int unit,int count){
		if (date == null) {
            return null;
        }
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(unit, count);
//		cal.add(Calendar.DATE, 7);
		return cal.getTime();
	}
	/**
	 * 日期操作：如：20130408115959
	 * @param date 操作的日期时分秒为23:59:59
	 * @param unit 操作的单位  @see日期Calendar.DATE,Calendar
	 * @param count 操作的数量，可为负数
	 * @return
	 * 如果取2012-08-01前一天，则unit = Calendar.DATE，count=1
	 */
	@Deprecated
	public static Date dateLastAdd(Date date,int unit,int count){
		if (date == null) {
            return null;
        }
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(unit, count);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}
	
	/**
	 * 取得星期几
	 * @param date
	 * @param type Calendar.DAY_OF_WEEK 则为星期几(1周日Calendar.Sunday，2周一，...7周六)
	 * @return  
	 */
	public static int getDayOfWeek(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);//1周日，2周一，...7周六 
		return dayOfWeek;
	}
	
	/**
	 * 取得第几周
	 * @param date
	 * @return  
	 */
	public static int getWeekOfYear(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
		return weekOfYear;
	}
	
	/**
	 * 用指定格式分析字符串，得到日期值
	 * @param str
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String str,String format) throws ParseException{
		if (str == null) {
            return null;
        }
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(str);
	}
	
	/**
	 * 用指定格式将日期转成字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date,String format){
		if (date==null) {
            return null;
        }
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 将指定时间的日期置为指定时间的日期
	 * @param valueDate
	 * @param srcDate
	 * @return
	 */
	public static Date setSameDate(Date srcDate,Date valueDate){
		if (srcDate ==null || valueDate == null) {
            return null;
        }
		Calendar calSrc = Calendar.getInstance();
		calSrc.setTime(srcDate);
		Calendar calTarget = Calendar.getInstance();
		calTarget.setTime(valueDate);
		calSrc.set(Calendar.YEAR, calTarget.get(Calendar.YEAR));
		calSrc.set(Calendar.MONTH, calTarget.get(Calendar.MONTH));
		calSrc.set(Calendar.DAY_OF_MONTH, calTarget.get(Calendar.DAY_OF_MONTH));
		return calSrc.getTime();
	}
	
	/**
	 * 将指定时间的时间置为指定时间的时间
	 * @param valueDate
	 * @param srcDate
	 * @return
	 */
	public static Date setSameTime(Date srcDate,Date valueDate){
		if (srcDate ==null || valueDate == null) {
            return null;
        }
		Calendar calSrc = Calendar.getInstance();
		calSrc.setTime(srcDate);
		Calendar calTarget = Calendar.getInstance();
		calTarget.setTime(valueDate);
		calSrc.set(Calendar.HOUR_OF_DAY, calTarget.get(Calendar.HOUR_OF_DAY));
		calSrc.set(Calendar.MINUTE, calTarget.get(Calendar.MINUTE));
		calSrc.set(Calendar.SECOND, calTarget.get(Calendar.SECOND));
		return calSrc.getTime();
	}
	
	/**
	 * 计算两个时间的时间差,
	 * 0表示两个时间值相等，>0表示前一时间大于后一时间，<0表示小于
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long diffTime(Date date1,Date date2){
		return date1.getTime() - date2.getTime();
	}
	
	/**
	 * 获取全路径
	 * @param domain 域名
	 * @param url 路径
	 * @return
	 */
	public static String getFullUrl(String domain,String url){
		if (url!=null && !url.startsWith("http")){
			String aDomain = domain!=null?domain:"";
			StringBuffer sb = new StringBuffer(aDomain);
			if (!aDomain.endsWith("/")) {
                sb.append("/");
            }
			if (url.startsWith("/")) {
                sb.append(url.substring(1));
            } else {
                sb.append(url);
            }
			return sb.toString();
				
		}
		return url;
	}


	/**
	 * 判断字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Matcher isNum = NUMBER_PATTERN.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	public static int ifnullZero(Integer value){
		return ifnull(value,0);
	}
	
	public static int ifnull(Integer value,int defaultValue){
		return value!=null?value.intValue():defaultValue;
	}
	
	/**
	 *  获取文件的文件夹路径
	 * @param fileUrl 文件路径
	 * @return
	 */
	public static String getFileDir(String fileUrl){
		String fileDir = "";
		if(isEmpty(fileUrl)){
			return fileDir;
		}
		int biasIndex = fileUrl.lastIndexOf(File.separator);
		if(biasIndex > 0) {
			fileDir = fileUrl.substring(0, biasIndex);
		}
		return fileDir;
	}
	
	//将秒数转换为时、分、秒
	public static String convertSecondTimeToString(Integer secondTime){
		if(Utils.isNull(secondTime)){
			return "";
		}
		String timeStr = "";
		Integer hour=secondTime/3600;           //小时
		Integer minute=secondTime%3600/60;      //分钟
		Integer second=secondTime%60;           //秒
		if(Utils.isNotNull(hour) && hour != 0){
			timeStr = timeStr + hour + "小时";
		}
		if(Utils.isNotNull(minute) && minute != 0){
			timeStr = timeStr + minute + "分钟";
		}
		if(Utils.isNotNull(second)){
			timeStr = timeStr + second + "秒";
		}
		return timeStr;
	}
	
	/**
	 * 检验时间格式是否为：yyyyMMddhhmmss
	 * @param yyyyMMddHHmmss
	 * @return true表示符合，false表示不合符
	 */
	public static boolean isValidDate(String date) {
		int[] DAYS = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if (date == null || date.length() != 14) {
			return false;
		}
		try {
			int year = Integer.parseInt(date.substring(0, 4));
			if (year <= 0) {
                return false;
            }
			int month = Integer.parseInt(date.substring(4, 6));
			if (month <= 0 || month > 12) {
                return false;
            }
			int day = Integer.parseInt(date.substring(6, 8));
			if (day <= 0 || day > DAYS[month]) {
                return false;
            }
			if (month == 2 && day == 29 && !isGregorianLeapYear(year)) {
				return false;
			}
			int hour = Integer.parseInt(date.substring(8, 10));
			if (hour < 0 || hour > 23) {
                return false;
            }
			int minute = Integer.parseInt(date.substring(10, 12));
			if (minute < 0 || minute > 59) {
                return false;
            }
			int second = Integer.parseInt(date.substring(12, 14));
			if (second < 0 || second > 59) {
                return false;
            }

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 检验时间格式是否为：yyyyMMdd
	 * @param yyyyMMddHHmmss
	 * @return true表示符合，false表示不合符
	 */
	public static boolean isValidDate2(String date) {
		int[] DAYS = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if (date == null || date.length() != 8) {
			return false;
		}
		try {
			int year = Integer.parseInt(date.substring(0, 4));
			if (year <= 0) {
                return false;
            }
			int month = Integer.parseInt(date.substring(4, 6));
			if (month <= 0 || month > 12) {
                return false;
            }
			int day = Integer.parseInt(date.substring(6, 8));
			if (day <= 0 || day > DAYS[month]) {
                return false;
            }
			if (month == 2 && day == 29 && !isGregorianLeapYear(year)) {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/** 判断是否闰年 */
	public static final boolean isGregorianLeapYear(int year) {
		return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
	}

	/** 把yyyyMMddHHmmss格式的字符串时间格式转换成date */
	public static Date formatDate(String dateStr) {
		Date date = null;
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			date = (Date)format.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
		return date;
	}
	
	/** 判断是否大于当前时间系统day(多少)天,true为大于 */
	public static boolean isGreaterThan30(String timeStamp,int day) {
		Date now = new Date();
		Date date = formatDate(timeStamp);
		long nowDate = now.getTime()/1000;		//系统当前时间，单位秒
		long srcDate = date.getTime()/1000;		//要做比较的时间,单位秒
		long date30 = day * 24 * 3600 ;			//30天的秒数
		if((srcDate - nowDate) > date30) {
			return true;
		}
		return false;
	} 
	
	/** 是否晚于当前系统时间60分钟, true为晚于
	 *  @param min 分钟数
	 * */
	public static boolean isLateMin(String timeStamp,int min) {
		Date now = new Date();
		Date date = formatDate(timeStamp);
		long nowDate = now.getTime()/1000;		//系统当前时间，单位秒
		long srcDate = date.getTime()/1000;		//要做比较的时间,单位秒
		long date60 = min * 60;					//min分钟的秒数
		
		if((srcDate - nowDate) > date60) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断两个时间是否在 指定小时数的范围内
	 * @param date1
	 * @param date2
	 * @param hourCount
	 * @return
	 * @throws Exception
	 */
	public static boolean judgmentDate(Date date1, Date date2, int hourCount) {  
	    long cha = date2.getTime() - date1.getTime();  
	    if(cha<0){ 
	      return false;  
	    } 
	    double result = cha * 1.0 / (1000 * 60 * 60); 
	    if(result<=hourCount){  
	         return true;  
	    }else{  
	         return false;  
	    }  
	} 
	
	/**
	 * 是否有逗号
	 * @param str
	 * @return true 表示存在逗号
	 */
	public static boolean isHaveComma(String str) {
		if((str.indexOf(",")) != -1) {
			//有逗号
			return true;
		}
		return false;
	}
	
	/** 
	 * 检查email格式是否正确
	 */
	public static boolean checkEmailFormat(String email) {
		if(Utils.isEmpty(email)) {
			return false;
		}
		Pattern pattern = Pattern.compile(REG_EMAIL_STR);
        Matcher matcher = pattern.matcher(email);
        
        if (!matcher.matches()) {
            return false;
        }
		
        return true;
	}
	
	/**
	 * 通过号码前三位判断是否电信本网号码
	 * @param mobileNum
	 * @return
	 */
	public static boolean isTelecomMobile(String mobileNum) {
		if(isEmpty(mobileNum)) {
			return false;
		}
		if(mobileNum.length() != 11) {
			return false;
		}
		String telHead = mobileNum.substring(0, 3);
		
		for(String str : TEL_TELECOM) {
			if(str.equals(telHead)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 将参数拼装到url
	 * @param uri
	 * @param dataMap
	 * @return
	 */
	public static String dataAppendToUri(String uri, Map<String, String> dataMap){
		if (Utils.isEmpty(dataMap)) {
			return uri;
		}
		if(Utils.isEmpty(uri)) {
            return uri;
        }
		StringBuffer temp = new StringBuffer(uri);
		StringBuffer dataStr = new StringBuffer();
		try{
			for (String key : dataMap.keySet()) {
				String val=dataMap.get(key);
				if(Utils.isNotNull(val)){
					// 中文乱码
					dataStr.append(key).append("=")
							.append(URLEncoder.encode(dataMap.get(key), "UTF-8"))
							.append("&");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(Utils.isNotEmpty(dataStr.toString())){
			dataStr.deleteCharAt(dataStr.length() - 1);
			if (uri.lastIndexOf("/") == (uri.length() - 1)) {
				temp.deleteCharAt(uri.length() - 1);
			}
			int index = uri.lastIndexOf("/");
			String subFix = temp.substring(index);
			if (!subFix.contains("?")) {
				dataStr.insert(0, "?");
			}else{
				dataStr.insert(0, "&");
			}
			temp.append(dataStr);
		}
		return temp.toString();
	}
	
	/** 把:兆或者KB为单位，转成：以B为单位的*/
	public static int mbOrKbToB(String str) {
		
		if(Utils.isEmpty(str)) {
			return 0;
		}
		str = str.toUpperCase();
		//判断格式是否是以MB(M)或者是KB(k)为单位的字符串
		if(str.indexOf("M") == -1 && str.indexOf("K") == -1) {
			return 0;
		}
		int intB = 0;
		int flag = 0;//识别传进来的是MB还是KB的标记 0默认MB
		if(str.indexOf("M") != -1) {
			str = str.substring(0, str.indexOf("M"));
		}
		if(str.indexOf("K") != -1) {
			str = str.substring(0, str.indexOf("K"));
			flag = 1;
		}
		if(1 == flag) {
			Double doubleB = Double.valueOf(str);
			intB = (int)Math.round(doubleB * 1024); 
		} else {
			Double doubleB = Double.valueOf(str);
			intB = (int)Math.round(doubleB * 1024 * 1024); 
		}
		return intB;
	}
	
	/** 判断字符串是否是某个前缀开头
	 * srcStr:传入的原字符串
	 * isStr: 是否是此前缀
	 */
	public static boolean isStartsWith(String srcStr,String isStr) {
		if(srcStr.startsWith(isStr, 0)) {
			return true;
		}
		return false;
	}
	
	//将分转换为元.角分
	public static String convertPannyToString(Integer panney){
		if(Utils.isNull(panney)){
			return "";
		}
		String moneyStr = "";
		Integer yuan = panney/100;       //元
		Integer jiao = panney%100/10;    //角
		Integer fen = panney%100%10; //分
		if(Utils.isNotNull(yuan)){
			moneyStr = yuan + ".";
		}
		if(Utils.isNotNull(jiao)){
			moneyStr = moneyStr + jiao;
		}
		if(Utils.isNotNull(fen)){
			moneyStr = moneyStr + fen;
		}
		return moneyStr;
	}
	
	  //验证手机号码合法性 2014-4-14
	/**
	 * true：格式正确
	 * false: 格式错误
	 * @param mobiles
	 * @return
	 */
    public static boolean isMobileNO(String mobiles){ 
		Pattern p = Pattern.compile(REG_MOBILE_NUM_STR); 
		Matcher m = p.matcher(mobiles); 
		return m.matches(); 
	} 
      
    /**
     * 2014-5-9
     * 验证邮箱地址是否正确
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
    	boolean flag = false;
    	try{
    		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    		Pattern regex = Pattern.compile(check);
    		Matcher matcher = regex.matcher(email);
    		flag = matcher.matches();
    	}catch(Exception e){
    		flag = false;
    	}
    	return flag;
    }
  
  	/**
  	 * 获取年份
  	 * //TODO 添加方法功能描述
  	 * @return
  	*/
	public static int getYear(){
	    Calendar calendar = Calendar.getInstance();
        int yearNumber = calendar.get(Calendar.YEAR);
	    return  yearNumber; 
	}
	
	 /** 
     * 得到本月最后一天
     * @Methods Name getLastDayOfMonth 
     * @return String 
     */  
    public static String getLastDayOfMonth(){  
        Calendar cDay = Calendar.getInstance();     
        cDay.setTime(new Date());
        cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
        return convertDateToString2(cDay.getTime());     
    }  
    
    /** 
    * 获得指定日期的前一天 
    * @param specifiedDay 
    * @return 
    * @throws Exception 
    */ 
    public static String getSpecifiedDayBefore(String specifiedDay){ 
    Calendar c = Calendar.getInstance(); 
    Date date=null; 
    try { 
    date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay); 
    } catch (ParseException e) { 
    e.printStackTrace(); 
    } 
    c.setTime(date); 
    int day=c.get(Calendar.DATE); 
    c.set(Calendar.DATE,day-1); 
    String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
    return dayBefore; 
    } 
    
    //计算相差的月数
    public static int getMonthSpace(Date date1, Date date2)
            throws ParseException {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);

        int year = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
        int month = c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
        int day = c1.get(Calendar.DATE) - c2.get(Calendar.DATE);
        if(year >= 0){
        	if(month>0){
        		if(day > 0){
            		return year * 12 + month + 1;
            	}else{
            		return year * 12 + month;
            	}
        	}else if(month==0){
        		if(day >= 0){
            		return year * 12 + month + 1;
            	}else{
            		return year * 12 + month;
            	}
        	}
        	else{
        		if(day >= 0){
            		return year * 12 + month;
            	}else{
            		return year * 12 + month - 1;
            	}
        	}
        }else{
        	if(month>=0){
        		if(day >= 0){
            		return year * 12 + month;
            	}else{
            		return year * 12 + month - 1;
            	}
        	}else{
        		if(day >= 0){
            		return year * 12 + month;
            	}else{
            		return year * 12 + month -1;
            	}
        	}
        }
    }
    
    //计算相差的天数
    public static int daysBetween(Date smdate,Date bdate) throws ParseException {    
        smdate=df2.parse(df2.format(smdate));  
        bdate=df2.parse(df2.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
        return Integer.parseInt(String.valueOf(between_days));           
    } 
    
    //计算同年相差的天数
    public static int daysBetween2(Date sdate,Date edate) throws ParseException {    
    	 Calendar aCalendar = Calendar.getInstance();
         aCalendar.setTime(sdate);
         int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
         aCalendar.setTime(edate);
         int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
         return day2 - day1;           
    }


	/**
	 * null值转换为空字符串
	 *
	 * @param o
	 * @return
	 */
	public static String nulltostr(Object o) {
		if (o == null) {
            return "";
        }
		return o.toString();
	}
	/**
	 * null值转换为0
	 * @param o
	 * @return
	 */
	public static int nulltoZero(Object o) {
		if (o == null) {
            return 0;
        }
		try {
			return Integer.parseInt(o.toString());
		} catch (Exception e) {
			return 0;
		}
	}
    
    public static void main(String[] args) throws ParseException {
    	int day = daysBetween(convertStringToDate2("2016-04-10"), convertStringToDate2("2015-10-10"));
    	System.out.println(day);
    }
}
