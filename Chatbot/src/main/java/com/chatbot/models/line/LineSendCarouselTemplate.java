package com.chatbot.models.line;

import java.util.List;

public class LineSendCarouselTemplate {
	private String type;
	private List<LineSendCarouselColumn> columns;
	
	public LineSendCarouselTemplate(String type, List<LineSendCarouselColumn> columns) {
		super();
		this.type = type;
		this.columns = columns;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public List<LineSendCarouselColumn> getColumns() {
		return columns;
	}
	
	public void setColumns(List<LineSendCarouselColumn> columns) {
		this.columns = columns;
	}
	
}
