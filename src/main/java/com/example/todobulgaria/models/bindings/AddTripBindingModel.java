package com.example.todobulgaria.models.bindings;

import com.example.todobulgaria.annotations.ValidUrl;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

public class AddTripBindingModel {

    @Size(min = 2, max = 25, message = "Town length must be between 2 and 25 symbols.")
    @NotBlank
    private String startPoint;
    @NotNull(message = "Category can not be empty")
    private String categoryName;
    @ValidUrl
    private MultipartFile url;
    @NotEmpty
    @Valid
    private List<@Size(min = 2, max = 30, message = "Town name must be between 2 and 30 symbols")@NotBlank String> townName;
    @NotEmpty
    private List<@Size(min = 2, max = 30, message = "Breakfast place name must be between 2 and 30 symbols")@NotBlank String> breakfastPlace;
    @NotEmpty
    private List<@Size(min = 2, max = 30,message = "Coffee place name must be between 2 and 30 symbols")@NotBlank String> coffeePlace;
    @NotEmpty
    private List<@Size(min = 2, max = 30,message = "Hotel name must be between 2 and 30 symbols")@NotBlank String> hotel;
    @NotEmpty
    private List<@Size(min = 2, max = 30, message = "Dinner place name must be between 2 and 30 symbols") @NotBlank String> dinnerPlace;
    @NotEmpty
    @Valid
    private List<@Size(min = 2, max = 30, message = "Attraction name must be between 2 and 30 symbols") @NotBlank String> attractionsName;
    private String description;
    private String equipment;
    private String festivals;
    private String fotoTip;

    public AddTripBindingModel() {
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
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


