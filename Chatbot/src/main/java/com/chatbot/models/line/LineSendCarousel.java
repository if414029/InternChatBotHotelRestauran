package com.chatbot.models.line;

import java.util.List;

public class LineSendCarousel {
	private String to;
	private List<LineSendCarouselMessage> messages;
	
	public LineSendCarousel(String to, List<LineSendCarouselMessage> messages) {
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
	
	public List<LineSendCarouselMessage> getMessages() {
		return messages;
	}
	
	public void setMessages(List<LineSendCarouselMessage> messages) {
		this.messages = messages;
	}
	
	
}
