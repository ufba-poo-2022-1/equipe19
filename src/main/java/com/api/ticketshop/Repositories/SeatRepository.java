package com.api.ticketshop.Repositories;

import com.api.ticketshop.Models.SeatModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends CrudRepository<SeatModel, Integer> {
}
