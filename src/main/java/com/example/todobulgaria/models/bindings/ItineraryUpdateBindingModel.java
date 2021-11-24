package com.example.todobulgaria.models.bindings;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ItineraryUpdateBindingModel {

    private Long id;
    @Size(min = 2, max = 50, message = "Name length must be between 2 and 50 symbols.")
    private String breakfastPlace;
    @Size(max = 100, message = "Address length must be less than 100 symbols.")
    private String breakfastPlaceAddress;
    @Size(max=200, message = "Url length must be less than 200 symbols")
    private String breakfastPlaceBookingUrl;
    @Size(min = 2, max = 50, message = "Name length must be between 2 and 50 symbols.")
    private String coffeePlace;
    @Size(max = 100, message = "Address length must be less than 100 symbols.")
    private String coffeePlaceAddress;
    @Size(max=200, message = "Url length must be less than 200 symbols")
    private String coffeePlaceBookingUrl;
    @Size(min = 2, max = 50, message = "Name length must be between 2 and 50 symbols.")
    private String dinnerPlace;
    @Size(max = 100, message = "Address length must be less than 100 symbols.")
    private String dinnerPlaceAddress;
    @Size(max=200, message = "Url length must be less than 200 symbols")
    private String dinnerPlaceBookingUrl;
    @Size(max = 100, message = "Address length must be less than 100 symbols.")
    private String hotel;
    @Size(max = 100, message = "Address length must be less than 100 symbols.")
    private String hotelAddress;
    @Size(max=200, message = "Url length must be less than 200 symbols")
    private String hotelBookingUrl;
    private Long tripId;

    public ItineraryUpdateBindingModel() {
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBreakfastPlace() {
        return breakfastPlace;
    }

    public void setBreakfastPlace(String breakfastPlace) {
        this.breakfastPlace = breakfastPlace;
    }

    public String getBreakfastPlaceAddress() {
        return breakfastPlaceAddress;
    }

    public void setBreakfastPlaceAddress(String breakfastPlaceAddress) {
        this.breakfastPlaceAddress = breakfastPlaceAddress;
    }

    public String getBreakfastPlaceBookingUrl() {
        return breakfastPlaceBookingUrl;
    }

    public void setBreakfastPlaceBookingUrl(String breakfastPlaceBookingUrl) {
        this.breakfastPlaceBookingUrl = breakfastPlaceBookingUrl;
    }

    public String getCoffeePlace() {
        return coffeePlace;
    }

    public void setCoffeePlace(String coffeePlace) {
        this.coffeePlace = coffeePlace;
    }

    public String getCoffeePlaceAddress() {
        return coffeePlaceAddress;
    }

    public void setCoffeePlaceAddress(String coffeePlaceAddress) {
        this.coffeePlaceAddress = coffeePlaceAddress;
    }

    public String getCoffeePlaceBookingUrl() {
        return coffeePlaceBookingUrl;
    }

    public void setCoffeePlaceBookingUrl(String coffeePlaceBookingUrl) {
        this.coffeePlaceBookingUrl = coffeePlaceBookingUrl;
    }

    public String getDinnerPlace() {
        return dinnerPlace;
    }

    public void setDinnerPlace(String dinnerPlace) {
        this.dinnerPlace = dinnerPlace;
    }

    public String getDinnerPlaceAddress() {
        return dinnerPlaceAddress;
    }

    public void setDinnerPlaceAddress(String dinnerPlaceAddress) {
        this.dinnerPlaceAddress = dinnerPlaceAddress;
    }

    public String getDinnerPlaceBookingUrl() {
        return dinnerPlaceBookingUrl;
    }

    public void setDinnerPlaceBookingUrl(String dinnerPlaceBookingUrl) {
        this.dinnerPlaceBookingUrl = dinnerPlaceBookingUrl;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getHotelBookingUrl() {
        return hotelBookingUrl;
    }

    public void setHotelBookingUrl(String hotelBookingUrl) {
        this.hotelBookingUrl = hotelBookingUrl;
    }
}
