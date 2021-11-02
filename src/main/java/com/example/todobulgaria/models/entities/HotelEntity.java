package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "hotels")
public class HotelEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String url;
    @Column(nullable = false)
    private String address;

    public HotelEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
