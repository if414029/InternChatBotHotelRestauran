package com.chatbot.models.facebook;

public class FacebookAttachment {
	private String type;
	private FacebookSendPayload payload;
	
	public FacebookAttachment(String type, FacebookSendPayload payload) {
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
	
	public FacebookSendPayload getPayload() {
		return payload;
	}
	
	public void setPayload(FacebookSendPayload payload) {
		this.payload = payload;
	}
	
}
