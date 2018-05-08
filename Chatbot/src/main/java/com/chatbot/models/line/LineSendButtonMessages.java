package com.chatbot.models.line;

public class LineSendButtonMessages {
	private String type;
	private String altText;
	private LineSendButtonTemplate template;
	
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
	
	public LineSendButtonTemplate getTemplate() {
		return template;
	}
	
	public void setTemplate(LineSendButtonTemplate template) {
		this.template = template;
	}
	
}
