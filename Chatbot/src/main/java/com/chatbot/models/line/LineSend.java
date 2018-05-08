package com.chatbot.models.line;

import java.util.List;

public class LineSend {
	private String to;
	private List<LineSendMessage> messages;
	
	public LineSend(String to, List<LineSendMessage> messages) {
		super();
		this.to = to;
		this.messages = messages;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public List<LineSendMessage> getMessages() {
		return messages;
	}
	
	public void setMessages(List<LineSendMessage> messages) {
		this.messages = messages;
	}
	
}
