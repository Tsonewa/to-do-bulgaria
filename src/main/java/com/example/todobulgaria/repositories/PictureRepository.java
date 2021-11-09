package com.example.todobulgaria.repositories;

import com.example.todobulgaria.models.entities.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<PictureEntity, Long> {

    void deleteAllByPublicId(String publicId);
}
