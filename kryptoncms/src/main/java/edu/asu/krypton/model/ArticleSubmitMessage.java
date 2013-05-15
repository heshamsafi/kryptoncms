package edu.asu.krypton.model;

import edu.asu.krypton.model.message_proxies.Message;

public class ArticleSubmitMessage extends Message {
	private String id;

	public String getId() {
		return id;
	}

	public ArticleSubmitMessage setId(String id) {
		this.id = id;
		return this;
	}
}
