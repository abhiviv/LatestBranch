package com.management.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.management.entity.ImagesTable;
import com.management.repository.ImageRepository;


public class ImageResize extends Thread  {
	
	private static final Logger logger = LoggerFactory.getLogger(ImageResize.class);

	private String PATH;

    private String imageFolder;

    private Integer imageSize=600;
	
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
	            BufferedImage outputImage = Scalr.resize(bufferedImage, Method.AUTOMATIC, imageSize);
	            int width          = outputImage.getWidth();
	            int height         = outputImage.getHeight();
	            String newFileName = FilenameUtils.getBaseName(sourceFile.getName())
	                    + "_" + imageSize.toString() + "."
	                    + FilenameUtils.getExtension(sourceFile.getName());
	            String newpath=imageFolder+'\\'+width+'x'+height;
	            Path path = Paths.get(newpath,newFileName);
	            File newImageFile = path.toFile();
	            newImageFile.mkdirs();
	            ImageIO.write(outputImage, "jpg", newImageFile);
	            outputImage.flush();
	            ImagesTable imagesTable2=imageRepository.findById(id).get();
	            String dimension=String.valueOf(width)+'x'+String.valueOf(height);
	            imagesTable2.setResizedimension(dimension);
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
