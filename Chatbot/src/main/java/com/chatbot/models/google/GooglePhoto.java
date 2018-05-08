package com.chatbot.models.google;

import java.util.ArrayList;

public class GooglePhoto {
	private long height;
	private ArrayList<String> html_attributions = new ArrayList<String>();
	private String photo_reference;
	private String width;
	
	public long getHeight() {
		return height;
	}
	
	public void setHeight(long height) {
		this.height = height;
	}
	
	public ArrayList<String> getHtml_attributions() {
		return html_attributions;
	}
	
	public void setHtml_attributions(ArrayList<String> html_attributions) {
		this.html_attributions = html_attributions;
	}

	public String getPhoto_reference() {
		return photo_reference;
	}

	public void setPhoto_reference(String photo_reference) {
		this.photo_reference = photo_reference;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	@Override
	public String toString() {
		return "GooglePhoto [height=" + height + ", html_attributions=" + html_attributions + ", photo_reference="
				+ photo_reference + ", width=" + width + "]";
	}
	
	
}
