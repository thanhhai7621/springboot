package main.java.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

import main.java.form.MessageInformationForm;
/**
 * 
 * @author HaiDT3
 *
 */
public class ValidateDataLogic {

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	/**
	 * This method do check validity of data 
	 * @param workName
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @return
	 */
	public static MessageInformationForm checkDataInput(String workName, String startDate, String endDate,
			String status) {
		if (checkWorkTask(workName.trim())) {
			return new MessageInformationForm("Work name is required", "Error input");
		}
		if (checkFormatDate(startDate.trim())) {
			return new MessageInformationForm("Start Date is required and format is yyyy/MM/dd", "Error input");
		}
		if (checkFormatDate(endDate.trim())) {
			return new MessageInformationForm("End Date is required and format is yyyy/MM/dd", "Error input");
		}
		if (checkStatus(status.trim())) {
			return new MessageInformationForm("Status is required and format is [Planning, Doing, Completed]",
					"Error input");
		}
		return null;

	}

	/**
	 * Check correlate date start Date < end Date
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean checkCorrelateDate(String startDate, String endDate) {

		try {
			Date endDatetmp = sdf.parse(endDate);
			Date startDatetmp = sdf.parse(startDate);
			if (startDatetmp.equals(endDatetmp)) {
				return false;
			}
			if (startDatetmp.before(endDatetmp)) {
				return false;
			} else {
				return true;
			}
		} catch (ParseException e) {
			// The previous format check will be accepted
		}
		return false;
	}

	/**
	 * Check format date of input, if true is incorrect format
	 * 
	 * @param date
	 * @return
	 */
	public static boolean checkFormatDate(String date) {

		try {
			sdf.parse(date);
			return false;
		} catch (ParseException e) {
			return true;
		}

	}

	/**
	 * Check work name is not blank or null
	 * 
	 * @param workTask
	 * @return boolean
	 */
	public static boolean checkWorkTask(String workTask) {
		if (!StringUtils.isEmpty(workTask)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Check status format 1 is planning 2 is doing 3 completed
	 * 
	 * @param status
	 * @return boolean
	 */
	public static boolean checkStatus(String status) {
		if (!StringUtils.isEmpty(status) & ("planning".equalsIgnoreCase(status) || "doing".equalsIgnoreCase(status)
				|| "completed".equalsIgnoreCase(status))) {
			return false;
		} else {
			return true;
		}

	}
}
