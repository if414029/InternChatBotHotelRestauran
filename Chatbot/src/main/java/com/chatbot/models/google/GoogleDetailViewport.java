package com.chatbot.models.google;

public class GoogleDetailViewport {
	private GoogleLocation northeast;
	private GoogleLocation southwest;
	
	public GoogleLocation getNortheast() {
		return northeast;
	}
	
	public void setNortheast(GoogleLocation northeast) {
		this.northeast = northeast;
	}
	
	public GoogleLocation getSouthwest() {
		return southwest;
	}
	
	public void setSouthwest(GoogleLocation southwest) {
		this.southwest = southwest;
	}

	@Override
	public String toString() {
		return "GoogleDetailViewport [northeast=" + northeast + ", southwest=" + southwest + "]";
	}
	
}
