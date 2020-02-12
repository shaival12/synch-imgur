package com.sync.imgur.web;

import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sync.imgur.model.UserEntity;
import com.sync.imgur.exception.RecordNotFoundException;
import com.sync.imgur.model.ImageEntity;
import com.sync.imgur.service.UserService;
import com.sync.imgur.util.ImgurUtil;
import com.sync.imgur.util.ResponseObject;
import com.sync.imgur.service.ImageService;
 
@RestController
@RequestMapping("/v1/images")
public class ImageController
{
    @Autowired
    ImageService service;
    
    /**
     * get all images
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ImageEntity>> getAllImages() {
        List<ImageEntity> list = service.getAllImages();
 
        return new ResponseEntity<List<ImageEntity>>(list, new HttpHeaders(), HttpStatus.OK);
    }
 
    /**
     * get an image by id 
     * @param id
     * @return
     * @throws RecordNotFoundException
     */
    @GetMapping("/{id}")
    public ResponseEntity<ImageEntity> getImageById(@PathVariable("id") Long id)
                                                    throws RecordNotFoundException {
    	ImageEntity entity = service.getImageById(id);
 
        return new ResponseEntity<ImageEntity>(entity, new HttpHeaders(), HttpStatus.OK);
    }
    
    /**
     * get all images of an user
     * @param id
     * @return
     * @throws RecordNotFoundException
     */
    @GetMapping("/user/{id}")
    public List<ImageEntity> getImagesByUserId(@PathVariable("id") Long id) {
    	List<ImageEntity> entityList = service.getImageByUserId(id);
 
        return entityList;
    }
    
    /**
     * delete an image
     * @param id
     * @return
     * @throws RecordNotFoundException
     */
    @DeleteMapping("/{id}")
    public HttpStatus deleteImageById(@PathVariable("id") Long id)
                                                    throws RecordNotFoundException {
        service.deleteImageById(id);
        return HttpStatus.FORBIDDEN;
    }
    
   /**
    * upload file to imgur and save to Db
    * @param file
    * @param fileName
    * @param userId
    * @return
    */
    @PostMapping("/upload") 
    public HttpStatus singleFileUpload(@RequestParam("file") MultipartFile file,
    		                           @RequestParam("fileName") String fileName,
    		                           @RequestParam("userId") Long userId ) {

        if (file.isEmpty()) {
            //redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return HttpStatus.BAD_REQUEST;
        }

        try {

            // convert bytes to base64
            byte[] bytes = file.getBytes();
            String base64String = Base64.getEncoder().encodeToString(bytes);
            
            // call util to upload to Imgur
            ResponseObject responseObj =  ImgurUtil.uploadImage(base64String);
            
            //save to h2 DB 
            if(responseObj!=null) {
	            ImageEntity imageEntity = new ImageEntity();
	            imageEntity.setFileName(fileName);
	            imageEntity.setFilePath(responseObj.getLink());
	            imageEntity.setDeleteHashCode(responseObj.getDeleteHashCode());
	            imageEntity.setImgType(responseObj.getImgType());
	            imageEntity.setUserId(userId);
				service.save(imageEntity);
				
            }else {
            	return HttpStatus.EXPECTATION_FAILED;
            }

        } catch (Exception e) {
            return HttpStatus.EXPECTATION_FAILED;
        }

        return HttpStatus.OK;
    }
 
    
 
}