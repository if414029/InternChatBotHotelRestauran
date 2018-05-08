package com.chatbot.models.facebook;

public class FacebookTemplate {
	private FacebookSendRecipient recipient;
	private FacebookSendGenericMessage message;
	
	
	public FacebookTemplate(FacebookSendRecipient recipient, FacebookSendGenericMessage message) {
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

	public FacebookSendGenericMessage getMessage() {
		return message;
	}

	public void setMessage(FacebookSendGenericMessage message) {
		this.message = message;
	}
	
	
}
