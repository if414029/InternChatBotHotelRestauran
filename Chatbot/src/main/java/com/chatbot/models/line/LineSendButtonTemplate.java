package com.chatbot.models.line;

import java.util.List;

public class LineSendButtonTemplate {
	private String type;
	private String thumbnailImageUrl;
	private String title;
	private String text;
	private List<LineSendButtonAction> actions;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getThumbnailImageUrl() {
		return thumbnailImageUrl;
	}
	
	public void setThumbnailImageUrl(String thumbnailImageUrl) {
		this.thumbnailImageUrl = thumbnailImageUrl;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public List<LineSendButtonAction> getActions() {
		return actions;
	}
	
	public void setActions(List<LineSendButtonAction> actions) {
		this.actions = actions;
	}
	
}
