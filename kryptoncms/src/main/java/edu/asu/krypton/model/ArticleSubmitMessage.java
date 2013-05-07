package edu.asu.krypton.model;

import edu.asu.krypton.model.message_proxies.Message;

public class ArticleSubmitMessage extends Message {
	private Long id;

	public Long getId() {
		return id;
	}

	public ArticleSubmitMessage setId(Long id) {
		this.id = id;
		return this;
	}
}
