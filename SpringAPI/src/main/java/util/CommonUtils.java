package main.java.util;

import org.springframework.util.StringUtils;

public class CommonUtils {
	/**
	 * Convert status to String Planing, Doing , Completed
	 * @param status 1, 2, 3
	 * @return
	 */
	public static String convertStatusToString(int status) {
		switch (status) {
		case 1:
			return "Planing";
		case 2:
			return "Doing";
		case 3:
			return "Completed";
		default:
			return null;
		}
	}

	/**
	 * Convert status to int 1, 2 , 3
	 * @param status Planing, Doing , Completed
	 * @return
	 */
	public static int convertStatusToInt(String status) {
		if (!StringUtils.isEmpty(status)) {
			status = status.toLowerCase();
		}
		switch (status) {
		case "planing":
			return 1;
		case "doing":
			return 2;
		case "completed":
			return 3;
		default:
			return 0;
		}
	}
}
