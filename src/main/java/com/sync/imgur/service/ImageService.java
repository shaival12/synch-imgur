package com.sync.imgur.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sync.imgur.exception.RecordNotFoundException;
import com.sync.imgur.model.ImageEntity;
import com.sync.imgur.repository.ImageRepository;
import com.sync.imgur.util.ImgurUtil;
 
@Service
public class ImageService {
     
    private static final String STATUS_SUCCESS = "SUCCESS";
    
	@Autowired
    ImageRepository repository;
     
    /**
     * get list of all images
     * @return
     */
    public List<ImageEntity> getAllImages()
    {
        List<ImageEntity> imagesList = repository.findAll();
         
        if(imagesList.size() > 0) {
            return imagesList;
        } else {
            return new ArrayList<ImageEntity>();
        }
    }
     
    /**
     *  Get image by id
     * @param id
     * @return
     * @throws RecordNotFoundException
     */
    public ImageEntity getImageById(Long id) throws RecordNotFoundException
    {
        Optional<ImageEntity> image = repository.findById(id);
         
        if(image.isPresent()) {
            return image.get();
        } else {
            throw new RecordNotFoundException("No Image record exist for given id");
        }
    }
    
    
    /**
     * create or update a image record, once uploaded to Imgur
     * @param entity
     * @return
     * @throws RecordNotFoundException
     */
    public ImageEntity save(ImageEntity entity)  
    {
        Optional<ImageEntity> image = repository.findById(entity.getId());
         
        if(image.isPresent())
        {
        	ImageEntity newEntity = image.get();
            newEntity.setFileName(entity.getFileName());
            newEntity.setFilePath(entity.getFilePath());
            newEntity.setUserId(entity.getUserId());
 
            newEntity = repository.save(newEntity);
             
            return newEntity;
        } else {
        	
        	//upload to Imgur and save to DB
            entity = repository.save(entity);
            return entity;
        }
    }
     
    /**
     * delete image by id 
     * @param id
     * @throws RecordNotFoundException
     */
    public void deleteImageById(Long id) throws RecordNotFoundException
    {
        Optional<ImageEntity> image = repository.findById(id);
         
        if(image.isPresent())
        {
        	try {
        		//delete from Imgur, if successful then delete from DB
				String status = ImgurUtil.deleteImage(image.get().getDeleteHashCode());
				if(status.contentEquals(STATUS_SUCCESS)) {
				  repository.deleteById(id);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
            
        } else {
            throw new RecordNotFoundException("No Image record exist for given id");
        }
    }

	public List<ImageEntity> getImageByUserId(Long id) {

		List<ImageEntity> list = repository.findImageByUserId(id);
		return list;
	}
}