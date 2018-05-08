package com.chatbot.models.facebook;

public class FacebookMessaging {
	private FacebookSender sender;
	private FacebookRecipient recipient;
	private long timestamp;
	private FacebookMessage message;
	private FacebookPostback postback;
	
	public FacebookSender getSender() {
		return sender;
	}
	
	public void setSender(FacebookSender sender) {
		this.sender = sender;
	}
	
	public FacebookRecipient getRecipient() {
		return recipient;
	}
	
	public void setRecipient(FacebookRecipient recipient) {
		this.recipient = recipient;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public FacebookMessage getMessage() {
		return message;
	}
	
	public void setMessage(FacebookMessage message) {
		this.message = message;
	}
	
	

	public FacebookPostback getPostback() {
		return postback;
	}

	public void setPostback(FacebookPostback postback) {
		this.postback = postback;
	}

	@Override
	public String toString() {
		return "FacebookMessaging [sender=" + sender + ", recipient=" + recipient + ", timestamp=" + timestamp
				+ ", message=" + message + ", postback=" + postback + "]";
	}
	
}
