package com.example.todobulgaria.models.views;

import java.util.List;

public class TripDetailsView {

    private String url;
    private String categoryName;
    private String townName;
    private Integer duration;
    private List<ItinariesDetailsViewModel> itinaries;
    private String description;
    private DetailsEntityViewModel details;

    public TripDetailsView() {
    }

    public DetailsEntityViewModel getDetails() {
        return details;
    }

    public void setDetails(DetailsEntityViewModel details) {
        this.details = details;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTownName() {
        return townName;
    }



    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<ItinariesDetailsViewModel> getItinaries() {
        return itinaries;
    }

    public void setItinaries(List<ItinariesDetailsViewModel> itinaries) {
        this.itinaries = itinaries;
    }
}