package com.chatbot.models.google;

import java.util.ArrayList;

public class GoogleDetail {
	private ArrayList<String> html_attributions = new ArrayList<String>();
	private GoogleDetailResult result;
	private String status;
	
	public ArrayList<String> getHtml_attributions() {
		return html_attributions;
	}
	
	public void setHtml_attributions(ArrayList<String> html_attributions) {
		this.html_attributions = html_attributions;
	}
	
	public GoogleDetailResult getResult() {
		return result;
	}
	
	public void setResult(GoogleDetailResult result) {
		this.result = result;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "GoogleDetail [html_attributions=" + html_attributions + ", result=" + result + ", status=" + status
				+ "]";
	}
	
}
