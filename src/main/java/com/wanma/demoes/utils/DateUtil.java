package com.wanma.demoes.utils;

import com.wanma.demoes.entity.PagingParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
	private static Logger log = LoggerFactory.getLogger(DateUtil.class);

	/** yyyy-MM-dd HH:mm:ss */
	public static final String TYPE_COM_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	/** yyyy-MM-dd HH:mm */
	public static final String TYPE_COM_YMDHM = "yyyy-MM-dd HH:mm";
	/** MM-dd HH:mm:ss */
	public static final String TYPE_COM_MDHMS = "MM-dd HH:mm:ss";
	/** MM-dd HH:mm */
	public static final String TYPE_COM_MDHM = "MM-dd HH:mm";
	/** yyyy-MM-dd */
	public static final String TYPE_COM_YMD = "yyyy-MM-dd";
	/** yyyy-MM */
	public static final String TYPE_COM_YM = "yyyy-MM";
	/** yyyy */
	public static final String TYPE_COM_YY= "yyyy";
	/** MM-dd */
	public static final String TYPE_COM_MD = "MM-dd";
	/** HH:mm:ss */
	public static final String TYPE_COM_HMS = "HH:mm:ss";
	/** HH:mm */
	public static final String TYPE_COM_HM = "HH:mm";
	/** HH */
	public static final String TYPE_COM_HH = "HH";


	/** yyyyMMddHHmmss */
	public static final String TYPE_COMBINE_YMDHMS = "yyyyMMddHHmmss";
	/** yyyyMMddHHmm */
	public static final String TYPE_COMBINE_YMDHM = "yyyyMMddHHmm";
	/** MMddHHmmss */
	public static final String TYPE_COMBINE_MDHMS = "MMddHHmmss";
	/** MMddHHmm */
	public static final String TYPE_COMBINE_MDHM = "MMddHHmm";
	/** yyyyMMdd */
	public static final String TYPE_COMBINE_YMD = "yyyyMMdd";
	/** yyyyMM */
	public static final String TYPE_COMBINE_YM = "yyyyMM";
	/** MMdd */
	public static final String TYPE_COMBINE_MD = "MMdd";

	/** yyyy年MM月dd日HH时mm分ss秒 */
	public static final String TYPE_CN_YMDHMS = "yyyy年MM月dd日HH时mm分ss秒";
	/** yyyy年MM月dd日HH时mm分 */
	public static final String TYPE_CN_YMDHM = "yyyy年MM月dd日HH时mm分";
	/** MM月dd日HH时mm分ss秒 */
	public static final String TYPE_CN_MDHMS = "MM月dd日HH时mm分ss秒";
	/** MM月dd日HH时mm分 */
	public static final String TYPE_CN_MDHM = "MM月dd日HH时mm分";
	/** yyyy年MM月dd日 */
	public static final String TYPE_CN_YMD = "yyyy年MM月dd日";
	/** yyyy年MM月 */
	public static final String TYPE_CN_YM = "yyyy年MM月";
	/** MM月dd日 */
	public static final String TYPE_CN_MD = "MM月dd日";

	/** yyyy/MM/dd HH:mm:ss */
	public static final String TYPE_EN_YMDHMS = "yyyy/MM/dd HH:mm:ss";
	/** yyyy/MM/dd HH:mm */
	public static final String TYPE_EN_YMDHM = "yyyy/MM/dd HH:mm";
	/** MM/dd HH:mm:ss */
	public static final String TYPE_EN_MDHMS = "MM/dd HH:mm:ss";
	/** MM/dd HH:mm */
	public static final String TYPE_EN_MDHM = "MM/dd HH:mm";
	/** yyyy/MM/dd */
	public static final String TYPE_EN_YMD = "yyyy/MM/dd";
	/** yyyy/MM */
	public static final String TYPE_EN_YM = "yyyy/MM";
	/** MM/dd */
	public static final String TYPE_EN_MD = "MM/dd";

	public static final SimpleDateFormat SDF_YMDHMS = new SimpleDateFormat(TYPE_COM_YMDHMS);
	public static final SimpleDateFormat SDF_YMD = new SimpleDateFormat(TYPE_COM_YMD);
	public static final SimpleDateFormat SDF_YY = new SimpleDateFormat(TYPE_COM_YY);

	public static final SimpleDateFormat SDF_HMS = new SimpleDateFormat(TYPE_COM_HMS);
	public static final SimpleDateFormat SDF_COMBINE_YMDHMS = new SimpleDateFormat(TYPE_COMBINE_YMDHMS);

	public static String getYearStr(String dayStr){
		return dayStr.substring(0,4);
	}

	public static String getMonthStr(String dayStr){
		return dayStr.substring(5,7);
	}

	/** 私有化构造器 */
	private DateUtil() {
	}

	/**
	 * 获取日期转换器
	 *
	 * @return DateFormat
	 */
	public static DateFormat getDateFormat() {
		return getDateFormat(TYPE_COM_MDHMS);
	}

	/**
	 * 获取日期转换器
	 *
	 * DateUtil.getDateFormat(DateUtil.TYPE_COM_YMDHMS);
	 *
	 * @param fmt
	 * @return DateFormat
	 */
	public static DateFormat getDateFormat(String fmt) {
		return new SimpleDateFormat(fmt);
	}

	/**
	 * 与当前时间比较,1是未来，-1是过去,0是现在
	 *
	 * DateUtil.compareNow(DateUtil.parse("2019-11-10 12:09:09"))
	 *
	 * @param date1
	 * @return int
	 */
	public static int compareNow(Date date1) {
		Date date2 = new Date();
		int rnum = date1.compareTo(date2);
		return rnum;
	}

	/**
	 * 获取系统当前时间
	 *
	 * @return String
	 */
	public static String getNow() {
		return getNow(TYPE_COM_YMDHMS);
	}

	/**
	 * 获取系统当前时间 DateUtil.getNow(DateUtil.TYPE_CN_YMD)
	 *
	 * @return String
	 */
	public static String getNow(String fmt) {
		return format(new Date(), fmt);
	}

	/**
	 * 日期转换字符串，默认格式：yyyy-MM-dd HH:mm:ss
	 *
	 * DateUtil.parse("2019-11-10 12:09:09")
	 *
	 * @param dateStr
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parse(String dateStr) {
		return parse(dateStr, TYPE_COM_YMDHMS);
	}

	/**
	 * 日期转换字符串
	 *
	 * DateUtil.parse("2019年11月10日",DateUtil.TYPE_CN_YMD)
	 *
	 * @param dateStr
	 * @param fmt
	 * @return Date
	 */
	public static Date parse(String dateStr, String fmt) {
		Date d = null;
		if (dateStr != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(fmt);
				d = sdf.parse(dateStr);
			} catch (ParseException e) {
				log.error("解析日期格式" + fmt + "出错：" + e.getLocalizedMessage());
			}
		}
		return d;
	}

	/**
	 * 字符串转日期，默认格式：yyyy-MM-dd HH:mm:ss
	 *
	 * DateUtil.format(new Date())
	 *
	 * @param date
	 * @return String
	 */
	public static String format(Date date) {
		return format(date, TYPE_COM_YMDHMS);
	}

	/**
	 * 字符串转日期
	 *
	 * DateUtil.format(new Date(),DateUtil.TYPE_EN_YMDHMS)
	 *
	 * @param date
	 * @param fmt
	 * @return String
	 */
	public static String format(Date date, String fmt) {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.format(date);
	}

	/**
	 * 字符串转字符串,原字符串默认格式：yyyy-MM-dd HH:mm:ss
	 *
	 * DateUtil.formatByStr("2019-11-10 12:09:09", DateUtil.TYPE_CN_YMDHM)
	 *
	 * @param dateStr
	 * @param fmt
	 * @return String
	 */
	public static String formatByStr(String dateStr, String fmt) {
		return formatByStr(dateStr, TYPE_COM_YMDHMS, fmt);
	}

	/**
	 * 字符串转字符串
	 *
	 * DateUtil.formatByStr("2019-11-10 12:09:09",
	 * DateUtil.TYPE_COM_YMDHMS,DateUtil.TYPE_CN_YMDHM)
	 *
	 * @param dateStr
	 * @param oldFmt
	 * @param newFmt
	 * @return String
	 */
	public static String formatByStr(String dateStr, String oldFmt,
									 String newFmt) {
		return format(parse(dateStr, oldFmt), newFmt);
	}

	/**
	 * 返回日期加X天后的日期
	 *
	 * DateUtil.addDay("2019-11-10 12:09:09", -21)
	 *
	 * @param dateStr
	 * @param i
	 *            正数是后面日期，负数是前面日期
	 * @return String
	 */
	public static String addDay(String dateStr, int i) {
		return dateCalculate(dateStr, Calendar.DATE, i, TYPE_COM_YMDHMS,
				TYPE_COM_YMDHMS);
	}

	/**
	 * 返回日期加X天后的日期
	 *
	 * DateUtil.addDay("2019-11-10 12:09:09", -21,DateUtil.TYPE_CN_YMDHMS)
	 *
	 * @param dateStr
	 * @param newFmt
	 *            字符串需要被转换的格式
	 * @param i
	 *            正数是后面日期，负数是前面日期
	 * @return String
	 */
	public static String addDay(String dateStr, int i, String newFmt) {
		return dateCalculate(dateStr, Calendar.DATE, i, TYPE_COM_YMDHMS, newFmt);
	}

	/**
	 * 返回日期加X天后的日期
	 *
	 * DateUtil.addDay("2019-11-10 12:09:09",
	 * -21,DateUtil.TYPE_COM_YMDHMS,DateUtil.TYPE_CN_YMD)
	 *
	 * @param dateStr
	 * @param oldFmt
	 * @param newFmt
	 * @param i
	 *            正数是后面日期，负数是前面日期
	 * @return String
	 */
	public static String addDay(String dateStr, int i, String oldFmt,
								String newFmt) {
		return dateCalculate(dateStr, Calendar.DATE, i, oldFmt, newFmt);
	}

	/**
	 * 返回日期加X月后的日期
	 *
	 * DateUtil.addMonth("2019-11-10 12:09:09", -36)
	 *
	 * @param dateStr
	 * @param i
	 *            正数是后面月数，负数是前面月数
	 * @return String
	 */
	public static String addMonth(String dateStr, int i) {
		return dateCalculate(dateStr, Calendar.MONTH, i, TYPE_COM_YMDHMS,
				TYPE_COM_YMDHMS);
	}

	/**
	 * 返回日期加X月后的日期
	 *
	 * DateUtil.addMonth("2019-11-10 12:09:09", -36,DateUtil.TYPE_CN_YMDHMS)
	 *
	 * @param dateStr
	 * @param newFmt
	 * @param i
	 *            正数是后面月数，负数是前面月数
	 * @return String
	 */
	public static String addMonth(String dateStr, int i, String newFmt) {
		return dateCalculate(dateStr, Calendar.MONTH, i, TYPE_COM_YMDHMS,
				newFmt);
	}

	/**
	 * 返回日期加X月后的日期
	 *
	 * DateUtil.addMonth("2019-11-10 12:09:09",
	 * -36,DateUtil.TYPE_COM_YMDHMS,DateUtil.TYPE_CN_YMDHMS)
	 *
	 * @param dateStr
	 * @param oldFmt
	 * @param newFmt
	 * @param i
	 *            正数是后面月数，负数是前面月数
	 * @return String
	 */
	public static String addMonth(String dateStr, int i, String oldFmt,
								  String newFmt) {
		return dateCalculate(dateStr, Calendar.MONTH, i, oldFmt, newFmt);
	}

	/**
	 * 返回日期加X年后的日期
	 *
	 * DateUtil.addYear("2019-11-10 12:09:09", -36)
	 *
	 * @param dateStr
	 * @param i
	 *            正数是后面年数，负数是前面年数
	 * @return String
	 */
	public static String addYear(String dateStr, int i) {
		return dateCalculate(dateStr, Calendar.YEAR, i, TYPE_COM_YMDHMS,
				TYPE_COM_YMDHMS);
	}

	/**
	 * 返回日期加X年后的日期
	 *
	 * DateUtil.addYear("2019-11-10 12:09:09", -36,DateUtil.TYPE_CN_YMDHMS)
	 *
	 * @param dateStr
	 * @param newFmt
	 * @param i
	 *            正数是后面年数，负数是前面年数
	 * @return String
	 */
	public static String addYear(String dateStr, int i, String newFmt) {
		return dateCalculate(dateStr, Calendar.YEAR, i, TYPE_COM_YMDHMS, newFmt);
	}

	/**
	 * 返回日期加X年后的日期
	 *
	 * DateUtil.addYear("2019-11-10 12:09:09",
	 * -36,DateUtil.TYPE_COM_YMDHMS,DateUtil.TYPE_CN_YMDHMS)
	 *
	 * @param dateStr
	 * @param oldFmt
	 * @param newFmt
	 * @param i
	 *            正数是后面年数，负数是前面年数
	 * @return String
	 */
	public static String addYear(String dateStr, int i, String oldFmt,
								 String newFmt) {
		return dateCalculate(dateStr, Calendar.YEAR, i, oldFmt, newFmt);
	}

	/**
	 * 日期计算 DateUtil.dateCalculate("2019-11-10 12:09:09", Calendar.DATE, 1)
	 *
	 * @param dateStr
	 * @param field
	 *            Calender的计算域
	 *            YEAR,MONTH,DATE,HOUR(12小时),HOUR_OF_DAY(24小时),MINUTE,SECOND
	 * @param i
	 * @return String
	 */
	public static String dateCalculate(String dateStr, int field, int i) {
		return dateCalculate(dateStr, field, i, TYPE_COM_YMDHMS,
				TYPE_COM_YMDHMS);
	}

	/**
	 * 日期计算
	 *
	 * DateUtil.dateCalculate("2019-11-10 12:09:09", Calendar.DATE,
	 * 1,DateUtil.TYPE_CN_YMDHMS)
	 *
	 * @param dateStr
	 * @param newFmt
	 * @param field
	 *            Calender的计算域
	 *            YEAR,MONTH,DATE,HOUR(12小时),HOUR_OF_DAY(24小时),MINUTE,SECOND
	 * @param i
	 * @return String
	 */
	public static String dateCalculate(String dateStr, int field, int i,
									   String newFmt) {
		return dateCalculate(dateStr, field, i, TYPE_COM_YMDHMS, newFmt);
	}

	/**
	 * 日期计算
	 *
	 * DateUtil.dateCalculate("2019-11-10 12:09:09", Calendar.DATE,
	 * 1,DateUtil.TYPE_COM_YMDHMS,DateUtil.TYPE_CN_YMDHMS)
	 *
	 * @param dateStr
	 * @param oldFmt
	 * @param newFmt
	 * @param field
	 *            Calender的计算域
	 *            YEAR,MONTH,DATE,HOUR(12小时),HOUR_OF_DAY(24小时),MINUTE,SECOND
	 * @param i
	 * @return String
	 */
	public static String dateCalculate(String dateStr, int field, int i,
									   String oldFmt, String newFmt) {
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(parse(dateStr, oldFmt));
			calendar.add(field, i);
			return format(calendar.getTime(), newFmt);
		} catch (Exception e) {
			log.error("DateUtil.dateCalculate() error:"
					+ e.getLocalizedMessage());
		}
		return null;
	}

	/**
	 * 获取当前年份 DateUtil.currentYear()
	 *
	 * @return int
	 */
	public static int currentYear() {
		return current(Calendar.YEAR);
	}

	/**
	 * 获取当前月份 DateUtil.currentMonth()
	 *
	 * @return int
	 */
	public static int currentMonth() {
		return current(Calendar.MONTH) + 1;
	}

	/**
	 * 获取当前日份 DateUtil.currentDate()
	 *
	 * @return int
	 */
	public static int currentDate() {
		return current(Calendar.DATE);
	}

	/**
	 * 获取当前小时数 DateUtil.currentHour()
	 *
	 * @return int
	 */
	public static int currentHour() {
		return current(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取当前分钟数 DateUtil.currentMinute()
	 *
	 * @return int
	 */
	public static int currentMinute() {
		return current(Calendar.MINUTE);
	}

	/**
	 * 获取当前秒数 DateUtil.currentSecond()
	 *
	 * @return int
	 */
	public static int currentSecond() {
		return current(Calendar.SECOND);
	}

	/**
	 * 获取当前某一域的值 DateUtil.current(Calendar.SECOND)
	 *
	 * @param field
	 *            Calender的计算域 YEAR,MONTH,DATE,HOUR_OF_DAY(24小时),MINUTE,SECOND
	 * @return int
	 */
	public static int current(int field) {
		Calendar calendar = new GregorianCalendar();
		return calendar.get(field);
	}

	/**
	 * 获取当前月份第一天
	 *
	 * @return String
	 */
	public static String getFirstDateOfMonth() {
		return getFirstDateOfMonth(new Date());
	}

	/**
	 * 获取月份第一天
	 *
	 * @param date
	 * @return String
	 */
	public static String getFirstDateOfMonth(Date date) {
		return getFirstDateOfMonth(date, TYPE_COM_YMDHMS);
	}

	/**
	 * 获取当前月份第一天
	 *
	 * @param dateStr
	 * @return String
	 */
	public static String getFirstDateOfMonth(String dateStr) {
		return getFirstDateOfMonth(parse(dateStr), TYPE_COM_YMDHMS);
	}

	/**
	 * 获取月份第一天
	 *
	 * @param dateStr
	 * @param newFmt
	 * @return String
	 */
	public static String getFirstDateOfMonth(String dateStr, String newFmt) {
		return getFirstDateOfMonth(parse(dateStr), newFmt);
	}

	/**
	 * 获取月份第一天
	 *
	 * @param dateStr
	 * @param oldFmt
	 * @param newFmt
	 * @return String
	 */
	public static String getFirstDateOfMonth(String dateStr, String oldFmt,
											 String newFmt) {
		return getFirstDateOfMonth(parse(dateStr, oldFmt), newFmt);
	}

	/**
	 * 获取月份第一天
	 *
	 * @param date
	 * @param newFmt
	 * @return String
	 */
	public static String getFirstDateOfMonth(Date date, String newFmt) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, 1);
		return format(calendar.getTime(), newFmt);
	}

	/**
	 * 获取当前月份最后一天
	 *
	 * @return String
	 */
	public static String getLastDateOfMonth() {
		return getLastDateOfMonth(new Date());
	}

	/**
	 * 获取月份最后一天
	 *
	 * @param dateStr
	 * @return String
	 */
	public static String getLastDateOfMonth(String dateStr) {
		return getLastDateOfMonth(parse(dateStr), TYPE_COM_YMDHMS);
	}

	/**
	 * 获取月份最后一天
	 *
	 * @param date
	 * @return String
	 */
	public static String getLastDateOfMonth(Date date) {
		return getLastDateOfMonth(date, TYPE_COM_YMDHMS);
	}

	/**
	 * 获取月份最后一天
	 *
	 * @param dateStr
	 * @param newFmt
	 * @return String
	 */
	public static String getLastDateOfMonth(String dateStr, String newFmt) {
		return getLastDateOfMonth(parse(dateStr), newFmt);
	}

	/**
	 * 获取月份最后一天
	 *
	 * @param dateStr
	 * @param oldFmt
	 * @param newFmt
	 * @return String
	 */
	public static String getLastDateOfMonth(String dateStr, String oldFmt,
											String newFmt) {
		return getLastDateOfMonth(parse(dateStr, oldFmt), newFmt);
	}

	/**
	 * 获取月份最后一天
	 *
	 * @param date
	 * @param newFmt
	 * @return String
	 */
	public static String getLastDateOfMonth(Date date, String newFmt) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return format(calendar.getTime(), newFmt);
	}
	//获取上个月的第一天
	public static String getLastMonthFirstDay(String newFmt){
		SimpleDateFormat format = new SimpleDateFormat(newFmt);
		Calendar   cal_1=Calendar.getInstance();//获取当前日期
		cal_1.add(Calendar.MONTH, -1);
		cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
		return format.format(cal_1.getTime());
	}
	//获取上个月的最后一天
	public static String getLastMonthLastDay(String newFmt){
		SimpleDateFormat format = new SimpleDateFormat(newFmt);
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天
		return format.format(cale.getTime());
	}

	//获取上个月的天数 preNum 为0时则为本月  1为上个月 2为推两个月 一次类推
	public static Integer getMonthDays(int preNum){
		Calendar   cal_1=Calendar.getInstance();//获取当前日期
		cal_1.add(Calendar.MONTH, -preNum);
		return cal_1.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	/**
	 * 获取日期的星期(星期日,一，二，三，四，五，六)
	 *
	 * @return String
	 */
	public static String getWeek() {
		return getWeek(new Date());
	}

	/**
	 * 获取日期的星期(星期日,一，二，三，四，五，六)
	 *
	 * @param dateStr
	 *            默认格式YMDHMS
	 * @return String
	 */
	public static String getWeek(String dateStr) {
		return getWeek(parse(dateStr, TYPE_COM_YMDHMS));
	}

	/**
	 * 获取日期的星期(星期日,一，二，三，四，五，六)
	 *
	 * @param dateStr
	 * @param fmt
	 * @return String
	 */
	public static String getWeek(String dateStr, String fmt) {
		return getWeek(parse(dateStr, fmt));
	}

	/**
	 * 获取日期的星期(星期日,一，二，三，四，五，六)
	 *
	 * @param date
	 * @return String
	 */
	public static String getWeek(Date date) {
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			String[] weeks = new String[] { "日", "一", "二", "三", "四", "五", "六" };
			return "星期" + weeks[weekNumber];
		} else {
			return null;
		}

	}

	/**
	 * 验证字符串是否可以转换为日期，格式需符合本类中常用格式：COMMON,COMBINE,CN,EN
	 *
	 * @param dateStr
	 * @return String
	 */
	public static boolean isDate(String dateStr) {
		String[] fmts = new String[] { TYPE_COM_YMDHMS, TYPE_COM_YMDHM,
				TYPE_COM_YMD, TYPE_COM_YM, TYPE_COM_MDHMS, TYPE_COM_MDHM,
				TYPE_COM_MD, TYPE_COM_HMS, TYPE_COM_HM, TYPE_COMBINE_YMDHMS,
				TYPE_COMBINE_YMDHM, TYPE_COMBINE_YMD, TYPE_COMBINE_YM,
				TYPE_COMBINE_MDHMS, TYPE_COMBINE_MDHM, TYPE_COMBINE_MD,
				TYPE_CN_YMDHMS, TYPE_CN_YMDHM, TYPE_CN_YMD, TYPE_CN_YM,
				TYPE_CN_MDHMS, TYPE_CN_MDHM, TYPE_CN_MD, TYPE_EN_YMDHMS,
				TYPE_EN_YMDHM, TYPE_EN_YMD, TYPE_EN_YM, TYPE_EN_MDHMS,
				TYPE_EN_MDHM, TYPE_EN_MD };
		Date d = null;
		for (String fmt : fmts) {
			d = parse(dateStr, fmt);
			if (d != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 计算日期差值
	 *
	 * @param dateStr1
	 * @param dateStr2
	 * @param field
	 *            Calender的计算域 DATE,HOUR,MINUTE,SECOND
	 * @return long
	 */
	public static Long subtract(String dateStr1, String dateStr2, int field) {
		return subtract(parse(dateStr1), parse(dateStr2), field);
	}

	/**
	 * 计算日期差值
	 *
	 * @param dateStr1
	 * @param dateStr2
	 * @param fmt
	 * @param field
	 *            Calender的计算域 DATE,HOUR,MINUTE,SECOND
	 * @return long
	 */
	public static Long subtract(String dateStr1, String dateStr2, String fmt,
								int field) {
		return subtract(parse(dateStr1, fmt), parse(dateStr2, fmt), field);
	}

	/**
	 * 计算日期差值
	 *
	 * @param date1
	 * @param date2
	 * @param field
	 * @return long
	 */
	public static Long subtract(Date date1, Date date2, int field) {
		Long differ = date1.getTime() - date2.getTime();
		if (field == Calendar.SECOND) {
			return differ / (1000);
		}
		if (field == Calendar.MINUTE) {
			return differ / (60 * 1000);
		}
		if (field == Calendar.HOUR) {
			return differ / (60 * 60 * 1000);
		}
		if (field == Calendar.DATE) {
			return differ / (24 * 60 * 60 * 1000);
		}
		if (field == Calendar.MONTH) {
			return differ / (30 * 24 * 60 * 60 * 1000);
		}
		return 0L;
	}

	/**
	 * 获取本周的星期一
	 */
	public static Date getWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		return cal.getTime();
	}

	/**
	 * 获取年1月1号
	 */
	public static Date getYearFirstDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获取去年年份
	 */
	public static String getPreYear() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.YEAR, -1);
		return SDF_YY.format(cal.getTime());
	}


	/**
	 * 获取去年年第一天日期
	 * @return Date
	 */
	public static Date getLastYearFirst(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -1);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获取去年年第一天日期
	 * @return Date
	 */
	public static Date getLastYearLast(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return cal.getTime();
	}
	/**
	 * 获取指定时分秒
	 */
	public static Date getNeedTime(Date date,int hour,int minute,int second,int addDay,int ...args){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if(addDay != 0){
			calendar.add(Calendar.DATE,addDay);
		}
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE,minute);
		calendar.set(Calendar.SECOND,second);
		if(args.length==1){
			calendar.set(Calendar.MILLISECOND,args[0]);
		}
		return calendar.getTime();
	}

	/**
	 * 根据日期偏移天数
	 * @param date
	 * @param i 正数加,负数减
	 * @return
	 */
	public static String getOffsetDayToString(Date date,Integer i,String newFmt) {
		SimpleDateFormat sdf = new SimpleDateFormat(newFmt);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, i);
		date = calendar.getTime();
		return sdf.format(date);
	}
	/**
	 * 两个时间相差距离多少天多少小时多少分多少秒
	 * @param str1 时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2 时间参数 2 格式：2009-01-01 12:00:00
	 * @return String 返回值为：xx天xx小时xx分xx秒
	 */
	public static String getDistanceTime(String str1, String str2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff ;
			if(time1<time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day + "天" + hour + "小时" + min + "分" + sec + "秒";
	}

	public static  void fenye(List list,int pagesize){
		int totalCount = list.size();//总条数
		int maxPageNo = 0;//最大页码
		int mMun = totalCount%pagesize;//取模余数
		if  (mMun>0){
			maxPageNo = totalCount/pagesize+1;
		}else{
			maxPageNo = totalCount/pagesize;
		}
		for(int pageNo=1;pageNo<=maxPageNo;pageNo++){
			if (mMun==0){
				List<Object> subList= list.subList((pageNo-1)*pagesize,pagesize*(pageNo));
				System.out.println(subList);
			}else{
				if (pageNo==maxPageNo){
					List<Object> subList= list.subList((pageNo-1)*pagesize,totalCount);
					System.out.println(subList);
				}else{
					List<Object> subList= list.subList((pageNo-1)*pagesize,pagesize*(pageNo));
					System.out.println(subList);
				}
			}
		}
	}

	public static void main(String[] args) {

		System.out.println(getNow(TYPE_COM_HH));
//		List<Integer> stringA = Arrays.asList(7270194,7272163,7273946,7270194,7272163,7273946,
//				7270154,7271301,7272774,7273534,7270154,7271301,7272774,7273534,
//				7270124,7271102,7272478,7273218,7273353,7270124,7271102,7272478,7273218,7273353,
//				7267290,7270188,7271054,7272407,7267290,7270188,7271054,7272407,7274775,
//				7270050,7272414,7270050,7272414,7274495,
//				7263331,7269683,7271500,7273347,7273756,7263331,7269683,7271500,7273347,7273756,
//				7265129,7266694,7269827,7271999,7274473,7265129,7266694,7269827,7271999,7274473,7275551,7276258,
//				7263319,7269434,7270530,7272388,7275022,7263319,7269434,7270530,7272388,7275022,
//				7263399,7266867,7270248,7271456,7273561,7263399,7266867,7270248,7271456,7273561,
//				7265868,7269699,7270891,7271777,7273656,7265868,7269699,7270891,7271777,7273656,
//				7270204,7270703,7272662,7270204,7270703,7272662,7274943,
//				7262951,7269401,7270215,7271372,7272892,7262951,7269401,7270215,7271372,7272892,
//				7270416,7272111,7270416,7272111,7275085,
//				7270206,7271498,7273783,7270206,7271498,7273783,7275185,
//				7270234,7272497,7270234,7272497,
//				7263316,7269495,7270557,7271514,7273733,7263316,7269495,7270557,7271514,7273733,
//				7270548,7272453,7273557,7270548,7272453,7273557,
//				7266640,7266942,7269464,7270364,7271079,7273062,7275307,7266640,7266942,7269464,7270364,7271079,7273062,7275307,
//				7265198,7268389,7269515,7270302,7270576,7273091,7275109,7265198,7268389,7269515,7270302,7270576,7273091,7275109,7275248,
//				7270274,7270615,7272141,7274605,7270274,7270615,7272141,7274605,
//				7266055,7269412,7270547,7270921,7272581,7274013,7266055,7269412,7270547,7270921,7272581,7274013,7275817,
//				7270527,7272708,7270527,7272708,
//				7264987,7269614,7270895,7271658,7273744,7264987,7269614,7270895,7271658,7273744,
//				7266332,7267405,7269282,7269310,7269984,7270629,7272820,7266332,7267405,7269282,7269310,7269984,7270629,7272820,7274735,
//				7269832,7271463,7269832,7271463,7273464,
//				7269899,7271693,7273718,7269899,7271693,7273718,7275247,
//				7266805,7269961,7270319,7271717,7273199,7266805,7269961,7270319,7271717,7273199,7275286,
//				7270053,7271838,7273029,7274167,7270053,7271838,7273029,7274167,7275629,
//				7266352,7268349,7270043,7271337,7274033,7266352,7268349,7270043,7271337,7274033,7275140,
//				7270111,7272308,7270111,7272308,7274675);
//		HashMap m = new HashMap();
//		stringA.forEach(i->{
//			if(m.containsKey(i)){
//				int num = (int) m.get(i)+1;
//				m.put(i,num);
//			}else{
//				m.put(i,1);
//			}
//		});


//		System.out.println(stringA.size());
//		Set<Integer> staffsSet = new HashSet<>(stringA);
//
//		System.out.println(staffsSet.size());








//		List<Integer> l = new ArrayList<Integer>();
//		for(int i=0;i<0;i++){
//			l.add(i);
//		}
//		int pageSize = 5;
//		fenye(l,pageSize);


		String currentDayStr = "2019-09-10 12:12:45";
		String yesterdayStr = DateUtil.addDay(currentDayStr, -1, DateUtil.TYPE_COM_YMD);
		System.out.println("=======1111==-1==="+yesterdayStr);

		yesterdayStr = DateUtil.addDay(currentDayStr, 1, DateUtil.TYPE_COM_YMD);
		System.out.println("======2222===1==="+yesterdayStr);

		yesterdayStr = DateUtil.addDay(currentDayStr, 0, DateUtil.TYPE_COM_YMD);
		System.out.println("=====3333===0===="+yesterdayStr);

//		print(DateUtil.compareNow(DateUtil.parse("2019-11-10 12:09:09")));
//		print(DateUtil.getNow(DateUtil.TYPE_CN_YMD));
//		print(DateUtil.parse("2019-11-10 12:09:09"));
//		print(DateUtil.parse("2019年11月10日", DateUtil.TYPE_CN_YMD));
//		print(DateUtil.format(new Date()));
//		print(DateUtil.format(new Date(), DateUtil.TYPE_EN_YMDHMS));
//		print(DateUtil.formatByStr("2019-11-10 12:09:09",
//				DateUtil.TYPE_CN_YMDHM));
//		print(DateUtil.formatByStr("2019-11-10 12:09:09",
//				DateUtil.TYPE_COM_YMDHMS, DateUtil.TYPE_CN_YMDHM));
//		print(DateUtil.addDay("2019-11-10 12:09:09", -21));
//		print(DateUtil.addDay("2019-11-10 12:09:09", -21,
//				DateUtil.TYPE_CN_YMDHMS));
//		print(DateUtil.addDay("2019-11-10 12:09:09", -21,
//				DateUtil.TYPE_COM_YMDHMS, DateUtil.TYPE_CN_YMD));
//		print(DateUtil.addMonth("2019-11-10 12:09:09", -36));
//		print(DateUtil.addMonth("2019-11-10 12:09:09", -36,
//				DateUtil.TYPE_CN_YMDHMS));
//		print(DateUtil.addMonth("2019-11-10 12:09:09", -36,
//				DateUtil.TYPE_COM_YMDHMS, DateUtil.TYPE_CN_YMDHMS));
//		print(DateUtil.addYear("2019-11-10 12:09:09", -36));
//		print(DateUtil.addYear("2019-11-10 12:09:09", -36,
//				DateUtil.TYPE_CN_YMDHMS));
//		print(DateUtil.addYear("2019-11-10 12:09:09", -36,
//				DateUtil.TYPE_COM_YMDHMS, DateUtil.TYPE_CN_YMDHMS));
//		print(DateUtil.dateCalculate("2019-11-10 12:09:09", Calendar.DATE, 1));
//		print(DateUtil.dateCalculate("2019-11-10 12:09:09", Calendar.DATE, 1,
//				DateUtil.TYPE_CN_YMDHMS));
//		print(DateUtil.dateCalculate("2019-11-10 12:09:09", Calendar.DATE, 1,
//				DateUtil.TYPE_COM_YMDHMS, DateUtil.TYPE_CN_YMDHMS));
//		print(DateUtil.currentYear());
//		print(DateUtil.currentMonth());
//		print(DateUtil.currentDate());
//		print(DateUtil.currentHour());
//		print(DateUtil.currentMinute());
//		print(DateUtil.currentSecond());
//		print(DateUtil.current(Calendar.SECOND));
//		print(DateUtil.getWeek());
//		print(DateUtil.getWeek("2019-10-10 12:09:09"));
//		print(DateUtil.getWeek("2011年10月10日12时09分09秒", DateUtil.TYPE_CN_YMDHMS));
//		print(DateUtil.getWeek(DateUtil.parse("2019-11-10 12:09:09")));
//		print(DateUtil.isDate("2011年10月10日12时09分09秒"));
//		print(DateUtil.isDate("2011年10月10日"));
//		print(DateUtil.isDate("12:09:09"));
	}


	/**
	 * 将日期为字符串格式转换成毫秒数
	 * @param dateStr
	 * @return
	 */
	public static long dateStrConvertDateTime(String dateStr,String fmt){
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		try {
			return sdf.parse(dateStr).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void print(Object obj) {
		System.out.println(obj);
	}

	/**
	 * @auth libg 2019.2.27
	 * @desc 根据开始时间和结束时间获取中间的所有月分 格式为：2019-02
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static List<PagingParam> getPagingParamList(String startTime, String endTime) {

		List<PagingParam> pagingParamList = new ArrayList<>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		try {
			Date start = dateFormat.parse(startTime);
			Date end = dateFormat.parse(endTime);

			Calendar tempStart = Calendar.getInstance();
			tempStart.setTime(start);

			Calendar tempEnd = Calendar.getInstance();
			tempEnd.setTime(end);
			tempEnd.add(Calendar.MONTH, +1);
			int index = 0;
			while (tempStart.before(tempEnd)) {
				String yearMonth = dateFormat.format(tempStart.getTime());
				PagingParam pagingParam = new PagingParam();
				pagingParam.setDateStr(yearMonth);
				pagingParam.setIndex(index);
				getQueryTime(pagingParam,yearMonth,startTime,endTime);
//                months.add(dateFormat.format(tempStart.getTime()));
				tempStart.add(Calendar.MONTH, 1);
				index++;
				pagingParamList.add(pagingParam);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagingParamList;
	}

	public static void getQueryTime(PagingParam pagingParam,String yearMonth,String beginChargeTime,String endChargeTime){
		String startQueryTime = "";
		String endQueryTime = "";
		if(!yearMonth.equals(beginChargeTime.substring(0,7))){//取当月第一天
			startQueryTime = DateUtil.getFirstDayOfMonthFromYYYYMM(yearMonth);
		}else{
			startQueryTime = beginChargeTime;
		}
		if(!yearMonth.equals(endChargeTime.substring(0,7))){
			endQueryTime = DateUtil.getLastDayOfMonthFromYYYYMM(yearMonth);
		}else{
			endQueryTime = endChargeTime;
		}
		pagingParam.setStartQueryTime(startQueryTime);
		pagingParam.setEndQueryTime(endQueryTime);
	}

	/**
	 * @auth libg 2019.03.01
	 * @desc 根据年月获取当月的第一天
	 * @param dateStr
	 * @return
	 */
	public static String getFirstDayOfMonthFromYYYYMM(String dateStr){
		return getLastOrFirstDayOfMonthFromYYYYMM(dateStr,1);
	}


	/**
	 * @auth libg 2019.03.01
	 * @desc 根据年月获取当月的最后一天
	 * @param dateStr
	 * @return
	 */
	public static String getLastDayOfMonthFromYYYYMM(String dateStr){
		return getLastOrFirstDayOfMonthFromYYYYMM(dateStr,0);
	}

	/**
	 * @auth libg 2019.03.01
	 * @desc 根据年月获取当月的第一天或者最后一天
	 * @param dateStr
	 * @param flag
	 * @return
	 */
	private static String getLastOrFirstDayOfMonthFromYYYYMM(String dateStr,int flag){
		String fmt = DateUtil.TYPE_COM_YM;
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(DateUtil.parse(dateStr,fmt));
		if(flag == 1){//第一天
			calendar.set(Calendar.DATE, 1);
		}else{//最后一天
			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		}
		return DateUtil.format(calendar.getTime(), DateUtil.TYPE_COM_YMD);
	}
}