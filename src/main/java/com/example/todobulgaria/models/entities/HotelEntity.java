package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BasePlaceEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "hotels")
public class HotelEntity extends BasePlaceEntity implements Serializable {

}
