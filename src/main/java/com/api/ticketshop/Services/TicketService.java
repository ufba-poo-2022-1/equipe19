package com.api.ticketshop.Services;

import com.api.ticketshop.Models.TicketModel;
import com.api.ticketshop.Repositories.TicketRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public TicketModel createNewTicket(TicketModel ticketModel) {
        return ticketRepository.save(ticketModel);
    }

    public List<TicketModel> getTicketModelByUserId(Integer userId){
        return ticketRepository.findTicketModelByPurchaseUserId(userId);
    }

    public List<TicketModel> getTicketModelByEventId(Integer eventId){
        return ticketRepository.findTicketModelBySeatEventId(eventId);
    }

    public List<TicketModel> getTicketModelByPurchaseId(Integer purchaseId){
        return ticketRepository.findTicketModelByPurchaseId(purchaseId);
    }

    public Optional<TicketModel> getTicketModelById(Integer ticketId){
        return ticketRepository.findTicketModelById(ticketId);
    }

    public Optional<TicketModel> getTicketModelBySeatId(Integer seatId){
        return ticketRepository.findTicketModelBySeatId(seatId);
    }

    @Transactional
    public void deleteEvent(TicketModel ticketModel) {
        ticketRepository.delete(ticketModel);
    }
}
