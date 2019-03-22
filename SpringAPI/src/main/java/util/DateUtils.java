package main.java.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	/**
	 * Convert data string to date
	 * @param input String
	 * @return date Date
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String input) {
		try {
			return sdf.parse(input);
		} catch (Exception e) {
			// Check format first, this case accept
		}
		return null;
	}
}
