package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "itineraries")
public class ItineraryEntity extends BaseEntity {

    @Column
    private Integer rating;
    @Column(name = "created_on")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate createdOn;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToMany
    @JoinTable(
            name = "itineraries_attractions",
            joinColumns = { @JoinColumn(name = "itineraries_id") },
            inverseJoinColumns = { @JoinColumn(name = "attraction_id") }
    )
    private List<AttractionEntity> attractions;
    @ManyToOne
    private TownEntity town;
    @Column(name = "breakfast_place", nullable = false)
    private String breakfastPlace;
    @Column(name = "coffee_place", nullable = false)
    private String coffeePlace;
    @Column(name = "dinner_place", nullable = false)
    private String dinnerPlace;
    @Column
    private String hotel;
    @OneToMany(mappedBy = "itinerary", targetEntity = ReviewEntity.class)
    private List<ReviewEntity> reviews;
    @OneToMany(mappedBy = "itineraryEntity", targetEntity = PictureEntity.class)
    private List<PictureEntity> pictures;
    @ManyToOne
    private UserEntity user;
    @OneToOne
    private CategoryEntity categoryEntity;

    public ItineraryEntity() {
    }
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public List<AttractionEntity> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<AttractionEntity> attractions) {
        this.attractions = attractions;
    }

    public List<ReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewEntity> reviews) {
        this.reviews = reviews;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
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

    public String getBreakfastPlace() {
        return breakfastPlace;
    }

    public void setBreakfastPlace(String breakfastPlace) {
        this.breakfastPlace = breakfastPlace;
    }

    public String getCoffeePlace() {
        return coffeePlace;
    }

    public void setCoffeePlace(String coffeePlace) {
        this.coffeePlace = coffeePlace;
    }

    public String getDinnerPlace() {
        return dinnerPlace;
    }

    public void setDinnerPlace(String dinnerPlace) {
        this.dinnerPlace = dinnerPlace;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public TownEntity getTown() {
        return town;
    }

    public void setTown(TownEntity town) {
        this.town = town;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
