package com.example.todobulgaria.models.service;

public class ItineraryUpdateServiceModel {

    private Long id;
    private String breakfastPlace;
    private String breakfastPlaceAddress;
    private String breakfastPlaceBookingUrl;
    private String coffeePlace;
    private String coffeePlaceAddress;
    private String coffeePlaceBookingUrl;
    private String dinnerPlace;
    private String dinnerPlaceAddress;
    private String dinnerPlaceBookingUrl;
    private String hotel;
    private String hotelAddress;
    private String hotelBookingUrl;
    private Long tripId;

    public ItineraryUpdateServiceModel() {
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
