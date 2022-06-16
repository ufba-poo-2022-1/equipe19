package com.api.ticketshop.Services;

import com.api.ticketshop.Models.EventModel;
import com.api.ticketshop.Repositories.EventRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    final EventRepository eventRepository;

    /**
     * Class constructor that receives the Repository Interface.
     * This constructor is actually a Dependency Injection Point.
     */
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    /**
     * Method to create an Event in the database.
     */
    public EventModel createNewEvent(EventModel eventModel) {
        return eventRepository.save(eventModel);
    }

    /**
     * Method to list all existing events in the database.
     */
    public List<EventModel> listAllEvents() {
        return (List<EventModel>) eventRepository.findAll();
    }

    /**
     * Method to find an event by its id.
     */
    public Optional<EventModel> getEventByID(Integer id) {
        return eventRepository.findById(id);
    }

    /**
     * Method to delete an event.
     */
    @Transactional
    public void deleteEvent(EventModel eventModel) {
        eventRepository.delete(eventModel);
    }
}
