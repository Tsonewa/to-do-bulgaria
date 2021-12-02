package com.example.todobulgaria.services;

import com.example.todobulgaria.models.entities.AttractionEntity;

public interface AttractionEntityService {

    AttractionEntity findAttractionByName(String name);

    boolean attractionExistByName(String name);

    AttractionEntity saveAttraction(AttractionEntity attraction);
}


