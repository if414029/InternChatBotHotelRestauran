package com.chatbot.models.kaskus;

import java.util.List;

public class KaskusSendGenericBody {
	private List<KaskusSendGenericInteractive> interactives;

	public KaskusSendGenericBody(List<KaskusSendGenericInteractive> interactives) {
		super();
		this.interactives = interactives;
	}

	public List<KaskusSendGenericInteractive> getInteractives() {
		return interactives;
	}

	public void setInteractives(List<KaskusSendGenericInteractive> interactives) {
		this.interactives = interactives;
	}
	
}
