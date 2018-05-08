package com.chatbot.models.facebook;

public class FacebookSendMessage {
	private String text;

	public FacebookSendMessage(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
