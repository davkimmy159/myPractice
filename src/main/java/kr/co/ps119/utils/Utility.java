package kr.co.ps119.utils;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import kr.co.ps119.entity.Board;

public class Utility {
	
	private static final ZoneId ZONE_ID_SEOUL = ZoneId.of("Asia/Seoul");
	
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
	
	private Utility() {
	}

	public static ZoneId getZoneIdSeoul() {
		return ZONE_ID_SEOUL;
	}

	public static DateTimeFormatter getDateTimeFormatter() {
		return DATE_TIME_FORMATTER;
	}
}