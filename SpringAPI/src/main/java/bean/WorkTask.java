package main.java.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "worktask")
/**
 * Table information work task : column idWork, WORK_NAME, START_DATE, END_DATE, STATUS
 * @author HaiDT3
 *
 */
public class WorkTask implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idWORK;
	@Column(name = "WORK_NAME", nullable = false)
	private String workName;
	@Column(name = "START_DATE", nullable = false)
	private Date startDate;
	@Column(name = "END_DATE", nullable = false)
	private Date endDate;
	@Column(name = "STATUS", nullable = false)
	private int status;

	public int getIdWORK() {
		return idWORK;
	}

	public void setIdWORK(int idWORK) {
		this.idWORK = idWORK;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
