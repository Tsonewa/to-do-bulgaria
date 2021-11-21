package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BasePlaceEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "coffee_places")
public class CoffeePlaceEntity extends BasePlaceEntity implements Serializable {


}
