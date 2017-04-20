package com.polaris.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理相关的工具方法
 * @author John
 *
 */
public final class DateUtil {

	/**
	 * date 2 string
	 * @param date
	 * @param dateFormatPattern
	 * @return
	 */
	public static String date2str(Date date, String dateFormatPattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormatPattern);
		return sdf.format(date);
	}
	
	/**
	 * 计算此刻到今天的23:59:59.999还剩多少个单位值(时间单位由调用方指定);
	 */
	public static Long getTodayLastTime() {
		// TODO
		return null;
	}
	
	
}
