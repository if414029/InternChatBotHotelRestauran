package com.chatbot.models.line;

public class LineSendCarouselMessage {
	private String type;
	private String altText;
	private LineSendCarouselTemplate template;
	
	public LineSendCarouselMessage(String type, String altText, LineSendCarouselTemplate template) {
		super();
		this.type = type;
		this.altText = altText;
		this.template = template;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getAltText() {
		return altText;
	}
	
	public void setAltText(String altText) {
		this.altText = altText;
	}
	
	public LineSendCarouselTemplate getTemplate() {
		return template;
	}
	
	public void setTemplate(LineSendCarouselTemplate template) {
		this.template = template;
	}
	
}
