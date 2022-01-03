package com.management.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.management.dto.ImageDto;
import com.management.entity.ImageCategory;
import com.management.entity.ImageSearch;
import com.management.entity.ImagesTable;
import com.management.entity.Registration;
import com.management.mapperDto.ImageMapper;
import com.management.repository.CategoryRepository;
import com.management.repository.ImageRepository;
import com.management.repository.RegistrationRepository;
import com.management.repository.SolrRepository;
import com.management.security.SecurityUtils;

import org.springframework.data.domain.Sort;

@Service
@Transactional
public class ImageServices {
	private static final Logger logger = LoggerFactory.getLogger(ImageServices.class);
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private RegistrationRepository registrationRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ImageMapper imageMapper;
	
	@Autowired
	private RegistrationService registrationService; 
	
	@Autowired
	private SolrRepository solrRepository;
	
	public List<ImagesTable> save(MultipartFile[] files, Long id) {
		Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String loggedInUserEmailId = (String) authentication;
		Registration loginusers = registrationRepository.findByEmailIgnoreCase(loggedInUserEmailId);
        try {
        	ImageCategory imageCategory=categoryRepository.findById(id).get();
        	List<ImagesTable>imagesTable=new ArrayList<>();
        	createDirIfNotExist(imageCategory.getCategoryName());
        	Arrays.asList(files).stream().forEach(file -> {
                byte[] bytes = new byte[0];
                try {
                    bytes = file.getBytes();
                    Files.write(Paths.get(FileUtil.folderPath+"\\" + imageCategory.getCategoryName() + "\\" + file.getOriginalFilename()), bytes);
                    ImagesTable img=new ImagesTable();
                    	img.setImages(file.getOriginalFilename());
                    	img.setRegistration(loginusers);
                    	img.setCategory(imageCategory);
                    	String details=file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf('.'));
                    	details.replace('-', ' ');    
                    	img.setImageDetails(details);
                    	imagesTable.add(img);	
                } catch (IOException e) {
                   logger.error("error", e);
                }
            });
        	imageRepository.saveAll(imagesTable);
        	imagesTable.forEach(im->{
        		ImageSearch image=new  ImageSearch();
            	image.setImageid(im.getImageId());
            	image.setImageName(im.getImages());
            	image.setImageDescription(im.getImageDetails());
            	image.setCategory(imageCategory.getCategoryName());
            	solrRepository.save(image);
        	});
        	

            return imagesTable;
		} catch (Exception e) {
			 logger.error("error", e);
		}
		
		return null;
		
	}
	
	private void createDirIfNotExist(String string) {
        File directory = new File(FileUtil.folderPath+"\\"+string+"\\");
        if (! directory.exists()){
            directory.mkdirs();
        }
    }
	
	
	public Page<ImageDto>page(Pageable pageable){
		Page<ImageDto>imageDto= imageRepository.findAll(pageable).map(imageMapper::imageDto);
		return imageDto;
	}
	
	public List<ImageDto> imagesTables(){
		return imageRepository.findAll(Sort.by(Sort.Direction.DESC, "imageId")).stream()
				.filter(id->id.getRegistration().getId()==registrationService.getuserdetails().getId())
				.map(imageMapper::imageDto).collect(Collectors.toList());
	}
	
	
	public ImagesTable upadateimagesTable(ImagesTable imagesTable) {
		ImagesTable imagesTable2=imageRepository.findById(imagesTable.getImageId()).get();
		cpimagedata(imagesTable, imagesTable2);
		SolrThread solrThread=new  SolrThread(solrRepository,imagesTable2.getImageId(),imagesTable2.getImages(),imagesTable2.getImageDetails(),imagesTable2.getCategory().getCategoryName());
		Thread t1=new Thread(solrThread);
		t1.start();
		return imageRepository.save(imagesTable2);
		
	}
	public void cpimagedata(ImagesTable newImagesTable,ImagesTable existingImagesTable) {
		existingImagesTable.setImageDetails(newImagesTable.getImageDetails());
	}
	
	public ImageDto imageDto(Long Id) {
		ImagesTable imagesTable=imageRepository.findById(Id).get();
		return imageMapper.imageDto(imagesTable);
	}
	
	public List<ImageDto> relatedImage(String CategoryName) {
		List<ImagesTable> imagesTable= imageRepository.findByCategoryCategoryNameIgnoreCase(CategoryName);
		return imagesTable.stream().map(imageMapper::imageDto).collect(Collectors.toList());
	}
	
	public List<ImageSearch> imageSearchs(String Name){
		return solrRepository.findByCategoryStartingWith(Name);
	}
}
