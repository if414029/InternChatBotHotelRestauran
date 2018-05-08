package com.chatbot.models.facebook;

public class FacebookSend {
	private FacebookSendRecipient recipient;
	private FacebookSendMessage message;
	
	public FacebookSend(FacebookSendRecipient recipient, FacebookSendMessage message) {
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
	
	public FacebookSendMessage getMessage() {
		return message;
	}
	
	public void setMessage(FacebookSendMessage message) {
		this.message = message;
	}
	
	
}
