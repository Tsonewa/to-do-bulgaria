package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "regions")
public class RegionEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "region", targetEntity = TownEntity.class)
    private List<TownEntity> towns;

    public RegionEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
