package com.chatbot.models.line;

public class LinePostback {
	private String data;
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "LinePostback [data=" + data + "]";
	}
	
	
}
