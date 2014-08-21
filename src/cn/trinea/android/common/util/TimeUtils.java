package cn.trinea.android.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <ul>
 * <li>SimpleDateFormat DEFAULT_DATE_FORMAT = new
 * SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");</li>
 * <li>TimeUtils.getCurrentTimeInString() =2014-03-11 16:07:07</li>
 * <li>TimeUtils.getCurrentTimeInLong() =1394525227103</li>
 * <li>TimeUtils.getTime(Long.valueOf("1394525227103")) =2014-03-11 16:07:07</li>
 * <li>TimeUtils.getCurrentTimeInString(DEFAULT_DATE_FORMAT) =2014年03月11日
 * 16:14:39</li>
 * <li>TimeUtils.getTime(Long.valueOf("1394525227103"),DEFAULT_DATE_FORMAT)
 * =2014年03月11日 16:07:07</li>
 * </ul>
 */
public class TimeUtils {

	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * long time to string
	 * 
	 * @param timeInMillis
	 * @param dateFormat
	 * @return
	 */
	public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
		return dateFormat.format(new Date(timeInMillis));
	}

	/**
	 * 将Date转换为String
	 * 
	 * @param date
	 * @param formatStr
	 *            举例 yyyy-MM-dd
	 * @return
	 */
	public static String DateToString(Date date, String formatStr) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
		return dateFormat.format(date);
	}

	/**
	 * 字符串转换到时间格式
	 * 
	 * @param dateStr
	 *            需要转换的字符串
	 * @param formatStr
	 *            需要格式的目标字符串 举例 yyyy-MM-dd
	 * @return Date 返回转换后的时间
	 * @throws ParseException
	 *             转换异常
	 */
	public static Date StringToDate(String dateStr, String formatStr) {
		DateFormat sdf = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
	 * 
	 * @param timeInMillis
	 * @return
	 */
	public static String getTime(long timeInMillis) {
		return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
	}

	/**
	 * get current time in milliseconds
	 * 
	 * @return
	 */
	public static long getCurrentTimeInLong() {
		return System.currentTimeMillis();
	}

	/**
	 * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
	 * 
	 * @return
	 */
	public static String getCurrentTimeInString() {
		return getTime(getCurrentTimeInLong());
	}

	/**
	 * get current time in milliseconds
	 * 
	 * @return
	 */
	public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
		return getTime(getCurrentTimeInLong(), dateFormat);
	}

	/***
	 * 
	 * 
	 * 得到当前时间
	 * 
	 * @param Format
	 *            eg : "yyyy-MM-dd hh:mm:ss" eg : "yyyy年MM月dd日 HH:mm:ss" eg :
	 *            "yyyy"
	 * 
	 * @return
	 */
	public static String getCurrentTime(String Format) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(Format);
		String date = sDateFormat.format(new java.util.Date());
		return date.toString();
	}

	/**
	 * 判断时间是否在指定的时间列表中
	 * 
	 * @param currentDate
	 * @return 存在true,不存在 false
	 */
	public static boolean isMark(Date currentDate) {
		String[] str = { "2014年5月07日", "2014年05月06日", "2014年05月09日",
				"2014年04月07日" };
		List<Date> list = new ArrayList<Date>();
		String yyString = TimeUtils.DateToString(currentDate, "yyyy年MM月dd日");
		currentDate = TimeUtils.StringToDate(yyString, "yyyy年MM月dd日");
		for (int i = 0; i < str.length; i++) {
			list.add(TimeUtils.StringToDate(str[i], "yyyy年MM月dd日"));
		}
		if (list.contains(currentDate)) {
			return true;
		} else {
			return false;
		}
	}
}
