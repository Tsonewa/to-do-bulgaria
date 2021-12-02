package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.exceptions.CategoryNotFoundException;
import com.example.todobulgaria.models.entities.CategoryEntity;
import com.example.todobulgaria.models.enums.CategoryEnum;
import com.example.todobulgaria.repositories.CategoryRepository;
import io.micrometer.core.instrument.config.validate.Validated;
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
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryEntityServiceImplTest {

    @Mock
    private CategoryRepository categoryRepositoryMock;
    @InjectMocks
    private CategoryEntityServiceImpl serviceToTest;

    private CategoryEntity categoryEntityTest;

    @BeforeEach
    void setUp() {

        serviceToTest = new CategoryEntityServiceImpl(categoryRepositoryMock);

        categoryEntityTest = new CategoryEntity();
        categoryEntityTest.setName(CategoryEnum.CITY);
        categoryEntityTest.setId(1L);

    }

    @DisplayName("Find existing category by name")
    @Test
    void getCategoryByName() {

        when(categoryRepositoryMock.getCategoryEntityByName(any(CategoryEnum.class))).thenReturn(Optional.of(categoryEntityTest));

        CategoryEntity categoryByName = serviceToTest.getCategoryByName(categoryEntityTest.getName());

        verify(categoryRepositoryMock).getCategoryEntityByName(categoryEntityTest.getName());

        assertThat(categoryByName).isNotNull();
    }

    @DisplayName("Unexciting category by name - throw exception")
    @Test
    void getCategoryByNameDoesntExist() {

        when(categoryRepositoryMock.getCategoryEntityByName(CategoryEnum.CITY)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, ()
                -> serviceToTest.getCategoryByName(CategoryEnum.CITY));
    }
}