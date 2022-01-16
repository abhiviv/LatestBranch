package com.management.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection="Image")
public class ImageSearch {

	
	@Id
	@Indexed(name = "id", type = "long")
	private Long imageid;

	@Indexed(name = "iname", type = "string")
	private String imageName;

	@Indexed(name = "idesc", type = "string")
	private String ImageDescription;
	
	@Indexed(name = "category",type = "string")
    private String category;
	
	@Indexed(name = "dimension",type = "string")
    private String dimension;
	
	public Long getImageid() {
		return imageid;
	}

	public void setImageid(Long imageid) {
		this.imageid = imageid;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageDescription() {
		return ImageDescription;
	}

	public void setImageDescription(String imageDescription) {
		ImageDescription = imageDescription;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	

}
