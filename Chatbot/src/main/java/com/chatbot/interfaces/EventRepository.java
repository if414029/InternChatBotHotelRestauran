package com.chatbot.interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chatbot.models.database.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer>{

}
