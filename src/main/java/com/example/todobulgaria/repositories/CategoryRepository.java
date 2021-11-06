package com.example.todobulgaria.repositories;

import com.example.todobulgaria.models.entities.CategoryEntity;
import com.example.todobulgaria.models.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    CategoryEntity getCategoryEntityByName(CategoryEnum name);
}
