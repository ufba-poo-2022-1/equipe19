package com.api.ticketshop.Services;

import com.api.ticketshop.Models.SeatModel;
import com.api.ticketshop.Repositories.SeatRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Class that contains all the methods to make each endpoint of a Seat
 */
@Service
public class SeatService {
    final SeatRepository seatRepository;

    /**
     * Class constructor that receives the Repository Interface.
     * This constructor is actually a Dependency Injection Point.
     */
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    /**
     * Method to create a list of Seats in the database.
     * @param seatModel, number
     * @return List<SeatModel>
     */
    public List<SeatModel> createNewSeat(SeatModel seatModel, int number){

        List<SeatModel> seats = new ArrayList<>();

        for(int i = 0; i < number; i++) {
            SeatModel s = new SeatModel();
            BeanUtils.copyProperties(seatModel, s);
            seats.add(s);
        }

        return (List<SeatModel>) seatRepository.saveAll(seats);

    }

    /**
     * Method to find all Seats by event's id.
     * @param eventID
     * @return List<SeatModel>
     */
    public List<SeatModel> getAllSeatsByEventID(Integer eventID) {
        return seatRepository.findByEventId(eventID);
    }

    /**
     * Method to delete a Seat its id.
     * @param id
     */
    public void deleteSeatByID(Integer id) {
        seatRepository.deleteById(id);
    }

    /**
     * Method to find a seat by its id.
     * @param id
     * @return Optional<SeatModel>
     */
    public Optional<SeatModel> getSeatByID(Integer id) {
        return seatRepository.findById(id);
    }

    /**
     * Method to find a seat by its id and event's id.
     * @param eventId, seatId
     * @return Optional<SeatModel>
     */
    public Optional<SeatModel> getSeatModelByEventIdAndId(Integer eventId, Integer seatId){
        return seatRepository.findSeatModelByEventIdAndId(eventId, seatId);
    }

}
