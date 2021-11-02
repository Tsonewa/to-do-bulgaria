package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "coffee_shops")
public class CoffeeShopEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String url;
    @Column(nullable = false, unique = true)
    private String location;

    public CoffeeShopEntity() {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
