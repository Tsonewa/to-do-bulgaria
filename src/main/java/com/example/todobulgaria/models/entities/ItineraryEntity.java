package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "itineraries")
public class ItineraryEntity extends BaseEntity implements Serializable {

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
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<AttractionEntity> attractions;
    @ManyToOne
    private TownEntity town;
    @ManyToOne(optional = false)
    private BreakfastPlaceEntity breakfastPlace;
    @ManyToOne(optional = false)
    private CoffeePlaceEntity coffeePlaceEntity;
    @ManyToOne(optional = false)
    private DinnerPlaceEntity dinnerPlaceEntity;
    @ManyToOne(optional = false)
    private HotelEntity hotelEntity;
    @ManyToOne(cascade = CascadeType.ALL)
    private TripEntity trip;

    public ItineraryEntity() {
    }

    public DinnerPlaceEntity getDinnerPlaceEntity() {
        return dinnerPlaceEntity;
    }

    public void setDinnerPlaceEntity(DinnerPlaceEntity dinnerPlaceEntity) {
        this.dinnerPlaceEntity = dinnerPlaceEntity;
    }

    public HotelEntity getHotelEntity() {
        return hotelEntity;
    }

    public void setHotelEntity(HotelEntity hotelEntity) {
        this.hotelEntity = hotelEntity;
    }

    public BreakfastPlaceEntity getBreakfastPlace() {
        return breakfastPlace;
    }

    public CoffeePlaceEntity getCoffeePlaceEntity() {
        return coffeePlaceEntity;
    }

    public void setCoffeePlaceEntity(CoffeePlaceEntity coffeePlaceEntity) {
        this.coffeePlaceEntity = coffeePlaceEntity;
    }

    public void setBreakfastPlace(BreakfastPlaceEntity breakfastPlace) {
        this.breakfastPlace = breakfastPlace;
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

}
