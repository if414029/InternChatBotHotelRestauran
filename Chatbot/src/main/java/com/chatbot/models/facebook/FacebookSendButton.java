package com.chatbot.models.facebook;

public class FacebookSendButton {
	private String type;
	private String url;
	private String title;
	private String payload;
	
	public FacebookSendButton(String type, String url, String title, String payload) {
		super();
		this.type = type;
		this.url = url;
		this.title = title;
		this.payload = payload;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getPayload() {
		return payload;
	}
	
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
}
