package com.chatbot.interfaces;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chatbot.models.database.Event;
import com.chatbot.models.database.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
	State findByIdUsername(@Param("id_username") String idUsername);
	
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query("UPDATE State s SET event_id = :event_id WHERE id_state = :id_state")
    void editEvent(@Param("event_id") Event event_id, @Param("id_state") Integer id_state);
}
