package com.api.ticketshop.Repositories;

import com.api.ticketshop.Models.SeatModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends CrudRepository<SeatModel, Integer> {

    List<SeatModel> findByEventId(Integer eventID);

    Optional<SeatModel> findSeatModelByEventIdAndId(Integer eventId, Integer seatId);

}
