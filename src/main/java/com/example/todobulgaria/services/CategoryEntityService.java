package com.example.todobulgaria.services;

import com.example.todobulgaria.models.entities.CategoryEntity;
import com.example.todobulgaria.models.enums.CategoryEnum;

public interface CategoryEntityService {
    void initCategories();

    CategoryEntity getCategoryByName(CategoryEnum name);
}

