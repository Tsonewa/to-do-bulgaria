package com.example.todobulgaria.services;

import com.example.todobulgaria.models.entities.RoleEntity;
import com.example.todobulgaria.models.enums.RoleEnum;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;

public interface RoleEntityService {
    void initRoles();

    RoleEntity findByName(RoleEnum user) throws RoleNotFoundException;
}


