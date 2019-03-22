package main.java.form;

/**
 * Message information of server return
 * @author HaiDT3
 *
 */
public class MessageInformationForm {
	private String message;
	private String status;

	public MessageInformationForm(String message, String status) {
		this.message = message;
		this.status = status;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
