package com.chatbot.models.facebook;

import java.util.List;

public class FacebookEntry {
	private long id;
	private long time;
	private List<FacebookMessaging> messaging;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTime() {
		return time;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public List<FacebookMessaging> getMessaging() {
		return messaging;
	}
	
	public void setMessaging(List<FacebookMessaging> messaging) {
		this.messaging = messaging;
	}

	@Override
	public String toString() {
		return "FacebookEntry [id=" + id + ", time=" + time + ", messaging=" + messaging + "]";
	}
	
	
}
