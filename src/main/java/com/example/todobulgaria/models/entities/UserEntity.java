package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity implements Serializable {

    @Column(unique = true, nullable = false)
    private String username;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToOne
    private PictureEntity profilePictureUrl;
    @ManyToMany
    private List<RoleEntity> roles;
    @Column
    private boolean status; //blocked or active
    @OneToMany(mappedBy = "user", targetEntity = TripEntity.class)
    private List<TripEntity> trips;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="user_favourite",
            joinColumns={@JoinColumn(name="userId")},
            inverseJoinColumns={@JoinColumn(name="favouriteId")})
    private Set<TripEntity> favouriteTrips = new HashSet<>();

    public UserEntity() {

    }

    public Set<TripEntity> getFavouriteTrips() {
        return favouriteTrips;
    }

    public void setFavouriteTrips(Set<TripEntity> favouriteTrips) {
        this.favouriteTrips = favouriteTrips;
    }

    public PictureEntity getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(PictureEntity profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public List<TripEntity> getTrips() {
        return trips;
    }

    public void setTrips(List<TripEntity> trips) {
        this.trips = trips;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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


}
