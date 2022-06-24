package com.api.ticketshop.Models;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "seat")
public class SeatModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventModel event;

    public Integer getId() {
        return id;
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

    public EventModel getEvent() {
        return event;
    }

    public void setEvent(EventModel event) {
        this.event = event;
    }
}
