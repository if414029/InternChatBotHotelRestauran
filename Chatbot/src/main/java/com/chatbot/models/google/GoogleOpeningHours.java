package com.chatbot.models.google;

public class GoogleOpeningHours {
	private Boolean open_now;

	public Boolean getOpen_now() {
		return open_now;
	}

	public void setOpen_now(Boolean open_now) {
		this.open_now = open_now;
	}

	@Override
	public String toString() {
		return "GoogleOpeningHours [open_now=" + open_now + "]";
	}
	
}
