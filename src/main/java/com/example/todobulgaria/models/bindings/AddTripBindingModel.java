package com.example.todobulgaria.models.bindings;

import com.example.todobulgaria.models.enums.CategoryEnum;

import java.util.List;

public class AddTripBindingModel {

    private String region;
    private String categoryName;
    private List<String> picturesUrls; //TODO fix pictures
    private List<String> townName;
    private List<String> breakfastPlace;
    private List<String> coffeePlace;
    private List<String> hotel;
    private List<String> dinnerPlace;
    private List<String> attractionsName;
    private String description;
    private String equipment;
    private String festivals;
    private String fotoTip;

    public AddTripBindingModel() {
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getPicturesUrls() {
        return picturesUrls;
    }

    public void setPicturesUrls(List<String> picturesUrls) {
        this.picturesUrls = picturesUrls;
    }

    public List<String> getTownName() {
        return townName;
    }

    public void setTownName(List<String> townName) {
        this.townName = townName;
    }

    public List<String> getBreakfastPlace() {
        return breakfastPlace;
    }

    public void setBreakfastPlace(List<String> breakfastPlace) {
        this.breakfastPlace = breakfastPlace;
    }

    public List<String> getCoffeePlace() {
        return coffeePlace;
    }

    public void setCoffeePlace(List<String> coffeePlace) {
        this.coffeePlace = coffeePlace;
    }

    public List<String> getHotel() {
        return hotel;
    }

    public void setHotel(List<String> hotel) {
        this.hotel = hotel;
    }

    public List<String> getDinnerPlace() {
        return dinnerPlace;
    }

    public void setDinnerPlace(List<String> dinnerPlace) {
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


