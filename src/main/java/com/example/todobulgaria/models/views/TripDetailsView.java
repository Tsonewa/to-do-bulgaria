package com.example.todobulgaria.models.views;

import java.util.List;

public class TripDetailsView {

    private Long id;
    private String url;
    private String categoryName;
    private String startPoint;
    private Integer duration;
    private List<ItinerariesDetailsViewModel> itinaries;
    private String description;
    private DetailsEntityViewModel details;

    public TripDetailsView() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
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

    public List<ItinerariesDetailsViewModel> getItinaries() {
        return itinaries;
    }

    public void setItinaries(List<ItinerariesDetailsViewModel> itinaries) {
        this.itinaries = itinaries;
    }
}
