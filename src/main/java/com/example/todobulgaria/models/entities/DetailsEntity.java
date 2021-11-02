package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "details")
public class DetailsEntity extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String equipment;
    @Column(columnDefinition = "TEXT")
    private String festivals;
    @Column(name = "foto_tips",columnDefinition = "TEXT")
    private String fotoTips;

    public DetailsEntity() {
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

    public String getFotoTips() {
        return fotoTips;
    }

    public void setFotoTips(String fotoTips) {
        this.fotoTips = fotoTips;
    }
}
