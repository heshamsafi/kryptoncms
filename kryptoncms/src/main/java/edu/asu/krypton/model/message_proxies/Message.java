package edu.asu.krypton.model.message_proxies;

public class Message {
	private boolean successful;
	private String errorMessage;
	public boolean isSuccessful() {
		return successful;
	}

	public Message setSuccessful(boolean successful) {
		this.successful = successful;
		return this;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public Message setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		return this;
	}
}
