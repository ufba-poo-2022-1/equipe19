package com.api.ticketshop.Services;

import com.api.ticketshop.Repositories.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


}
