package com.api.ticketshop.Models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ticket")
public class TicketModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer seat_id;

    @Column(nullable = false)
    private Integer seat_event_id;

    @Column(nullable = false)
    private Integer purchase_id;

    @Column(nullable = false)
    private Integer purchase_user_id;

    @Column(nullable = false)
    private Integer purchase_user_billing_address_id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String hash;

    @Column(nullable = false)
    private String status;

    public Integer getId() {
        return id;
    }

    public Integer getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(Integer seat_id) {
        this.seat_id = seat_id;
    }

    public Integer getSeat_event_id() {
        return seat_event_id;
    }

    public void setSeat_event_id(Integer seat_event_id) {
        this.seat_event_id = seat_event_id;
    }

    public Integer getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(Integer purchase_id) {
        this.purchase_id = purchase_id;
    }

    public Integer getPurchase_user_id() {
        return purchase_user_id;
    }

    public void setPurchase_user_id(Integer purchase_user_id) {
        this.purchase_user_id = purchase_user_id;
    }

    public Integer getPurchase_user_billing_address_id() {
        return purchase_user_billing_address_id;
    }

    public void setPurchase_user_billing_address_id(Integer purchase_user_billing_address_id) {
        this.purchase_user_billing_address_id = purchase_user_billing_address_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
