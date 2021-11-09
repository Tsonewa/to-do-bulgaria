package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "itineraries")
public class ItineraryEntity extends BaseEntity {

    @Column(nullable = false)
    private Integer day;
    @Column(name = "created_on", nullable = false)
//    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate createdOn;
    @ManyToMany
    @JoinTable(
            name = "itineraries_attractions",
            joinColumns = { @JoinColumn(name = "itineraries_id") },
            inverseJoinColumns = { @JoinColumn(name = "attraction_id")}
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
    @Column(nullable = false)
    private String hotel;
    @ManyToOne(cascade = CascadeType.ALL)
    private TripEntity trip;

    public ItineraryEntity() {
    }

    public TripEntity getTrip() {
        return trip;
    }

    public void setTrip(TripEntity trip) {
        this.trip = trip;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
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

    public TownEntity getTown() {
        return town;
    }

    public void setTown(TownEntity town) {
        this.town = town;
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
}
