package com.sync.imgur.util;


import com.sync.imgur.exception.UploadException;
import com.sync.imgur.util.CustomResponseHandler;
import com.sync.imgur.util.ResponseObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

public class ImgurUtil {
	
	private static final Logger LOGGER = Logger.getLogger(ImgurUtil.class);
	
	private static final String IMGUR_URL = "https://api.imgur.com/3/upload";
	private static final String IMGUR_DELETE_URL = "https://api.imgur.com/3/image/";
	
	private static String CLIENT_ID = "ff4e87024f0d84c";

	
	 /**
	  * upload Image to Imgur	
	  * @param base64String
	  * @return
	  * @throws Exception
	  */
	 public static ResponseObject uploadImage(String base64String) throws Exception {

	    
		CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPostRequest = new HttpPost(IMGUR_URL);
        httpPostRequest.setHeader("Authorization", "Client-ID " + CLIENT_ID);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("image", base64String));
        CustomResponseHandler customResponseHandler = new CustomResponseHandler();
        int status = -1;
        ResponseObject responseBody = null;
        try {
            httpPostRequest.setEntity(new UrlEncodedFormEntity(params));
            
            // call Imgur API and upload image
            responseBody = (ResponseObject) httpClient.execute(httpPostRequest, customResponseHandler);
            status = responseBody.getStatusCode();
            
            if(status>=200 && status<300){
                LOGGER.info("Uploaded to Imgur successfully.");
            } else {
                LOGGER.info("Uploading failed.");
            }

          
        } catch (UnsupportedEncodingException e) {
            throw new UploadException(e, status);
        } catch (ClientProtocolException e) {
            throw new UploadException(e, status);
        } catch (Exception e) {
            throw new UploadException(e, status);
        }finally {
        	httpClient.close();
        }

       return responseBody;
	}
	 
	 
	 /**
	  * delete image from Imgur
	  * @param deleteHash
	  * @return
	  * @throws Exception
	  */
	 public static String deleteImage(String deleteHash) throws Exception {

		    
			CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpPost httpPostRequest = new HttpPost(IMGUR_DELETE_URL+deleteHash);
	        httpPostRequest.setHeader("Authorization", "Client-ID " + CLIENT_ID);
	        
	        try {
	            
	            // call Imgur API and delete image
	             httpClient.execute(httpPostRequest);
	             
	        } catch (Exception e) {
	        	LOGGER.error("deletion of image failed.");
	            return "FAILURE";
	        } finally {
	        	httpClient.close();
	        }

	       return "SUCCESS";
		}
	
	
}
