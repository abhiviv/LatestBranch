package com.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.entity.CategoryImageValidation;

@Repository
public interface ImageValidationRepo extends JpaRepository<CategoryImageValidation, Long> {

}
