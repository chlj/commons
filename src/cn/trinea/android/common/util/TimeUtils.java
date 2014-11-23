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
 * SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");</li>
 * <li>TimeUtils.getCurrentTimeInString() =2014-03-11 16:07:07</li>
 * <li>TimeUtils.getCurrentTimeInLong() =1394525227103</li>
 * <li>TimeUtils.getTime(Long.valueOf("1394525227103")) =2014-03-11 16:07:07</li>
 * <li>TimeUtils.getCurrentTimeInString(DEFAULT_DATE_FORMAT) =2014��03��11��
 * 16:14:39</li>
 * <li>TimeUtils.getTime(Long.valueOf("1394525227103"),DEFAULT_DATE_FORMAT)
 * =2014��03��11�� 16:07:07</li>
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
	 * ��Dateת��ΪString
	 * 
	 * @param date
	 * @param formatStr
	 *            ���� yyyy-MM-dd
	 * @return
	 */
	public static String DateToString(Date date, String formatStr) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
		return dateFormat.format(date);
	}

	/**
	 * �ַ���ת����ʱ���ʽ
	 * 
	 * @param dateStr
	 *            ��Ҫת�����ַ���
	 * @param formatStr
	 *            ��Ҫ��ʽ��Ŀ���ַ��� ���� yyyy-MM-dd
	 * @return Date ����ת�����ʱ��
	 * @throws ParseException
	 *             ת���쳣
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
	 * �õ���ǰʱ��
	 * 
	 * @param Format
	 *            eg : "yyyy-MM-dd hh:mm:ss" eg : "yyyy��MM��dd�� HH:mm:ss" eg :
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
	 * ��ʱ���ַ���ת��Ϊlong
	 * 
	 * @param dateStr
	 *            eg : 2013-04-12
	 * @param formatStr
	 *            eg : "yyyy-MM-dd hh:mm:ss" eg : "yyyy��MM��dd�� HH:mm:ss"
	 * @return
	 */
	public static Long getLongByDateString(String dateStr, String formatStr) {
		long millionSeconds = 0;
		Date dt = StringToDate(dateStr, formatStr);
		millionSeconds = dt.getTime();
		return millionSeconds;
	}

	/**
	 * �ж�ʱ���Ƿ���ָ����ʱ���б���
	 * 
	 * @param currentDate
	 * @return ����true,������ false
	 */
	public static boolean isMark(Date currentDate) {
		String[] str = { "2014��5��07��", "2014��05��06��", "2014��05��09��",
				"2014��04��07��" };
		List<Date> list = new ArrayList<Date>();
		String yyString = TimeUtils.DateToString(currentDate, "yyyy��MM��dd��");
		currentDate = TimeUtils.StringToDate(yyString, "yyyy��MM��dd��");
		for (int i = 0; i < str.length; i++) {
			list.add(TimeUtils.StringToDate(str[i], "yyyy��MM��dd��"));
		}
		if (list.contains(currentDate)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �õ�����ʱ��֮��ķ��Ӳ�
	 * 
	 * @param endtime
	 *            2014/9/3 10:34:00
	 * @param begintime
	 *            2014/9/3 6:22:00
	 * @param formate
	 *            yyyy/MM/dd hh:mm:ss
	 * @return �쳣���� endtime
	 */
	public static String getTimedifference(String endtime, String begintime,
			String formate) {
		try {
			long nd = 1000 * 24 * 60 * 60;// һ��ĺ�����
			long nh = 1000 * 60 * 60;// һСʱ�ĺ�����
			long nm = 1000 * 60;// һ���ӵĺ�����

			Date dt1 = StringToDate(begintime, formate);
			Date dt2 = StringToDate(endtime, formate);

			long diff = dt2.getTime() - dt1.getTime();
			long day = diff / nd;// ����������
			long hour = diff % nd / nh;// ��������Сʱ
			long min = diff % nd % nh / nm;// �������ٷ���

			long result = day * 24 * 60 + hour * 60 + min;

			String s = String.valueOf(result);
			return s;
		} catch (Exception e) {
			return endtime;
		}
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String getTimeChange(String str) {
		if (str == null) {
			return "";
		} else {
			str = str.replace("0:00:00", "");
			str = str.replace("00:00:00", "-");
			str = str.replace("/", "-").toString().trim();
			return str;
		}
	}

}
