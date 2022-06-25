package com.api.ticketshop.Services;


import com.api.ticketshop.Models.TicketModel;
import com.api.ticketshop.Repositories.TicketRepository;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public TicketModel createNewTicket(TicketModel ticketModel) {
        return ticketRepository.save(ticketModel);
    }


}
