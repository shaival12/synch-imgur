package com.sync.imgur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sync.imgur.model.ImageEntity;
 
@Repository
public interface ImageRepository
        extends JpaRepository<ImageEntity, Long> {

	List<ImageEntity> findImageByUserId(Long id);
 
}
