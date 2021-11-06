package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.CategoryEntity;
import com.example.todobulgaria.models.enums.CategoryEnum;
import com.example.todobulgaria.repositories.CategoryRepository;
import com.example.todobulgaria.services.CategoryEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CategoryEntityServiceImpl implements CategoryEntityService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryEntityServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initCategories() {

        if(categoryRepository.count() > 0){
            return;
        }

        Arrays.stream(CategoryEnum.values())
                .forEach(c -> {

                    CategoryEntity categoryEntity = new CategoryEntity();
                    categoryEntity.setName(c);

                    categoryRepository.save(categoryEntity);
                });
    }

    @Override
    public CategoryEntity getCategoryByName(CategoryEnum name) {
        return categoryRepository.getCategoryEntityByName(name);
    }


}
