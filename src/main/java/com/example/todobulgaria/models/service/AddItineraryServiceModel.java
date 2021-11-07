package com.example.todobulgaria.models.service;

import com.example.todobulgaria.models.enums.CategoryEnum;

import java.util.List;

public class AddItineraryServiceModel {

    private String region;
    private CategoryEnum category;
    private String picturesUrl; //TODO fix pictures
    private String townName;
    private String breakfastPlace;
    private String coffeePlace;
    private String hotel;
    private String dinnerPlace;
    private List<String> attractionsName;
    private String description;
    private String equipment;
    private String festivals;
    private String fotoTip;

    public AddItineraryServiceModel() {
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public String getPicturesUrl() {
        return picturesUrl;
    }

    public void setPicturesUrl(String picturesUrl) {
        this.picturesUrl = picturesUrl;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
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

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getDinnerPlace() {
        return dinnerPlace;
    }

    public void setDinnerPlace(String dinnerPlace) {
        this.dinnerPlace = dinnerPlace;
    }

    public List<String> getAttractionsName() {
        return attractionsName;
    }

    public void setAttractionsName(List<String> attractionsName) {
        this.attractionsName = attractionsName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getFestivals() {
        return festivals;
    }

    public void setFestivals(String festivals) {
        this.festivals = festivals;
    }

    public String getFotoTip() {
        return fotoTip;
    }

    public void setFotoTip(String fotoTip) {
        this.fotoTip = fotoTip;
    }
}
