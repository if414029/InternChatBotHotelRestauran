package com.chatbot.models.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "event")
public class Event {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idEvent;
	private String description;
	
	@OneToMany(mappedBy = "idEvent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<State> states = new ArrayList<>();
	
	public Event() {
		super();
	}

	public Event(String description) {
		super();
		this.description = description;
	}

	public Integer getIdEvent() {
		return idEvent;
	}
	
	public void setIdEvent(Integer idEvent) {
		this.idEvent = idEvent;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}
	
	
}
