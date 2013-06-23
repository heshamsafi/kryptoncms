package edu.asu.krypton.service.atmosphere.chat;

import java.util.ArrayList;
import java.util.List;

public class BroadcastMessage {
	private List<String> paths = new ArrayList<String>();
	private String message;
	public List<String> getPaths() {
		return paths;
	}
	public void setPaths(List<String> paths) {
		this.paths = paths;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
