package com.api.ticketshop.Services;

import com.api.ticketshop.Models.EventModel;
import com.api.ticketshop.Models.PurchaseModel;
import com.api.ticketshop.Models.SeatModel;
import com.api.ticketshop.Repositories.SeatRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SeatService {
    final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<SeatModel> createNewSeat(SeatModel seatModel, int number){

        List<SeatModel> seats = new ArrayList<>();

        for(int i = 0; i < number; i++) {
            SeatModel s = new SeatModel();
            BeanUtils.copyProperties(seatModel, s);
            seats.add(s);
        }

        return (List<SeatModel>) seatRepository.saveAll(seats);

    }

    public List<SeatModel> getAllSeatsByEventID(Integer eventID) {
        return seatRepository.findByEventId(eventID);
    }

    public void deleteSeatByID(Integer id) {
        seatRepository.deleteById(id);
    }

    /**
     * Method to find a seat by its id.
     */
    public Optional<SeatModel> getSeatByID(Integer id) {
        return seatRepository.findById(id);
    }

    public Optional<SeatModel> getSeatModelByEventIdAndId(Integer eventId, Integer seatId){
        return seatRepository.findSeatModelByEventIdAndId(eventId, seatId);
    }


}
