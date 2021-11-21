package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;


@Entity
@Table(name = "reviews")
public class ReviewEntity extends BaseEntity {

    @Column(nullable = false)
    private String comment;
    @Column(name = "created_on", nullable = false)
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate createdOn;
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private TripEntity trip;
    @Column
    private boolean approved;

    public ReviewEntity() {
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public TripEntity getTrip() {
        return trip;
    }

    public void setTrip(TripEntity trip) {
        this.trip = trip;
    }


}

