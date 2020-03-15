package com.boe.admin.uiadmin.utils;



import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;


/**
	Instant         时间戳
	Duration        持续时间、时间差
	LocalDate       只包含日期，比如：2018-09-24
	LocalTime       只包含时间，比如：10:32:10
	LocalDateTime   包含日期和时间，比如：2018-09-24 10:32:10
	Peroid          时间段
	ZoneOffset      时区偏移量，比如：+8:00
	ZonedDateTime   带时区的日期时间
	Clock           时钟，可用于获取当前时间戳
	java.time.format.DateTimeFormatter      时间格式化类
 */

public class DateUtil {
	
	private static final String COMMON_LOCAL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(COMMON_LOCAL_DATE_FORMAT);
	
	//date转字符串
	public static String date2String(Date date, String format) {
		if(null == date) {
			return null;
		}
		if(StringUtils.isNotBlank(format)) {
			return DateTimeFormatter.ofPattern(format).format(date.toInstant().atZone(ZoneId.of("Asia/Shanghai")).toLocalDateTime());
		}
		return DATE_FORMATTER.format(date.toInstant().atZone(ZoneId.of("Asia/Shanghai")).toLocalDateTime());
	}
	//字符串转date
	public static Date str2Date(String date, String format) {
		if(StringUtils.isBlank(date)) {
			return null;
		}
		LocalDateTime localDateTime = LocalDateTime.parse(date, DATE_FORMATTER);
		if(StringUtils.isNotBlank(format)) {
			localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(format));
		}
		ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }
	
	
	//字符串转date
	public static LocalDateTime str2LocalDateTime(String date, String format) {
		if(StringUtils.isBlank(date)) {
			return null;
		}
		return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(format == null? COMMON_LOCAL_DATE_FORMAT: format));
		
    }
	
	
	//获取当前时间
	public static LocalDateTime now() {
		//LocalDate localDate = LocalDate.now();
		//LocalTime localTime = LocalTime.now();
		return LocalDateTime.now();
	}
	
	//获取当前毫秒数
	public static Long currentTimeMills() {
		//long epochMilli = Instant.now().toEpochMilli();
		return System.currentTimeMillis();
	}
	

}