package com.parkingalley.parkingalleyanalytics.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import com.parkingalley.parkingalleyanalytics.model.ParkingLog;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final String S3_BUCKET = "patd-h1";

    private static final String ACCESS_KEY = "AKIAZI2LD5LJ4FE3W5M2";

    private static final String SEC_KEY = "PV8cXBJgf+dLuxVJ+ZU4TB1dvgioHJc2Iq1e3oTt";

    private File createAndAppendToFile(String content, String fileName) {
        try {
            File file = new File(fileName);
            file.createNewFile();
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content + "\n");
            writer.close();
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String appendParkingLog(ParkingLog parkingLog) {
        String response = "ok";
        AmazonS3 s3client = getS3Client();

        String bucketName = S3_BUCKET;
        String fileName = getFileName();
        String content = null;

        //check if bucket exists
        if (s3client.doesBucketExistV2(bucketName)) {
            //if file exists for today, write to it
//            if (s3client.doesObjectExist(bucketName, fileName)) {
//            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileName);
//            S3Object objectResponse = s3client.getObject(getObjectRequest);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(objectResponse.getObjectContent()));
            // Read the existing content and append the new line
//                String existingContent = reader.lines().collect(Collectors.joining("\n"));
//                content = existingContent + "\n" + parkingLog.buildRow();
            content = parkingLog.buildRow();
//            }else{
//                content = parkingLog.buildRow();
//            }
            System.out.println(content);
            File file = createAndAppendToFile(content, fileName);
            PutObjectRequest req = new PutObjectRequest(bucketName, fileName, file);
            PutObjectResult result = s3client.putObject(req);
            System.out.println("uploaded data to s3");
        }
        return response;
    }

    public String readTodaysFile(){
        AmazonS3 s3client = getS3Client();
        String bucketName = S3_BUCKET;
        String fileName = getFileName();
        String content = "NA";

        //check if bucket exists
        if (s3client.doesBucketExistV2(bucketName)) {
            //if file exists for today, write to it
            if (s3client.doesObjectExist(bucketName, fileName)) {
                GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileName);
                S3Object objectResponse = s3client.getObject(getObjectRequest);
                BufferedReader reader = new BufferedReader(new InputStreamReader(objectResponse.getObjectContent()));
                // Read the existing content and append the new line
                content = reader.lines().collect(Collectors.joining("\n"));
            }
        }
        return content;
    }

    private static AmazonS3 getS3Client() {
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(
                        ACCESS_KEY,
                        SEC_KEY
                )))
                .withRegion(Regions.AP_SOUTH_1)
                .build();
        return s3client;
    }



    public String createTestFile() {
        File file = new File(getFileName());
        try {
            return String.valueOf(file.createNewFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private String getFileName() {
        return "PilotVenue_" + getToday() + ".txt";
    }

    private String getBucket() {
        return this.S3_BUCKET.split("//")[0];
    }

    private String getToday() {
        return LocalDateTime.now().format(formatter);
    }

    private String getAK() {
        return this.S3_BUCKET.split("//")[1];
    }

    private String getSK() {
        return this.S3_BUCKET.split("//")[2];
    }
}
