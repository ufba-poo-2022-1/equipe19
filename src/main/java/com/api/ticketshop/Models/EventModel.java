package com.api.ticketshop.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
@Entity
@Table(name = "event")
public class EventModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Integer available_seates;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private LocalTime start_time;
    @Column(nullable = false)
    private LocalTime finish_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalTime start_time) {
        this.start_time = start_time;
    }

    public LocalTime getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(LocalTime finish_time) {
        this.finish_time = finish_time;
    }
}
