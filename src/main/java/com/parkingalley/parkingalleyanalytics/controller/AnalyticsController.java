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
    public ResponseEntity<String> test(){
        service.appendParkingLog2(null);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }


    @PostMapping("/uploadParkingLog")
    public ResponseEntity<ResponseDetails> uploadParkingLog(@RequestBody ParkingLog parkingDetails) {

        parkingDetails.setCaptured_time(System.currentTimeMillis());

        ResponseDetails responseDetails;

        if (parkingDetails != null && service.appendParkingLog(parkingDetails)) {
            responseDetails = new ResponseDetails(SUCCESS, "");
        } else {
            responseDetails = new ResponseDetails(FAIL, "Failed to upload parking log.");
        }
        return ResponseEntity.ok(responseDetails);
    }
}
