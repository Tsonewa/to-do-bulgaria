package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.exceptions.CategoryNotFoundException;
import com.example.todobulgaria.models.entities.CategoryEntity;
import com.example.todobulgaria.models.enums.CategoryEnum;
import com.example.todobulgaria.repositories.CategoryRepository;
import com.example.todobulgaria.services.CategoryEntityService;
import org.springframework.stereotype.Service;

@Service
public class CategoryEntityServiceImpl implements CategoryEntityService {

    private final CategoryRepository categoryRepository;

    public CategoryEntityServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }

    @Override
    public CategoryEntity getCategoryByName(CategoryEnum name) {
        return categoryRepository.getCategoryEntityByName(name)
                .orElseThrow(() -> new CategoryNotFoundException(name));
    }


}
