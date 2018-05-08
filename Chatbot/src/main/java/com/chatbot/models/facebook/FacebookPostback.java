package com.chatbot.models.facebook;

public class FacebookPostback {
	private String payload;
	private String title;
	
	public String getPayload() {
		return payload;
	}
	
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "FacebookPostback [payload=" + payload + ", title=" + title + "]";
	}
	
}
