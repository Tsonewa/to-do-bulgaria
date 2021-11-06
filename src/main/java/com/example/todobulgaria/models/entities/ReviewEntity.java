package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;
import org.apache.catalina.User;
import org.springframework.format.annotation.DateTimeFormat;

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
    @Column(name = "user_id" ,nullable = false)
    private Long userId;
    @Column(name = "parent_id")
    private Long parentId;
    @ManyToOne
    private ItineraryEntity itinerary;

    public ReviewEntity() {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public ItineraryEntity getItinerary() {
        return itinerary;
    }

    public void setItinerary(ItineraryEntity itinerary) {
        this.itinerary = itinerary;
    }
}
