package com.management.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.management.entity.ImagesTable;

@Repository
public interface ImageRepository extends JpaRepository<ImagesTable, Long>{

	
   @Query("SELECT b FROM ImagesTable b INNER JOIN b.category c WHERE c.categoryName IN (:CategoryName)  ORDER BY RANDOM()")
   List<ImagesTable> findTop5ByCategoryCategoryNameIgnoreCase(String CategoryName);

	
}
