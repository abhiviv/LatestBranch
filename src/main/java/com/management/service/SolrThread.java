package com.management.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.management.entity.ImageSearch;
import com.management.repository.SolrRepository;

public class SolrThread extends  Thread {
	
	@Autowired
	private SolrRepository solrRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(Mailthread.class);
		
	private Long imageId;
	
	private String imageName;
	
	private String imageDetails;
	
	private String categoryname;
	


	public SolrThread(SolrRepository solrRepository, Long imageId, String imageName, String imageDetails,
			String categoryname) {
		this.solrRepository = solrRepository;
		this.imageId = imageId;
		this.imageName = imageName;
		this.imageDetails = imageDetails;
		this.categoryname = categoryname;
	}


	@Override
	public synchronized void run() {
		 try {
			Thread.sleep(1000);
			logger.debug("thread Started");
			ImageSearch image=new  ImageSearch();
			image.setImageid(imageId);
			image.setImageName(imageName);
			image.setImageDescription(imageDetails);
			image.setCategory(categoryname);
			solrRepository.save(image);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error("error"+e);
		}
		
		
	}
	
}
