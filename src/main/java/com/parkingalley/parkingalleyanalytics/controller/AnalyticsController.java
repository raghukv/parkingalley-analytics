package com.parkingalley.parkingalleyanalytics.controller;

import com.parkingalley.parkingalleyanalytics.model.ParkingLog;
import com.parkingalley.parkingalleyanalytics.model.ResponseDetails;
import com.parkingalley.parkingalleyanalytics.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/openapi")
public class AnalyticsController {

    @Autowired
    AnalyticsService service;

    private int SUCCESS = 1;
    private int FAIL = 1;

    @GetMapping("/test")
    public ResponseEntity<String> test() throws InterruptedException {
        return new ResponseEntity<>("ready", HttpStatusCode.valueOf(200));
    }

    @PostMapping("/uploadParkingLog")
    public ResponseEntity<ResponseDetails> uploadParkingLog(@RequestBody ParkingLog parkingDetails) {

        long currTime = System.currentTimeMillis();
        System.out.println("Request Received at: " + currTime);
        parkingDetails.setCaptured_time(currTime);

        ResponseDetails responseDetails = new ResponseDetails(1, "");

        if (parkingDetails != null) {
            service.appendParkingLog(parkingDetails);
        }
        return ResponseEntity.ok(responseDetails);
    }

    @GetMapping("/data")
    public ResponseEntity<String> readData(){
        return new ResponseEntity<>(service.readTodaysFile(), HttpStatusCode.valueOf(200));
    }
}
