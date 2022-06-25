package com.api.ticketshop.Services;

import com.api.ticketshop.Models.SeatModel;
import com.api.ticketshop.Repositories.SeatRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

}
