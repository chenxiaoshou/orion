package common;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Date;

/**
 * 试用jdk8的新的日期API
 * 
 * @author John
 *
 */
public class NewDateInJdk8 {

	public static void main(String[] args) {
		localDate();
		sleep(1);
		localTime();
		sleep(1);
		zonedDateTime();
	}

	private static void zonedDateTime() {
		ZonedDateTime zonedDateTime = ZonedDateTime.now();
		System.out.println(zonedDateTime);
		System.out.println(new Date().getTime());
		System.out.println(zonedDateTime.getLong(ChronoField.INSTANT_SECONDS));
	}

	private static void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void localDate() {
		LocalDate localDate1 = LocalDate.now();
		System.out.println(localDate1);
		// 按照时区获取日期
		LocalDate localDate2 = LocalDate.now(ZoneId.of(ZoneId.SHORT_IDS.get("AST")));
		System.out.println(localDate2);
		// 获取从1970/1/1基准天数 + 363
		LocalDate dateFromBase = LocalDate.ofEpochDay(363);
		System.out.println("365th day from base date= " + dateFromBase);
	}

	public static void localTime() {
		LocalTime localTime = LocalTime.now();
		System.err.println(localTime);
		LocalTime localTime1 = LocalTime.now(ZoneId.ofOffset("GMT", ZoneOffset.UTC));
		System.err.println(localTime1);
		LocalTime localTime2 = LocalTime.now(ZoneId.ofOffset("UT", ZoneOffset.MIN));
		System.err.println(localTime2);
		LocalTime localTime3 = LocalTime.now(ZoneId.ofOffset("UTC", ZoneOffset.MAX));
		System.err.println(localTime3);
	}

}
