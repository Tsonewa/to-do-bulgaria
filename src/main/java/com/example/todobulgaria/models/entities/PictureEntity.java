package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "pictures")
public class PictureEntity extends BaseEntity {

    @Column
    private String url;
    @ManyToOne(fetch = FetchType.EAGER)
    private ItineraryEntity itineraryEntity;


    public PictureEntity() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ItineraryEntity getItineraryEntity() {
        return itineraryEntity;
    }

    public void setItineraryEntity(ItineraryEntity itineraryEntity) {
        this.itineraryEntity = itineraryEntity;
    }
}
