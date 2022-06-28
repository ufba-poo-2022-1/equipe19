package com.api.ticketshop.Controllers;

import com.api.ticketshop.DTOs.EventDTO;
import com.api.ticketshop.Models.EventModel;
import com.api.ticketshop.Models.SeatModel;
import com.api.ticketshop.Models.TicketModel;
import com.api.ticketshop.Services.EventService;
import com.api.ticketshop.Services.SeatService;
import com.api.ticketshop.Services.TicketService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Class that contains all endpoints of an Event
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/events")
public class EventController {
    final EventService eventService;
    final TicketService ticketService;
    final SeatService seatService;
    /**
     * Class constructor that receives the Service Interface.
     * This constructor is actually a Dependency Injection Point.
     * @param eventService, ticketService, seatService
     */
    public EventController(EventService eventService, TicketService ticketService, SeatService seatService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.seatService = seatService;
    }

    /**
     * Method to creat a new Event.
     * @param eventDTO
     * @return ResponseEntity<Object>
     */
    @PostMapping
    public ResponseEntity<Object> newEvent(@RequestBody @Valid EventDTO eventDTO){
        EventModel eventModel = new EventModel();
        BeanUtils.copyProperties(eventDTO, eventModel);

        DateTimeFormatter parserDate = new DateTimeFormatterBuilder()
                .appendPattern("dd/MM/yyyy")
                .parseDefaulting(ChronoField.ERA, 1)
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT);
        DateTimeFormatter parserTime = DateTimeFormatter.ofPattern("HH:mm");

        try{
            LocalDate parsedDate = LocalDate.parse(eventDTO.getDate(), parserDate);
            eventModel.setDate(parsedDate);

            LocalTime parsedStartTime = LocalTime.parse(eventDTO.getStart_time(), parserTime);
            eventModel.setStart_time(parsedStartTime);

            LocalTime parsedFinishTime = LocalTime.parse(eventDTO.getFinish_time(), parserTime);
            eventModel.setFinish_time(parsedFinishTime);
        } catch (java.time.DateTimeException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Date/time format is not acceptable");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createNewEvent(eventModel));
    }

    /**
     *  Method to list all Events.
     * @return ResponseEntity<Object>
     */
    @GetMapping
    public ResponseEntity<Object> getAllEvents(){

        List<EventModel> events_list = eventService.listAllEvents();

        if(events_list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No events yet");
        }

        return ResponseEntity.status(HttpStatus.OK).body(events_list);
    }

    /**
     * Method to list a specific event by its id.
     * @param id
     * @return ResponseEntity<Object>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEventByID(@PathVariable(value = "id") Integer id) {

        Optional<EventModel> eventModelOptional = eventService.getEventByID(id);

        if(eventModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No event found with the given id");
        }

        return ResponseEntity.status(HttpStatus.OK).body(eventModelOptional.get());
    }

    /**
     * Method to delete a specific event by its id.
     * @param eventId
     */
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Object> deleteEventById(@PathVariable(value = "eventId") Integer eventId){

        Optional<EventModel> eventModelOptional = eventService.getEventByID(eventId);
        List<TicketModel> ticketModelList = ticketService.getTicketModelByEventId(eventId);
        List<SeatModel> seatModelList = seatService.getAllSeatsByEventID(eventId);

        if(eventModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No event found with the given id");
        }

        if(ticketModelList.size() > 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This event cannot be deleted because there are tickets purchased for it");
        }

        if(seatModelList.size() > 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This event cannot be deleted");
        }

        eventService.deleteEvent(eventModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Event deleted successfully");
    }

    /**
     * Method to update a specific event by its id.
     * @param eventId
     */
    @PatchMapping("/{eventId}")
    public EventModel updateEventModel(@PathVariable(value = "eventId") Integer eventId, @RequestBody Map<Object, Object> fields) {
        return eventService.updateEventModel(eventId, fields);
    }

}