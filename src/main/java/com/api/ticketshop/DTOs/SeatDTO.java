package com.api.ticketshop.DTOs;

import javax.validation.constraints.NotNull;

public class SeatDTO {

    @NotNull
    private Integer eventID;

    @NotNull
    private String name;

    @NotNull
    private String status;

    @NotNull
    private Integer price;

    public Integer getEventID() {
        return eventID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
