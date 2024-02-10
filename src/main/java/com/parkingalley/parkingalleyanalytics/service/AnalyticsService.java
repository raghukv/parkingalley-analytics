package com.parkingalley.parkingalleyanalytics.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.stream.Collectors;

import com.parkingalley.parkingalleyanalytics.model.ParkingLog;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@Service
public class AnalyticsService {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final String S3 = "patd-h1//AKIAZI2LD5LJ4FE3W5M2//PV8cXBJgf+dLuxVJ+ZU4TB1dvgioHJc2Iq1e3oTt";


    public String appendParkingLog(ParkingLog parkingLog){
        String response = null;
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(
                        getAK(),
                        getSK()
                )))
                .withRegion(Regions.AP_SOUTH_1)
                .build();

        String bucketName = getBucket();
        String fileName = getFileName();

        //check if bucket exists
        if(s3client.doesBucketExistV2(bucketName)){
            //if file exists for today, write to it
            if(s3client.doesObjectExist(bucketName, fileName)){
                GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileName);
                S3Object objectResponse = s3client.getObject(getObjectRequest);
                BufferedReader reader = new BufferedReader(new InputStreamReader(objectResponse.getObjectContent()));
                // Read the existing content and append the new line
                String existingContent = reader.lines().collect(Collectors.joining("\n"));
                String updatedContent = existingContent + "\n" + parkingLog.getRandomData();
                PutObjectRequest putObject = new PutObjectRequest(bucketName, fileName, file);
                PutObjectResult result = s3client.putObject(putObject, RequestBody.fromString(updatedContent));
            }else{
                //if file does not exist, create and write
                File file = new File(fileName);
                try {
                    file.createNewFile();
                    try {
                        FileWriter writer = new FileWriter(fileName, true);
                        writer.write(parkingLog.getRandomData() + "\n");
                        writer.close();
                    } catch (IOException e) {
                        System.err.println("Failed to append to the file: " + e.getMessage());
                    }
                } catch (IOException e) {
                    response = "file creation failed at disk";
                    return response;
                }
                PutObjectRequest putObject = new PutObjectRequest(bucketName, fileName, file);
                PutObjectResult result = s3client.putObject(putObject);
                System.out.println(result);
                response = "file created";
            }
        }
    return response;
    }

    public String createTestFile(){
        File file = new File(getFileName());
        try {
            return String.valueOf(file.createNewFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private String getFileName() {
        return "PilotVenue_" + getToday();
    }

    private String getBucket(){
        return this.S3.split("//")[0];
    }

    private String getToday(){
        return LocalDateTime.now().format(formatter);
    }

    private String getAK(){
        return this.S3.split("//")[1];
    }

    private String getSK(){
        return this.S3.split("//")[2];
    }
}
