package com.chatbot.models.google;

public class GoogleDetailGeometry {
	private GoogleLocation location;
	private GoogleDetailViewport viewport;
	
	public GoogleLocation getLocation() {
		return location;
	}
	
	public void setLocation(GoogleLocation location) {
		this.location = location;
	}
	
	public GoogleDetailViewport getViewport() {
		return viewport;
	}
	
	public void setViewport(GoogleDetailViewport viewport) {
		this.viewport = viewport;
	}

	@Override
	public String toString() {
		return "GoogleDetailGeometry [location=" + location + ", viewport=" + viewport + "]";
	}
	
}
