package com.chatbot.models.google;

public class GoogleGeometry {
	private GoogleLocation location;

	public GoogleLocation getLocation() {
		return location;
	}

	public void setLocation(GoogleLocation location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "GoogleGeometry [location=" + location + "]";
	}
	
}
