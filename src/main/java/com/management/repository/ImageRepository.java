package com.management.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.entity.ImagesTable;

@Repository
public interface ImageRepository extends JpaRepository<ImagesTable, Long>{

	
   List<ImagesTable> findByCategoryCategoryNameIgnoreCase(String CategoryName);
	
}
