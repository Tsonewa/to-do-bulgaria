package com.example.todobulgaria.models.service;

import com.example.todobulgaria.models.enums.CategoryEnum;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class AddTripServiceModel {

    private String startPoint;
    private CategoryEnum categoryName;
    private MultipartFile url;
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
    private String creator;

    public AddTripServiceModel() {
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public CategoryEnum getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryEnum categoryName) {
        this.categoryName = categoryName;
    }

    public MultipartFile getUrl() {
        return url;
    }

    public void setUrl(MultipartFile url) {
        this.url = url;
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
