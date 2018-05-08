package com.chatbot.models.facebook;

public class FacebookSendButtonMessage {
	private FacebookSendAttachment attachment;

	public FacebookSendButtonMessage(FacebookSendAttachment attachment) {
		super();
		this.attachment = attachment;
	}

	public FacebookSendAttachment getAttachment() {
		return attachment;
	}

	public void setAttachment(FacebookSendAttachment attachment) {
		this.attachment = attachment;
	}
	
}
