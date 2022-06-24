package com.api.ticketshop.Services;

import com.api.ticketshop.Repositories.SeatRepository;
import org.springframework.stereotype.Service;

@Service
public class SeatService {
    final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }


}
