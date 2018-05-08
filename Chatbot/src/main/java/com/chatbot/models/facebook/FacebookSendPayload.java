package com.chatbot.models.facebook;

import java.util.List;

public class FacebookSendPayload {
	private String template_type;
	private List<FacebookSendElement> elements;
	
	public FacebookSendPayload(String template_type, List<FacebookSendElement> elements) {
		super();
		this.template_type = template_type;
		this.elements = elements;
	}

	public String getTemplate_type() {
		return template_type;
	}
	
	public void setTemplate_type(String template_type) {
		this.template_type = template_type;
	}
	
	public List<FacebookSendElement> getElements() {
		return elements;
	}
	
	public void setElements(List<FacebookSendElement> elements) {
		this.elements = elements;
	}
	
}
