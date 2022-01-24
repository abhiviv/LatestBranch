package com.management.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.management.entity.ImagesTable;
import com.management.repository.ImageRepository;


public class ImageResize extends Thread  {
	
	private static final Logger logger = LoggerFactory.getLogger(ImageResize.class);

	private String PATH;

    private String imageFolder;

    private Integer imageSize=1000;
	
    private Long id;
    
    @Autowired
    private ImageRepository imageRepository;
    
	public ImageResize(ImageRepository imageRepository,String pATH, String imgpath,Long ids) {
		PATH = pATH;
		imageFolder=imgpath;
		id=ids;
		this.imageRepository = imageRepository;
	}
//resize images
	@Override
	public synchronized void run() {
		try {
			Thread.sleep(1000);
			try {
				File sourceFile=new File(PATH);
	            BufferedImage bufferedImage = ImageIO.read(sourceFile);
	            BufferedImage outputImage = Scalr.resize(bufferedImage, imageSize);
	            String newFileName = FilenameUtils.getBaseName(sourceFile.getName())
	                    + "_" + imageSize.toString() + "."
	                    + FilenameUtils.getExtension(sourceFile.getName());
	            Path path = Paths.get(imageFolder,newFileName);
	            File newImageFile = path.toFile();
	            newImageFile.mkdirs();
	            ImageIO.write(outputImage, "jpg", newImageFile);
	            outputImage.flush();
	            ImagesTable imagesTable2=imageRepository.findById(id).get();
	            imagesTable2.setResizeImages(newFileName);
	            imageRepository.save(imagesTable2);
	            
	        } catch (IOException e) {
	            logger.error(e.getMessage(), e);
	          
	        }
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
