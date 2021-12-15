package com.management.entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ImagesTable extends Audit<String> {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long imageId;
	
	private String imageDetails;
	
	private String images;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "category_id", nullable = false)
	@JsonIgnore
	private ImageCategory category;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "Id", nullable = false)
	@JsonIgnore
	private Registration registration;

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public String getImageDetails() {
		return imageDetails;
	}

	public void setImageDetails(String imageDetails) {
		this.imageDetails = imageDetails;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public ImageCategory getCategory() {
		return category;
	}

	public void setCategory(ImageCategory category) {
		this.category = category;
	}

	public Registration getRegistration() {
		return registration;
	}

	public void setRegistration(Registration registration) {
		this.registration = registration;
	}
	
	
	
}
