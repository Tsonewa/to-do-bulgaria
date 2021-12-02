package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.DetailsEntity;
import com.example.todobulgaria.repositories.DetailsEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DetailsEntityServiceImplTest {

    @Mock
    private DetailsEntityRepository detailsEntityRepositoryMock;

    @InjectMocks
    private DetailsEntityServiceImpl serviceToTest;

    private DetailsEntity detailsEntityTest;
    @BeforeEach
    void setUp() {

        serviceToTest = new DetailsEntityServiceImpl(detailsEntityRepositoryMock);

        detailsEntityTest = new DetailsEntity();

        detailsEntityTest.setId(1L);
        detailsEntityTest.setFestivals("festivals");
        detailsEntityTest.setEquipment("equipment");
        detailsEntityTest.setFotoTips("fototip");
    }

    @Test
    void saveDetailsEntity() {

        when(detailsEntityRepositoryMock.saveAndFlush(any(DetailsEntity.class))).thenReturn(detailsEntityTest);

        DetailsEntity savedDetails = serviceToTest.saveDetailsEntity(detailsEntityTest);

        assertThat(savedDetails).isNotNull();

        verify(detailsEntityRepositoryMock).saveAndFlush(detailsEntityTest);
    }
}