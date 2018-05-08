package com.chatbot.models.database;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "state")
public class State {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idState;
	private String idUsername;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id")
	private Event idEvent;
	
	public State() {
		super();
	}	
	
	public State(String idUsername) {
		super();
		this.idUsername = idUsername;
	}
	
	public State(Event idEvent) {
		super();
		this.idEvent = idEvent;
	}

	public State(String idUsername, Event idEvent) {
		super();
		this.idUsername = idUsername;
		this.idEvent = idEvent;
	}

	public Integer getIdState() {
		return idState;
	}
	
	public void setIdState(Integer idState) {
		this.idState = idState;
	}
	
	public String getIdUsername() {
		return idUsername;
	}
	
	public void setIdUsername(String idUsername) {
		this.idUsername = idUsername;
	}
	
	public Event getIdEvent() {
		return idEvent;
	}
	
	public void setIdEvent(Event idEvent) {
		this.idEvent = idEvent;
	}
	
}
