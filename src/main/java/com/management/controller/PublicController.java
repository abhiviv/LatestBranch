package com.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.dto.CategoryDto;
import com.management.dto.ImageDto;
import com.management.entity.ImageSearch;
import com.management.entity.ImagesTable;
import com.management.service.CategoryServices;
import com.management.service.ImageServices;

@RestController
@RequestMapping(path = "/public")
public class PublicController {

	@Autowired
	private CategoryServices categoryServices;
	
	@Autowired
	private ImageServices imageServices;
	
	@GetMapping(path = "/getcategory")
	public List<CategoryDto> imageCategories(){
		return categoryServices.getImageCategory();
	}
	
	@GetMapping(path = "/getImageData")
	public Page<ImageDto> getImageData(Pageable p){
		return imageServices.page(p);
	}
	
	@GetMapping(path = "/getImageById/{Id}")
	public ImageDto imageDto(@PathVariable("Id")Long id) {
		return imageServices.imageDto(id);
		
	}
	
	@GetMapping(path = "/getRelatedImg/{Name}")
	public List<ImageDto> relatedImg(@PathVariable("Name")String Name) {
		return imageServices.relatedImage(Name);
		
	}
	
	@GetMapping(path = "/getSearchImageList/{name}")
	public List<ImageSearch> imageSearch(@PathVariable("name")String name) {
		return imageServices.imageSearchs(name);
	}
}
