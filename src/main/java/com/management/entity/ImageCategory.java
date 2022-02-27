package com.management.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

@Entity
public class ImageCategory extends Audit<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long categoryId;
	
	@NonNull
	@NotBlank
	private String categoryName;
	
	private String categoryDetails;
	
	private String categoryImage;
	
	private Boolean Active = false;
	
	
	@OneToMany(
	        mappedBy = "category",
	        cascade = CascadeType.PERSIST,
	        fetch = FetchType.EAGER)
    private Set<ImagesTable> imagesTables;
	

	
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

	public Boolean getActive() {
		return Active;
	}

	public void setActive(Boolean active) {
		Active = active;
	}

	public Set<ImagesTable> getImagesTables() {
		return imagesTables;
	}

	public void setImagesTables(Set<ImagesTable> imagesTables) {
		this.imagesTables = imagesTables;
	}

	
}
