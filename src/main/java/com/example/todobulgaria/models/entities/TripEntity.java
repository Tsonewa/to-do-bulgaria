package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="trips")
public class TripEntity extends BaseEntity implements Serializable {

    @Column(nullable = false)
    private String startPoint;
    @Column
    private Integer duration;
    @OneToOne(fetch = FetchType.EAGER)
    private PictureEntity picture;
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private CategoryEntity categoryEntity;
    @OneToMany(mappedBy = "trip", targetEntity = ItineraryEntity.class, cascade = CascadeType.ALL)
    private List<ItineraryEntity> itineraries;
    @Lob
    @Column
    private String description;
    @OneToOne
    private DetailsEntity details;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "trip", targetEntity = ReviewEntity.class, cascade = CascadeType.ALL)
    private List<ReviewEntity> reviews;
    @Column
    private Integer rating = 0;

    public TripEntity() {
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public void setPicture(PictureEntity picture) {
        this.picture = picture;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String region) {
        this.startPoint = region;
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
