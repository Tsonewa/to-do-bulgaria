package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "favourites")
public class FavouriteTripEntity extends BaseEntity {

    @ManyToMany
    private Set<UserEntity> userFavourites = new HashSet<>();

    public FavouriteTripEntity() {
    }

    public Set<UserEntity> getUserFavourites() {
        return userFavourites;
    }

    public void setUserFavourites(Set<UserEntity> userFavourites) {
        this.userFavourites = userFavourites;
    }
}
