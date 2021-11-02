package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;
import com.example.todobulgaria.models.enums.RoleEnum;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum role;

    public RoleEntity() {
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
