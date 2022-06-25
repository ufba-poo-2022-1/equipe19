package com.api.ticketshop.Controllers;

import com.api.ticketshop.DTOs.TicketDTO;
import com.api.ticketshop.Models.PurchaseModel;
import com.api.ticketshop.Models.SeatModel;
import com.api.ticketshop.Models.TicketModel;
import com.api.ticketshop.Services.EventService;
import com.api.ticketshop.Services.PurchaseService;
import com.api.ticketshop.Services.SeatService;
import com.api.ticketshop.Services.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/tickets")
public class TicketController {

    final TicketService ticketService;
    final SeatService seatService;

    final EventService eventService;

    final PurchaseService purchaseService;

    public TicketController(TicketService ticketService, SeatService seatService, EventService eventService, PurchaseService purchaseService) {
        this.ticketService = ticketService;
        this.seatService = seatService;
        this.eventService = eventService;
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<Object> newTicket(@RequestBody @Valid TicketDTO ticketDTO){
        TicketModel ticketModel = new TicketModel();

        Optional<SeatModel> seatModelOptional = seatService.getSeatModelByEventIdAndId(ticketDTO.getSeat_event_id(), ticketDTO.getSeat_id());
        Optional<PurchaseModel> purchaseModelOptional = purchaseService.getPurchaseModelByUserIdAndUserBillingAddressIdAndId(ticketDTO.getPurchase_user_id(), ticketDTO.getPurchase_user_billing_address_id(), ticketDTO.getPurchase_id());

        if(seatModelOptional.isPresent() && purchaseModelOptional.isPresent()) {
            ticketModel.setSeat_id(ticketDTO.getSeat_id());
            ticketModel.setSeat_event_id(ticketDTO.getSeat_event_id());
            ticketModel.setPurchase_id(ticketDTO.getPurchase_id());
            ticketModel.setPurchase_user_id(ticketDTO.getPurchase_user_id());
            ticketModel.setPurchase_user_billing_address_id(ticketDTO.getPurchase_user_billing_address_id());
            ticketModel.setType(ticketDTO.getType());
            ticketModel.setHash(UUID.randomUUID().toString());
            ticketModel.setStatus(ticketDTO.getStatus());

            return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.createNewTicket(ticketModel));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found with the given input");

    }
}
