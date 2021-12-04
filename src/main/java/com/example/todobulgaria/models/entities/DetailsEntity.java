package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "details")
public class DetailsEntity extends BaseEntity implements Serializable {

    @Lob
    @Column
    private String equipment;
    @Lob
    @Column
    private String festivals;
    @Lob
    @Column(name = "foto_tips")
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
