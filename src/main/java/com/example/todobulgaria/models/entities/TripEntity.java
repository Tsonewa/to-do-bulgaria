package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "trips")
public class TripEntity extends BaseEntity {

    @Column(nullable = false)
    private Integer duration;
    @Column
    private Integer rating;
    @OneToMany(mappedBy = "trip", targetEntity = ReviewEntity.class)
    private List<ReviewEntity> reviews;
    @OneToOne
    private RegionEntity region;
    @OneToMany(mappedBy = "trip", targetEntity = PictureEntity.class)
    private List<PictureEntity> pictures;
    @ManyToOne
    private UserEntity user;
    @OneToOne
    private CategoryEntity categoryEntity;
    @OneToMany(mappedBy = "trip", targetEntity = ItineraryEntity.class)
    private List<ItineraryEntity> itinerary;
    @OneToOne
    private DetailsEntity details;

    public TripEntity() {
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public List<ReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewEntity> reviews) {
        this.reviews = reviews;
    }

    public RegionEntity getRegion() {
        return region;
    }

    public void setRegion(RegionEntity region) {
        this.region = region;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public CategoryEntity getCategory() {
        return categoryEntity;
    }

    public void setCategory(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public List<ItineraryEntity> getItinerary() {
        return itinerary;
    }

    public void setItinerary(List<ItineraryEntity> itinerary) {
        this.itinerary = itinerary;
    }

    public DetailsEntity getDetails() {
        return details;
    }

    public void setDetails(DetailsEntity details) {
        this.details = details;
    }
}
