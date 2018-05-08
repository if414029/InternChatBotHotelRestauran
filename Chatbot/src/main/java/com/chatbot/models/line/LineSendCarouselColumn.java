package com.chatbot.models.line;

import java.util.List;

public class LineSendCarouselColumn {
	private String thumbnailImageUrl;
	private String title;
	private String text;
	private List<LineSendCarouselAction> actions;
	
	public LineSendCarouselColumn(String thumbnailImageUrl, String title, String text,
			List<LineSendCarouselAction> actions) {
		super();
		this.thumbnailImageUrl = thumbnailImageUrl;
		this.title = title;
		this.text = text;
		this.actions = actions;
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
	
	public List<LineSendCarouselAction> getActions() {
		return actions;
	}
	
	public void setActions(List<LineSendCarouselAction> actions) {
		this.actions = actions;
	}
	
}
