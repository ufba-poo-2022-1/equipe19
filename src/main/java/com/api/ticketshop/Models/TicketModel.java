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
    private Integer seatId;

    @Column(nullable = false)
    private Integer seatEventId;

    @Column(nullable = false)
    private Integer purchaseId;

    @Column(nullable = false)
    private Integer purchaseUserId;

    @Column(nullable = false)
    private Integer purchaseUserBillingAddressId;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String hash;

    @Column(nullable = false)
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public Integer getSeatEventId() {
        return seatEventId;
    }

    public void setSeatEventId(Integer seatEventId) {
        this.seatEventId = seatEventId;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Integer getPurchaseUserId() {
        return purchaseUserId;
    }

    public void setPurchaseUserId(Integer purchaseUserId) {
        this.purchaseUserId = purchaseUserId;
    }

    public Integer getPurchaseUserBillingAddressId() {
        return purchaseUserBillingAddressId;
    }

    public void setPurchaseUserBillingAddressId(Integer purchaseUserBillingAddressId) {
        this.purchaseUserBillingAddressId = purchaseUserBillingAddressId;
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
