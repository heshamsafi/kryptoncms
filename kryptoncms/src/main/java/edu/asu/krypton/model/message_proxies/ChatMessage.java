package edu.asu.krypton.model.message_proxies;

public class ChatMessage {
	private String source;
	private String destinations[];
	private String body;
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String[] getDestinations() {
		return destinations;
	}
	public void setDestinations(String[] destinations) {
		this.destinations = destinations;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return String.format("ChatMessage[source=%s, destinations=%d, body=%s]",getSource(),getDestinations().length,getBody());
	}	
	
	
}
