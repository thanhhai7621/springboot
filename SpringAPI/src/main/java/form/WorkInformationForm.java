package main.java.form;

import java.util.List;

import main.java.bean.WorkTask;

/**
 * Work task information list
 * @author HaiDT3
 *
 */
public class WorkInformationForm {
	private List<WorkTask> workTaskList;

	public List<WorkTask> getWorkTaskList() {
		return workTaskList;
	}

	public void setWorkTaskList(List<WorkTask> workTaskList) {
		this.workTaskList = workTaskList;
	}

	
}
