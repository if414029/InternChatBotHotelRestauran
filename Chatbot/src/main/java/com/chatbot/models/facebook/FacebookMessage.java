package com.chatbot.models.facebook;


public class FacebookMessage {
	private String mid;
	private long seq;
	private String text;
	
	public String getMid() {
		return mid;
	}
	
	public void setMid(String mid) {
		this.mid = mid;
	}
	
	public long getSeq() {
		return seq;
	}
	
	public void setSeq(long seq) {
		this.seq = seq;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "FacebookMessage [mid=" + mid + ", seq=" + seq + ", text=" + text + "]";
	}
	
}
