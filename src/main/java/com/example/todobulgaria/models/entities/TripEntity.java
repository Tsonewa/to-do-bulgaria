package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="trips")
public class TripEntity extends BaseEntity {

    @Column(nullable = false)
    private String region;
    @Column
    private Integer duration;
    @OneToMany(mappedBy = "trip", targetEntity = PictureEntity.class)
    private List<PictureEntity> pictures;
    @ManyToOne
    private UserEntity user;
    @OneToOne
    private CategoryEntity categoryEntity;
    @OneToMany(mappedBy = "trip", targetEntity = ItineraryEntity.class)
    private List<ItineraryEntity> itineraries;
    @Column(columnDefinition = "TEXT")
    private String description;
    @OneToOne
    private DetailsEntity details;
    @OneToMany(mappedBy = "trip", targetEntity = ReviewEntity.class)
    private List<ReviewEntity> reviews;

    public TripEntity() {
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<PictureEntity> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureEntity> pictures) {
        this.pictures = pictures;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public List<ItineraryEntity> getItineraries() {
        return itineraries;
    }

    public void setItineraries(List<ItineraryEntity> itineraries) {
        this.itineraries = itineraries;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DetailsEntity getDetails() {
        return details;
    }

    public void setDetails(DetailsEntity details) {
        this.details = details;
    }

    public List<ReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewEntity> reviews) {
        this.reviews = reviews;
    }
}
