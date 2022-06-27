package com.api.ticketshop.Services;

import com.api.ticketshop.Models.TicketModel;
import com.api.ticketshop.Repositories.TicketRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Class that contains all the methods to make each endpoint of a Ticket
 */
@Service
public class TicketService {

    final TicketRepository ticketRepository;

    /**
     * Class constructor that receives the Repository Interface.
     * This constructor is actually a Dependency Injection Point.
     */
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    /**
     * Method to create a Ticket in the database.
     * @param ticketModel
     * @return TicketModel
     */
    @Transactional
    public TicketModel createNewTicket(TicketModel ticketModel) {
        return ticketRepository.save(ticketModel);
    }

    /**
     * Method to list all Tickets by user's id.
     * @param userId
     * @return List<TicketModel>
     */
    public List<TicketModel> getTicketModelByUserId(Integer userId){
        return ticketRepository.findTicketModelByPurchaseUserId(userId);
    }

    /**
     * Method to list all Tickets by event's id.
     * @param eventId
     * @return List<TicketModel>
     */
    public List<TicketModel> getTicketModelByEventId(Integer eventId){
        return ticketRepository.findTicketModelBySeatEventId(eventId);
    }

    /**
     * Method to list all Tickets by purchase's id.
     * @param purchaseId
     * @return List<TicketModel>
     */
    public List<TicketModel> getTicketModelByPurchaseId(Integer purchaseId){
        return ticketRepository.findTicketModelByPurchaseId(purchaseId);
    }

    /**
     * Method to find a Ticket by its id.
     * @param ticketId
     * @return Optional<TicketModel>
     */
    public Optional<TicketModel> getTicketModelById(Integer ticketId){
        return ticketRepository.findTicketModelById(ticketId);
    }

    /**
     * Method to find a Ticket by seat's id.
     * @param seatId
     * @return Optional<TicketModel>
     */
    public Optional<TicketModel> getTicketModelBySeatId(Integer seatId){
        return ticketRepository.findTicketModelBySeatId(seatId);
    }

    /**
     * Method to delete a specific Ticket.
     * @param ticketModel
     */
    @Transactional
    public void deleteTicket(TicketModel ticketModel) {
        ticketRepository.delete(ticketModel);
    }

    /**
     * Method to update a Ticket by its id.
     * @param ticketModelId
     * @return TicketModel
     */
    @Transactional
    public TicketModel updateTicketModel(Integer ticketModelId, Map<Object, Object> fields){
        TicketModel ticketModel = ticketRepository
                .findById(ticketModelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No tickets with this id"));

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(TicketModel.class, key.toString());
            assert field != null;
            field.setAccessible(true);
            ReflectionUtils.setField(field, ticketModel, value);
        });

        return ticketRepository.save(ticketModel);
    }
}