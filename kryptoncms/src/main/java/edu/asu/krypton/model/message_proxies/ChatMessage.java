package edu.asu.krypton.model.message_proxies;

public class ChatMessage {
	//i know enums are better for this, but i am on a really tight schedule !
	private String action;//DELETE_CHANNEL,ADD_CHANNEL,ADD_PARTY,REMOVE_PARTY,BROADCAST_MESSAGE
	private String sourceUsername;
	private String conversationId;
	private String messageBody;
	private String[] parties;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getSourceUsername() {
		return sourceUsername;
	}
	public void setSourceUsername(String sourceUsername) {
		this.sourceUsername = sourceUsername;
	}
	public String getConversationId() {
		return conversationId;
	}
	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}
	public String getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	public String[] getParties() {
		return parties;
	}
	public void setParties(String[] parties) {
		this.parties = parties;
	}
	
}
