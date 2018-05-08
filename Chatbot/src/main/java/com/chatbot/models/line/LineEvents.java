package com.chatbot.models.line;

public class LineEvents {
	private String replyToken;
	private String type;
	private String timestamp;
	private LineSource source;
	private LineMessage message;
	private LinePostback postback;
	
	public String getReplyToken() {
		return replyToken;
	}
	
	public void setReplyToken(String replyToken) {
		this.replyToken = replyToken;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public LineSource getSource() {
		return source;
	}
	
	public void setSource(LineSource source) {
		this.source = source;
	}
	
	public LineMessage getMessage() {
		return message;
	}
	
	public void setMessage(LineMessage message) {
		this.message = message;
	}
	
	

	public LinePostback getPostback() {
		return postback;
	}

	public void setPostback(LinePostback postback) {
		this.postback = postback;
	}

	@Override
	public String toString() {
		return "LineEvents [replyToken=" + replyToken + ", type=" + type + ", timestamp=" + timestamp + ", source="
				+ source + ", message=" + message + ", postback=" + postback + "]";
	}

	
}
