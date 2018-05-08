package com.chatbot.models.facebook;

import java.util.List;

public class FacebookSendButtonPayload {
	private String template_type;
	private String text;
	private List<FacebookSendButton> buttons;
	
	public FacebookSendButtonPayload(String template_type, String text, List<FacebookSendButton> buttons) {
		super();
		this.template_type = template_type;
		this.text = text;
		this.buttons = buttons;
	}

	public String getTemplate_type() {
		return template_type;
	}
	
	public void setTemplate_type(String template_type) {
		this.template_type = template_type;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public List<FacebookSendButton> getButtons() {
		return buttons;
	}
	
	public void setButtons(List<FacebookSendButton> buttons) {
		this.buttons = buttons;
	}
	
}
