package com.management.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class CategoryImageValidation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long validationid;
	
	public String resolution;
	
	public String imageSize;
	
	public String Extension;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "category_id", referencedColumnName = "categoryId")
	public ImageCategory categoryvalidation;

	public Long getValidationid() {
		return validationid;
	}

	public void setValidationid(Long validationid) {
		this.validationid = validationid;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getImageSize() {
		return imageSize;
	}

	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}

	public ImageCategory getCategoryvalidation() {
		return categoryvalidation;
	}

	public void setCategoryvalidation(ImageCategory categoryvalidation) {
		this.categoryvalidation = categoryvalidation;
	}

	public String getExtension() {
		return Extension;
	}

	public void setExtension(String extension) {
		Extension = extension;
	}
	
	
}
