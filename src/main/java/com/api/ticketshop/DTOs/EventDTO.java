package com.api.ticketshop.DTOs;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EventDTO {

    @NotNull @NotEmpty
    private String name;
    @NotNull @NotEmpty
    private String description;
    @NotNull
    private Integer available_seates;
    @NotNull @NotEmpty
    private String date;
    @NotNull @NotEmpty
    private String start_time;
    @NotNull @NotEmpty
    private String finish_time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAvailable_seates() {
        return available_seates;
    }

    public void setAvailable_seates(Integer available_seates) {
        this.available_seates = available_seates;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }
}