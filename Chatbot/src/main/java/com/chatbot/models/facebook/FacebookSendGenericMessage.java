package com.chatbot.models.facebook;

public class FacebookSendGenericMessage {
	private FacebookAttachment attachment;

	public FacebookSendGenericMessage(FacebookAttachment attachment) {
		super();
		this.attachment = attachment;
	}

	public FacebookAttachment getAttachment() {
		return attachment;
	}

	public void setAttachment(FacebookAttachment attachment) {
		this.attachment = attachment;
	}
	
}
