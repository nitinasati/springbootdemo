package com.asatisamaj.matrimony.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Service
public class S3BucketStorageService {

    private static final Logger LOGGER = LogManager.getLogger(S3BucketStorageService.class);

    @Autowired
    @Qualifier("S3client")
    private AmazonS3 amazonS3Client;

    @Value("${application.bucket.name}")
    private String bucketName;

    /**
     * Upload file into AWS S3 
     *
     * @param keyName
     * @param file
     * @return String
     */
    public String uploadFile(String keyName, MultipartFile file) {
        try {
        	File fileTemp = new File(file.getOriginalFilename());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucketName, keyName, file.getInputStream(), metadata);
            if(fileTemp.delete()) {
            	LOGGER.info("File has been removed from server {}", fileTemp);
            }
            
           return "Your picture has been successfully uploaded.";
        } catch (IOException ioe) {
        	LOGGER.error("IOException: {}", ioe.getMessage());
        } catch (AmazonServiceException serviceException) {
        	LOGGER.error("AmazonServiceException:  {}",serviceException.getMessage());
            throw serviceException;
        } catch (AmazonClientException clientException) {
        	LOGGER.error("AmazonClientException Message: {}", clientException.getMessage());
            throw clientException;
        }
        return "File not uploaded: " + keyName;
    }

    /**
     * Deletes file from AWS S3 bucket
     *
     * @param fileName
     * @return
     */
    public String deleteFile(final String fileName) {
        amazonS3Client.deleteObject(bucketName, fileName);
        return "Deleted File: " + fileName;
    }

    /**
     * Check if file exists at AWS S3 bucket
     *
     * @param fileName
     * @return
     */
    public String checkFile(final String fileName) {
        return amazonS3Client.doesObjectExist(bucketName, fileName)?"File exists on S3":"Not present";
    }

    /**
     * Downloads file using amazon S3 client from S3 bucket
     *
     * @param keyName
     * @return ByteArrayOutputStream
     */
    public ByteArrayOutputStream downloadFile(String keyName) {
        try {
            S3Object s3object = amazonS3Client.getObject(new GetObjectRequest(bucketName, keyName));

            InputStream is = s3object.getObjectContent();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, len);
            }

            return outputStream;
        } catch (IOException ioException) {
        	LOGGER.error("IOException: {}",ioException.getMessage());
        } catch (AmazonServiceException serviceException) {
        	LOGGER.error("AmazonServiceException Message:    {}",serviceException.getMessage());
            throw serviceException;
        } catch (AmazonClientException clientException) {
        	LOGGER.error("AmazonClientException Message: {}",clientException.getMessage());
            throw clientException;
        }

        return null;
    }

    /**
     * Get all files from S3 bucket
     *
     * @return
     */
    public List<String> listFiles() {

        ListObjectsRequest listObjectsRequest =
                new ListObjectsRequest()
                        .withBucketName(bucketName);

        List<String> keys = new ArrayList<>();

        ObjectListing objects = amazonS3Client.listObjects(listObjectsRequest);

        while (true) {
            List<S3ObjectSummary> objectSummaries = objects.getObjectSummaries();
            if (objectSummaries.isEmpty()) {
                break;
            }

            for (S3ObjectSummary item : objectSummaries) {
                if (!item.getKey().endsWith("/"))
                    keys.add(item.getKey());
            }

            objects = amazonS3Client.listNextBatchOfObjects(objects);
        }

        return keys;
    }

}
