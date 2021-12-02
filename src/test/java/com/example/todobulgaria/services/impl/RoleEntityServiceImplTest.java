package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.RoleEntity;
import com.example.todobulgaria.models.enums.RoleEnum;
import com.example.todobulgaria.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;

import static com.example.todobulgaria.models.enums.RoleEnum.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleEntityServiceImplTest {

    @Mock
    private RoleRepository roleRepositoryMock;

    @InjectMocks
    private RoleEntityServiceImpl serviceToTest;

    private RoleEntity roleEntityTest;

    @BeforeEach
    void setUp() {

        serviceToTest = new RoleEntityServiceImpl(roleRepositoryMock);

        roleEntityTest = new RoleEntity();
        roleEntityTest.setRole(USER);

    }

    @DisplayName("Find existing role")
    @Test
    void findByName() throws RoleNotFoundException {

        when(roleRepositoryMock.findByRole(roleEntityTest.getRole())).thenReturn(Optional.of(roleEntityTest));

        RoleEntity byName = serviceToTest.findByName(roleEntityTest.getRole());

        verify(roleRepositoryMock).findByRole(roleEntityTest.getRole());

        assertThat(byName).isNotNull();
    }

    @DisplayName("Unexisting role - throw exception")
    @Test
    void findByNameDoesntExist(){

        when(roleRepositoryMock.findByRole(USER)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, ()
                -> serviceToTest.findByName(USER));

    }
}