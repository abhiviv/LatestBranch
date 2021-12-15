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


public class ImageResize extends Thread  {
	private static final Logger logger = LoggerFactory.getLogger(ImageResize.class);

	private String PATH;

    private String imageFolder=FileUtil.ResizePath;

    private Integer imageSize=1000;
	
	public ImageResize(String pATH) {
		PATH = pATH;
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
	           
	        } catch (IOException e) {
	            logger.error(e.getMessage(), e);
	          
	        }
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
