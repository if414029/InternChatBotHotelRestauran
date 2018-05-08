package com.chatbot.models.line;

import java.util.List;

public class Line {
	private List<LineEvents> events;

	public List<LineEvents> getEvents() {
		return events;
	}

	public void setEvents(List<LineEvents> events) {
		this.events = events;
	}

	@Override
	public String toString() {
		return "Line [events=" + events + "]";
	}
	
}
