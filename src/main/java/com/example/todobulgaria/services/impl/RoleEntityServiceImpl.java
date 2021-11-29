package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.RoleEntity;
import com.example.todobulgaria.models.enums.RoleEnum;
import com.example.todobulgaria.repositories.RoleRepository;
import com.example.todobulgaria.services.RoleEntityService;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Arrays;

@Service
public class RoleEntityServiceImpl implements RoleEntityService {

    private final RoleRepository roleRepository;

    public RoleEntityServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleEntity findByName(RoleEnum role) throws RoleNotFoundException {

        return roleRepository.findByRole(role)
                .orElseThrow(() -> new RoleNotFoundException("Role " + role + " does not exist!"));
    }
}
