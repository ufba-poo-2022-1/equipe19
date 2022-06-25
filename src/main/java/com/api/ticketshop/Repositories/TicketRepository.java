package com.api.ticketshop.Repositories;

import com.api.ticketshop.Models.TicketModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends CrudRepository<TicketModel, Integer> {
}
