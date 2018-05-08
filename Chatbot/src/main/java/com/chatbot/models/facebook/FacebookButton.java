package com.chatbot.models.facebook;

public class FacebookButton {
	private FacebookSendRecipient recipient;
	private FacebookSendButtonMessage message;
	
	public FacebookButton(FacebookSendRecipient recipient, FacebookSendButtonMessage message) {
		super();
		this.recipient = recipient;
		this.message = message;
	}

	public FacebookSendRecipient getRecipient() {
		return recipient;
	}
	
	public void setRecipient(FacebookSendRecipient recipient) {
		this.recipient = recipient;
	}
	
	public FacebookSendButtonMessage getMessage() {
		return message;
	}
	
	public void setMessage(FacebookSendButtonMessage message) {
		this.message = message;
	}
	
}
