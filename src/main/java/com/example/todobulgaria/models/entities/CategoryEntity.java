package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;
import com.example.todobulgaria.models.enums.CategoryEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity implements Serializable {

    @Enumerated(value = EnumType.STRING)
    private CategoryEnum name;

    public CategoryEntity() {
    }

    public CategoryEnum getName() {
        return name;
    }

    public void setName(CategoryEnum name) {
        this.name = name;
    }
}
