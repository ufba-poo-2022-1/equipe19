package com.api.ticketshop.Repositories;

import com.api.ticketshop.Models.EventModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<EventModel, Integer> {

}
