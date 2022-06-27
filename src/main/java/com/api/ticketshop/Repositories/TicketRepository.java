package com.api.ticketshop.Repositories;

import com.api.ticketshop.Models.TicketModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends CrudRepository<TicketModel, Integer> {
    List<TicketModel> findTicketModelByPurchaseUserId(Integer userId);
    List<TicketModel> findTicketModelBySeatEventId(Integer eventId);
    List<TicketModel> findTicketModelByPurchaseId(Integer purchaseId);
    Optional<TicketModel> findTicketModelById(Integer ticketId);

    Optional<TicketModel> findTicketModelBySeatId(Integer seatId);

}
