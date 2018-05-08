package com.chatbot.models.google;

public class GoogleAltId {
	private String place_id;
	private String scope;
	
	public String getPlace_id() {
		return place_id;
	}
	
	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "GoogleAltId [place_id=" + place_id + ", scope=" + scope + "]";
	}
	
}
