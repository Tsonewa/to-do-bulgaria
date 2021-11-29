package com.example.todobulgaria.services;

import com.example.todobulgaria.models.entities.CategoryEntity;
import com.example.todobulgaria.models.enums.CategoryEnum;

public interface CategoryEntityService {

    CategoryEntity getCategoryByName(CategoryEnum name);
}

