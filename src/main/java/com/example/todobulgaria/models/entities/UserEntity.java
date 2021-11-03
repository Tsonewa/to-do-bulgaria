package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<RoleEntity> roles;
    @Column
    private boolean status; //blocked or active
    @OneToMany(mappedBy = "user", targetEntity = TripEntity.class)
    private List<TripEntity> trips;

    public UserEntity() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<TripEntity> getTrips() {
        return trips;
    }

    public void setTrips(List<TripEntity> trips) {
        this.trips = trips;
    }
}
