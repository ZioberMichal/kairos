package com.kairos.core.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateUtils {

	private final static String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";

	private final static String DATE_FORMAT = "dd-MM-yyyy";

	public static LocalDateTime getDateTime(String dateTime) {
		if (StringUtils.hasText(dateTime)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
			return LocalDateTime.parse(dateTime, formatter);
		}
		return LocalDateTime.now();
	}

	public static LocalDate getDate(String date) {
		if (StringUtils.hasText(date)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
			return LocalDate.parse(date, formatter);
		}
		return LocalDate.now();
	}

	public static LocalDateTime getDateTimeFrom(String dateFrom) {
		LocalDateTime from = LocalDateTime.MIN;
		if (StringUtils.hasText(dateFrom)) {
			from = setToEarlyTime(DateUtils.getDate(dateFrom));
		}
		return from;
	}

	public static LocalDateTime setToEarlyTime(LocalDate date) {
		return date.atTime(0, 0, 0);
	}
}
