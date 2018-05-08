package com.chatbot.models.facebook;

import java.util.List;

public class FacebookSendElement {
	private String title;
	private String image_url;
	private String subtitle;
	private FacebookSendDefaultAction default_action;
	private List<FacebookSendButton> buttons;
	
	public FacebookSendElement(String title, String image_url, String subtitle,
			FacebookSendDefaultAction default_action, List<FacebookSendButton> buttons) {
		super();
		this.title = title;
		this.image_url = image_url;
		this.subtitle = subtitle;
		this.default_action = default_action;
		this.buttons = buttons;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getImage_url() {
		return image_url;
	}
	
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	
	public String getSubtitle() {
		return subtitle;
	}
	
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	
	public FacebookSendDefaultAction getDefault_action() {
		return default_action;
	}
	
	public void setDefault_action(FacebookSendDefaultAction default_action) {
		this.default_action = default_action;
	}
	
	public List<FacebookSendButton> getButtons() {
		return buttons;
	}
	
	public void setButtons(List<FacebookSendButton> buttons) {
		this.buttons = buttons;
	}
	
}
