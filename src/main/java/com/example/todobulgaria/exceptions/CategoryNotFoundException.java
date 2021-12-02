package com.example.todobulgaria.exceptions;

import com.example.todobulgaria.models.enums.CategoryEnum;

public class CategoryNotFoundException extends RuntimeException{

    private final CategoryEnum categoryEnum;

    public CategoryNotFoundException(CategoryEnum categoryEnum) {
        super("Category " + categoryEnum + " does not exist");
        this.categoryEnum = categoryEnum;
    }

    public CategoryEnum getCategoryEnum() {
        return categoryEnum;
    }

}
