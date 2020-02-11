package com.sync.imgur.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sync.imgur.exception.RecordNotFoundException;
import com.sync.imgur.model.UserEntity;
import com.sync.imgur.model.ImageEntity;
import com.sync.imgur.service.UserService;
import com.sync.imgur.service.ImageService;
 
@RestController
@RequestMapping("/image")
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
    * upload an image 
    * @param employee
    * @return
    * @throws RecordNotFoundException
    */
    @PostMapping
    public ResponseEntity<ImageEntity> uploadImage(ImageEntity image)
                                                    throws RecordNotFoundException {
    	ImageEntity updated = service.uploadImage(image);
        return new ResponseEntity<ImageEntity>(updated, new HttpHeaders(), HttpStatus.OK);
    }
 
    
 
}