package com.example.todobulgaria.models.dto;

import com.example.todobulgaria.models.enums.CategoryEnum;

import java.util.List;

public class AddItineraryDto {

    private String breakfastPlace;
    private String coffeePlace;
    private String dinnerPlace;
    private String hotel;
    private String town;
    private CategoryEnum category;
    private List<AddAttractionDto> addAttractionDtoList;

    public AddItineraryDto() {
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

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public List<AddAttractionDto> getAddAttractionDtoList() {
        return addAttractionDtoList;
    }

    public void setAddAttractionDtoList(List<AddAttractionDto> addAttractionDtoList) {
        this.addAttractionDtoList = addAttractionDtoList;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }
}
