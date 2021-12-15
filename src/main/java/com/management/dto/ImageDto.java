package com.management.dto;

public class ImageDto {

	
	private Long imageId;
    private String imageDetails;
	private String images;
	private String categoryName;
	private String UploadBy;
	
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
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getUploadBy() {
		return UploadBy;
	}
	public void setUploadBy(String uploadBy) {
		UploadBy = uploadBy;
	}
	
	
	
}
