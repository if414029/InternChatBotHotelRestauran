package com.chatbot.models.facebook;

import java.util.List;

public class Facebook {
	private String object;
	private List<FacebookEntry> entry;
	
	public String getObject() {
		return object;
	}
	
	public void setObject(String object) {
		this.object = object;
	}
	
	public List<FacebookEntry> getEntry() {
		return entry;
	}
	
	public void setEntry(List<FacebookEntry> entry) {
		this.entry = entry;
	}

	@Override
	public String toString() {
		return "Facebook [object=" + object + ", entry=" + entry + "]";
	}
	
	
}
