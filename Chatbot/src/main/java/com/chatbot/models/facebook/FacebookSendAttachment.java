package com.chatbot.models.facebook;

public class FacebookSendAttachment {
	private String type;
	private FacebookSendButtonPayload payload;
	
	public FacebookSendAttachment(String type, FacebookSendButtonPayload payload) {
		super();
		this.type = type;
		this.payload = payload;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public FacebookSendButtonPayload getPayload() {
		return payload;
	}
	
	public void setPayload(FacebookSendButtonPayload payload) {
		this.payload = payload;
	}
	
}
