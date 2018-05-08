package com.chatbot.models.google;

import java.util.ArrayList;
import java.util.List;

public class Google {
	private ArrayList<String> html_attributions = new ArrayList<String>();
	private List<GoogleResult> results;
	private String status;
	
	public ArrayList<String> getHtml_attributions() {
		return html_attributions;
	}
	
	public void setHtml_attributions(ArrayList<String> html_attributions) {
		this.html_attributions = html_attributions;
	}
	
	public List<GoogleResult> getResults() {
		return results;
	}
	
	public void setResults(List<GoogleResult> results) {
		this.results = results;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Google [html_attributions=" + html_attributions + ", results=" + results + ", status=" + status + "]";
	}
	
}
