package com.chatbot.models.line;

import java.util.List; 

public class LineSendButton {	
	private String to;
	private List<LineSendButtonMessages> messages;
	
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	public List<LineSendButtonMessages> getMessages() {
		return messages;
	}
	
	public void setMessages(List<LineSendButtonMessages> messages) {
		this.messages = messages;
	}
	
	
}
