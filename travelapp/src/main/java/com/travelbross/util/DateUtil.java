package com.travelbross.util;


import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import com.travelbross.config.PropertyConfiguration;

public class DateUtil {
	private static String timezonePath = PropertyConfiguration.getPropertyValue("resourcePath") + "config/timezones.txt";
	public static String identifyUserTimezone(String timezoneOffset,
			String timezoneName) {
		String timezone = "UTC";
		JSONArray timezones = new JSONArray(FileUtil.getFileContent(timezonePath));
		int totalTimezone = timezones.length();
		if(totalTimezone > 0) {
			boolean isTimezoneFound = false;
			for(int i=0; i<totalTimezone; i++) {
				JSONObject timezoneObj = timezones.getJSONObject(i);
				if(timezoneObj.getString("code").equals(timezoneName)) {
					timezone = timezoneObj.getString("code");
					isTimezoneFound = true;
					break;
				}
			}
			if(!isTimezoneFound) {
				for(int j=0; j<totalTimezone; j++) {
					JSONObject timezoneObj = timezones.getJSONObject(j);
					if(timezoneObj.getString("offset").equals(timezoneOffset)) {
						timezone = timezoneObj.getString("code");
						break;
					}
				}
			}
		}
		return timezone;
	}
	public static long getCurrentUTCTime() {
		LocalDateTime date = LocalDateTime.now();
		DateTimeZone tz = DateTimeZone.getDefault();
		long currentTime = (date.toDateTime(tz).getMillis()) / 1000;
		return currentTime;
	}

}
