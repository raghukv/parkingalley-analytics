package com.parkingalley.parkingalleyanalytics.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import com.parkingalley.parkingalleyanalytics.model.ParkingLog;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {

    private static final String FILE_PATH = "path/to/your/logfile.txt"; // specify your file path

    private static final String S3 = "patd-h1//AKIAZI2LD5LJ4FE3W5M2//PV8cXBJgf+dLuxVJ+ZU4TB1dvgioHJc2Iq1e3oTt";

    public boolean appendParkingLog(ParkingLog parkingDetails) {
//        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Convert ParkingDetails object to JSON string
//            String parkingDetailsJson = objectMapper.writeValueAsString(parkingDetails);
              String parkingDetailsRow = parkingDetails.buildRow();
            // Append the JSON string to the file in a new line
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true));
            writer.append(parkingDetailsRow);
            writer.newLine();
            writer.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {

        }
    }

    public boolean appendParkingLog2(ParkingLog parkingLog){

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

        if(s3client.doesBucketExistV2(bucketName)){
            GetObjectRequest getObject = new GetObjectRequest(getBucket(), fileName);
            if(s3client.doesObjectExist(bucketName, fileName)){
                S3Object obj = s3client.getObject(getObject);
            }else{
//                PutObjectRequest putObject = new PutObjectRequest();
            }
        }
    return false;
    }

    private String getFileName() {
        return "ankur_kothapet_" + getToday();
    }

    private String getBucket(){
        return this.S3.split("//")[0];
    }

    private String getToday(){
        LocalDateTime now = LocalDateTime.now();

        // Print the current date and time
        System.out.println("Current Date and Time: " + now);

        // If you want to print the date and time in a specific format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDateTime = now.format(formatter);
        return formattedDateTime;
    }

    private String getAK(){
        return this.S3.split("//")[1];
    }

    private String getSK(){
        return this.S3.split("//")[2];
    }


}
