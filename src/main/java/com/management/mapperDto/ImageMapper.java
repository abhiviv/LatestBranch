package com.management.mapperDto;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.management.dto.ImageDto;
import com.management.entity.ImageCategory;
import com.management.entity.ImagesTable;
import com.management.repository.CategoryRepository;


@Component
public class ImageMapper {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public ImageDto imageDto(ImagesTable imagesTable) {
		ImageDto imageDto =new ImageDto();
		BeanUtils.copyProperties(imagesTable, imageDto);
		imageDto.setCategoryName(imagesTable.getCategory().getCategoryName());
		imageDto.setUploadBy(imagesTable.getRegistration().getName());
		imageDto.setPath("http://localhost:8080/files/resizeImages/"+imagesTable.getCategory().getCategoryName()+"/"+imagesTable.getResizedimension()+"/"+imagesTable.getResizeImages());
		return imageDto;
	}
	
}
