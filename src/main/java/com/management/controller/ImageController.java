package com.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.management.dto.ImageDto;
import com.management.entity.ImagesTable;
import com.management.service.ImageServices;

@RestController
@RequestMapping(path = "/web/imageData")
public class ImageController {

	@Autowired
	private ImageServices imageServices;
	
	
	@PostMapping(path = "/saveImageData",consumes = {"multipart/form-data"})
	public List<ImagesTable> save(@RequestParam("files") MultipartFile[] files,@RequestParam("id") Long id){
		return imageServices.save(files,id);
		
	}
	
	
	@GetMapping(path = "/getAllImage")
	public List<ImageDto> imagesTables(){
		return imageServices.imagesTables();
	}
	
	@PostMapping(path = "/UpdateImageDetails")
	public ImagesTable imagesTable(@RequestBody ImagesTable imagesTable) {
		return imageServices.upadateimagesTable(imagesTable);
	}
}
