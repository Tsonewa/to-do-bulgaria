package com.example.todobulgaria.models;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@MappedSuperclass
public class BasePlaceEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Column
    private String address;
    @Column(name = "booking_url")
    private String bookingUrl;

    public BasePlaceEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBookingUrl() {
        return bookingUrl;
    }

    public void setBookingUrl(String bookingUrl) {
        this.bookingUrl = bookingUrl;
    }
}
