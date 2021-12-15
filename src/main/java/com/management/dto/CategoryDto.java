package com.management.dto;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CategoryDto {
	
    private Long categoryId;
	
	private String categoryName;
	
	private String categoryDetails;
	
	private String categoryImage;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime lastModifiedDate;
	
	private Boolean Active;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDetails() {
		return categoryDetails;
	}

	public void setCategoryDetails(String categoryDetails) {
		this.categoryDetails = categoryDetails;
	}

	public String getCategoryImage() {
		return categoryImage;
	}

	public void setCategoryImage(String categoryImage) {
		this.categoryImage = categoryImage;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Boolean getActive() {
		return Active;
	}

	public void setActive(Boolean active) {
		Active = active;
	}
	 
	
}
