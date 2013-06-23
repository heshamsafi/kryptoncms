package edu.asu.krypton.model;

import edu.asu.krypton.model.message_proxies.Message;

public class ArticleSubmitMessage extends Message {
	private String id;
	private String title;
	private String version;
	public String getId() {
		return id;
	}

	public ArticleSubmitMessage setId(String id) {
		this.id = id;
		return this;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
		
	}

	public String getTitle() {
		return title;
	}

	public ArticleSubmitMessage setTitle(String title) {
		this.title = title;
		return this;

	}
}
