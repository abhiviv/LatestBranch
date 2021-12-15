package com.management.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.management.dto.CategoryDto;
import com.management.entity.ImageCategory;
import com.management.service.CategoryServices;

@RestController
@RequestMapping(path = "/web/category")
public class CategoryController {

	@Autowired
	private CategoryServices categoryService;
	
	@PostMapping(path = "/addCategory")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ImageCategory category(@RequestBody ImageCategory imageCategory) {
		return categoryService.saveImageCategory(imageCategory);
	}
	
	@PostMapping(path = "/updateCategory")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ImageCategory update(@RequestBody ImageCategory imageCategory) {
		return categoryService.UpdateImagecategory(imageCategory);
	}
	
	@GetMapping(path = "/getcategory")
	public List<CategoryDto> imageCategories(){
		return categoryService.getImageCategory();
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(path = "/categoryImgUpload/{id}",consumes = { "multipart/form-data" })
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public  ResponseEntity uploadImg(@RequestParam("file") MultipartFile file,@PathVariable("id")Long id) throws IOException, InterruptedException {
		return categoryService.uploadToLocalFile(file,id);
	}
	
	@GetMapping(path = "/getcategoryById/{id}")
	public CategoryDto imageCategory(@PathVariable("id")Long id) throws Exception {
		return categoryService.cImageCategory(id);
	}
	
	@PostMapping(path = "/validateImage",consumes = {"multipart/form-data"})
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	private ObjectNode validateImage(@RequestParam("file") MultipartFile file) {
		CategoryServices categoryServices=new CategoryServices();
		return categoryServices.validateImage(file);
	} 
}

