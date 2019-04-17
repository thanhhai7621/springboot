package main.java.controller;

import java.util.List;

import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.java.bean.WorkTask;
import main.java.dao.WorkTaskDAO;
import main.java.form.MessageInformationForm;
import main.java.form.WorkInformationForm;
import main.java.logic.ValidateDataLogic;
import main.java.util.CommonUtils;

@RestController
public class WorkTaskController {

	@RequestMapping("/display")
	public WorkInformationForm display(@RequestParam MultiValueMap<String, String> params) {
		WorkTaskDAO taskDao = new WorkTaskDAO();
		// Set default value for Pagination
		int page = 1;
		int maxResult = 1000;

		// Check data input
		if (!StringUtils.isEmpty(getDataFromParams(params, "page").trim())) {
			try {
				page = Integer.parseInt(getDataFromParams(params, "page").trim());
			} catch (Exception e) {
				// get default value
			}
		}
		if (!StringUtils.isEmpty(getDataFromParams(params, "maxResult").trim())) {
			try {
				maxResult = Integer.parseInt(getDataFromParams(params, "maxResult").trim());
				if (maxResult == 0) {
					maxResult = 1000;
				}
			} catch (Exception e) {
				// get default value
			}
		}
		List<WorkTask> workTaskBeanList = taskDao.getAllWorkTask(getDataFromParams(params, "sort").trim(), page,
				maxResult);
		WorkInformationForm workInformationFormList = new WorkInformationForm();
		workInformationFormList.setWorkTaskList(workTaskBeanList);
		return workInformationFormList;
	}

	@RequestMapping("/update")
	public MessageInformationForm update(@RequestParam MultiValueMap<String, String> params) {
		// Check required input
		if (StringUtils.isEmpty(getDataFromParams(params, "id"))) {
			return new MessageInformationForm("Id is required", "Error input");
		}
		// Check input is number
		try {
			Integer.parseInt(getDataFromParams(params, "id"));
		} catch (Exception e) {
			return new MessageInformationForm("Id need is integer", "Error input");
		}

		// Check start date and end date
		if (!"".equals(getDataFromParams(params, "startDate").trim())
				&& ValidateDataLogic.checkFormatDate(getDataFromParams(params, "startDate").trim())) {
			return new MessageInformationForm("Start Date is required and format is yyyy/MM/dd", "Error input");
		}
		if (!"".equals(getDataFromParams(params, "endDate").trim())
				&& ValidateDataLogic.checkFormatDate(getDataFromParams(params, "endDate").trim())) {
			return new MessageInformationForm("End Date is required and format is yyyy/MM/dd", "Error input");
		}
		// Check status format
		if (!"".equals(getDataFromParams(params, "status").trim())
				&& ValidateDataLogic.checkStatus(getDataFromParams(params, "status").trim())) {
			return new MessageInformationForm("Status is required and format is [Planning, Doing, Completed]",
					"Error input");
		}
		// Check correlate date start Date < end Date
		if (ValidateDataLogic.checkCorrelateDate(getDataFromParams(params, "startDate").trim(),
				getDataFromParams(params, "endDate").trim())) {
			return new MessageInformationForm("Start Date need before End Date", "Error correlate");
		}
		
		// Check exits id
		WorkTaskDAO taskDao = new WorkTaskDAO();
		long count = taskDao.countWorkTask(Integer.parseInt(getDataFromParams(params, "id")));
		if (count == 0) {
			return new MessageInformationForm("Id not exits", "Error input");
		}
		
		taskDao.updateWorkTask(Integer.parseInt(getDataFromParams(params, "id")),
				getDataFromParams(params, "workName").trim(), getDataFromParams(params, "startDate").trim(),
				getDataFromParams(params, "endDate").trim(),
				CommonUtils.convertStatusToInt(getDataFromParams(params, "status").trim()));

		return new MessageInformationForm("Update is success", "success");
	}

	@RequestMapping("/delete")
	public MessageInformationForm delete(@RequestParam MultiValueMap<String, String> params) {
		// Check required input
		if (StringUtils.isEmpty(getDataFromParams(params, "id"))) {
			return new MessageInformationForm("Id is required", "Error input");
		}
		// Check input is number
		try {
			Integer.parseInt(getDataFromParams(params, "id"));
		} catch (Exception e) {
			return new MessageInformationForm("Id need is integer", "Error input");
		}

		// Check exits id
		WorkTaskDAO taskDao = new WorkTaskDAO();
		long count = taskDao.countWorkTask(Integer.parseInt(getDataFromParams(params, "id")));
		if (count == 0) {
			return new MessageInformationForm("Id not exits", "Error input");
		}
		// Delete data
		taskDao.deleteWorkTask(Integer.parseInt(getDataFromParams(params, "id")));

		return new MessageInformationForm("Delete is success", "success");
	}
	
	@RequestMapping("/message")
	public MessageInformationForm message(@RequestParam MultiValueMap<String, String> params) {

		return new MessageInformationForm("Hello", "success");
	}

	@RequestMapping("/insert")
	public MessageInformationForm insert(@RequestParam MultiValueMap<String, String> params) {
		// Check input data
		MessageInformationForm mesageError = ValidateDataLogic.checkDataInput(
				getDataFromParams(params, "workName").trim(), getDataFromParams(params, "startDate").trim(),
				getDataFromParams(params, "endDate").trim(), getDataFromParams(params, "status").trim());
		// return message if have
		if (mesageError != null) {
			return mesageError;
		}

		// Check correlate date start Date < end Date
		if (ValidateDataLogic.checkCorrelateDate(getDataFromParams(params, "startDate").trim(),
				getDataFromParams(params, "endDate").trim())) {
			return new MessageInformationForm("Start Date need before End Date", "Error correlate");
		}
		// Insert data to DB
		try {
			WorkTaskDAO taskDAO = new WorkTaskDAO();
			taskDAO.saveWorkTask(getDataFromParams(params, "workName").trim(),
					getDataFromParams(params, "startDate").trim(), getDataFromParams(params, "endDate").trim(),
					CommonUtils.convertStatusToInt(getDataFromParams(params, "status")));
		} catch (Exception e) {
			return new MessageInformationForm("System Error!!", "systemERROR");
		}
		return new MessageInformationForm("Insert data is success", "success");
	}

	/**
	 * Get data from url with key
	 * 
	 * @param params
	 * @param key
	 * @return
	 */
	private String getDataFromParams(MultiValueMap<String, String> params, String key) {
		String data = "";
		if (params.get(key) != null) {
			data = params.get(key).get(0);
		}
		return data;

	}
}
