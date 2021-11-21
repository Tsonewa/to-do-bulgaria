package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;
import com.example.todobulgaria.models.BasePlaceEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "dinner_places")
public class DinnerPlaceEntity extends BasePlaceEntity implements Serializable {

}
