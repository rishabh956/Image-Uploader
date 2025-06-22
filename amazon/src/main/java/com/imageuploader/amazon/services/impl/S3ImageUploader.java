package com.imageuploader.amazon.services.impl;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.imageuploader.amazon.exceptions.ImageUploadException;
import com.imageuploader.amazon.services.ImageUploader;

@Service
public class S3ImageUploader implements ImageUploader {

	@Autowired
	public AmazonS3 client ;
	
	@Value("${app.s3.bucket}")
	private String bucketName ;
	
	@Override
	public String uploadImage(MultipartFile image) {
		// TODO Auto-generated method stub
		
		if(image == null)
		{
			throw new ImageUploadException("Image is null  :: ");
		}
		
		String actualFileName = image.getOriginalFilename();
		
		String fileName = UUID.randomUUID().toString() + actualFileName.substring(actualFileName.lastIndexOf('.')) ;
		
		ObjectMetadata metaData = new ObjectMetadata();
		metaData.setContentLength(image.getSize());
		
		try {
			PutObjectResult putObjectResult = client.putObject(bucketName, fileName, image.getInputStream(), metaData);
			
			return this.presignedUrl(fileName) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ImageUploadException("Error in image upload :: "+e.getMessage());
		} 
		
		
	}
	//@Async("asyncTaskExecutor")
	@Override
	public List<String> allFiles() {
		// TODO Auto-generated method stub
		
		// we need to return all the stored images object presigned URLs
		
		ListObjectsV2Request listObjectRequest = new ListObjectsV2Request().withBucketName(bucketName);
		
		ListObjectsV2Result listObjectV2Result = client.listObjectsV2(listObjectRequest);
		List<S3ObjectSummary> objectSummaries = listObjectV2Result.getObjectSummaries();
		
		List<String> listFileUrls = objectSummaries.stream().map(item -> this.presignedUrl(item.getKey())).collect(Collectors.toList());
		
		
		//return CompletableFuture.completedFuture(listFileUrls);
		return listFileUrls ;
	}

	@Override
	public String presignedUrl(String fileName) {
		// TODO Auto-generated method stub
		
		Date expirationDate = new Date() ;
		
		long time = expirationDate.getTime();
		
		int hour=2 ;
		time += hour *60 *60 * 1000 ;
		
		expirationDate.setTime(time);
		
		GeneratePresignedUrlRequest generatePresignedUrl = 
				new GeneratePresignedUrlRequest( bucketName,fileName)
				.withMethod(HttpMethod.GET)
				.withExpiration(expirationDate);
		URL url = client.generatePresignedUrl(generatePresignedUrl);
		
		return url.toString();
	}

}
