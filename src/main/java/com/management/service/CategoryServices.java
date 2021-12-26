package com.management.service;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.management.dto.CategoryDto;
import com.management.entity.ImageCategory;
import com.management.mapperDto.RegistrationDtomapper;
import com.management.repository.CategoryRepository;


@Service
@Transactional
public class CategoryServices {
	private static final Logger logger = LoggerFactory.getLogger(CategoryServices.class);
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private RegistrationDtomapper registrationDtomapper;
	
	@Autowired
	private MessageSource messageSource;

	public ImageCategory saveImageCategory(ImageCategory imageCategory) {
		ImageCategory imageCategory2=categoryRepository.findByCategoryNameIgnoreCase(imageCategory.getCategoryName());
		if(imageCategory2!=null) {
			throw new com.management.exception.CustomeException(messageSource.getMessage("api.error.category.allready", null, Locale.ENGLISH), "",
					"Enter different ", "then click Submit", "if and issued contact us");
		}
		return categoryRepository.save(imageCategory);	
	}
	
	public ImageCategory UpdateImagecategory(ImageCategory imageCategory) {
		ImageCategory imageCategory2=categoryRepository.findById(imageCategory.getCategoryId()).get();
		if(imageCategory2!=null) {
			copyData(imageCategory,imageCategory2);
			return categoryRepository.save(imageCategory2);	
		}else {
			throw new com.management.exception.CustomeException("Id not available", "Use different id",
					"Enter different Id", "", "");
		}
		
	}
	
	private void copyData(ImageCategory imageCategory, ImageCategory existing) {
		existing.setCategoryDetails(imageCategory.getCategoryDetails());
//		existing.setCategoryName(imageCategory.getCategoryName());
	}
	
	
	public ResponseEntity<String> uploadToLocalFile(MultipartFile file,Long id) throws IOException, InterruptedException {
		ImageCategory category=categoryRepository.findById(id).get();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		 File folder=new File("C:\\temp\\"+category.getCategoryName()+"\\");
         folder.mkdirs();
		 Path path = Paths.get("C:\\temp\\"+category.getCategoryName()+"\\"+ file.getOriginalFilename());
		 if(fileName.equals("")) {
			 return ResponseEntity.ok("save");
		 }
		    category.setCategoryImage(fileName);
		    categoryRepository.save(category);
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//			String path1="C:\\temp\\"+category.getCategoryName()+"\\"+ file.getOriginalFilename();
//			ImageResize imageResize=new ImageResize(path1);
//			Thread t1=new Thread(imageResize);
//			t1.start();
		} catch (IOException e) {
			logger.error("Invalid Size:",e.getMessage());
			e.printStackTrace();
		}
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/files/download/")
				.path(fileName)
				.toUriString();
		return ResponseEntity.ok(fileDownloadUri);
	}
	
	public ObjectNode validateImage(MultipartFile file) {
		ObjectMapper mapper  = new ObjectMapper();
		ObjectNode objectNode = mapper.createObjectNode();
		objectNode.put("SUCCESS", "IMAGE");
		ObjectNode objectNode1 = mapper.createObjectNode();
		objectNode1.put("FAIL", "NOT AN IMAGE");
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	    String extname = FilenameUtils.getExtension(fileName);//File extension
        String allowImgFormat = "jpg,jpeg";
        if (!allowImgFormat.contains(extname.toLowerCase())) {
        	return objectNode1;
        }
		return objectNode;
	}
	
	public CategoryDto cImageCategory(Long id) throws Exception {
		ImageCategory category=categoryRepository.findById(id).get();
	  	if(category==null) {
	  		throw new com.management.exception.CustomeException("Id not available", "Use different id",
					"Enter different Id", "", "");
	  	}
	return registrationDtomapper.categoryDtoconvert(category);
	
	}
	
	public List<CategoryDto> getImageCategory() {
		return categoryRepository.findAll().stream().map(registrationDtomapper::categoryDto).collect(Collectors.toList());
	}
}