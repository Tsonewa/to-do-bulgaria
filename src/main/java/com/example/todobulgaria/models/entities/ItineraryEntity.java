package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "inineraries")
public class ItineraryEntity extends BaseEntity {

    @Column(nullable = false)
    private Integer day;
    @Column(name = "created_on")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate createdOn;
    @ManyToOne(targetEntity = TripEntity.class)
    private TripEntity trip;
    @ManyToMany
    @JoinTable(
            name = "itineraries_attractions",
            joinColumns = { @JoinColumn(name = "itineraries_id") },
            inverseJoinColumns = { @JoinColumn(name = "attraction_id") }
    )
    private List<AttractionEntity> attractions;
    @ManyToMany
    @JoinTable(
            name = "itineraries_towns",
            joinColumns = { @JoinColumn(name = "itinerary_id") },
            inverseJoinColumns = { @JoinColumn(name = "town_id") }
    )
    private List<TownEntity> towns;
    @ManyToOne
    private BreakfastPlaceEntity breakfastPlace;
    @ManyToOne
    private CoffeeShopEntity coffeePlace;
    @ManyToOne
    private RestaurantEntity dinnerPlace;
    @ManyToOne
    private HotelEntity hotel;

    public ItineraryEntity() {
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

    public TripEntity getTrip() {
        return trip;
    }

    public void setTrip(TripEntity trip) {
        this.trip = trip;
    }

    public List<AttractionEntity> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<AttractionEntity> attractions) {
        this.attractions = attractions;
    }

    public List<TownEntity> getTowns() {
        return towns;
    }

    public void setTowns(List<TownEntity> towns) {
        this.towns = towns;
    }

    public BreakfastPlaceEntity getBreakfastPlace() {
        return breakfastPlace;
    }

    public void setBreakfastPlace(BreakfastPlaceEntity breakfastPlace) {
        this.breakfastPlace = breakfastPlace;
    }

    public CoffeeShopEntity getCoffeePlace() {
        return coffeePlace;
    }

    public void setCoffeePlace(CoffeeShopEntity coffeePlace) {
        this.coffeePlace = coffeePlace;
    }

    public RestaurantEntity getDinnerPlace() {
        return dinnerPlace;
    }

    public void setDinnerPlace(RestaurantEntity dinnerPlace) {
        this.dinnerPlace = dinnerPlace;
    }

    public HotelEntity getHotel() {
        return hotel;
    }

    public void setHotel(HotelEntity hotel) {
        this.hotel = hotel;
    }
}
