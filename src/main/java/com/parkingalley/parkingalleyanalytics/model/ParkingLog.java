package com.parkingalley.parkingalleyanalytics.model;

import java.util.Calendar;
import java.util.Random;

public class ParkingLog {
    private String pid;       // Venue unique id
    private String space;     // Space number
    private String map;       // Map Name
    private String area;      // Area Name
    private String garage;    // Garage Name
    private String plate;     // Plate number
    private long inTime;      // Parking start time
    private long outTime;     // Parking end time
    private int timeLen;      // Parking duration in minutes
    private long captured_time; //time at which the request was calle

    private String COMMA = ",";

    // Constructor
    public ParkingLog(String pid, String space, String map, String area, String garage, String plate, long inTime, long outTime, int timeLen) {
        this.pid = pid;
        this.space = space;
        this.map = map;
        this.area = area;
        this.garage = garage;
        this.plate = plate;
        this.inTime = inTime;
        this.outTime = outTime;
        this.timeLen = timeLen;
    }

    public String buildRow(){
        StringBuilder s = new StringBuilder();
        s.append(pid).append(COMMA);
        s.append(space).append(COMMA);
        s.append(map).append(COMMA);
        s.append(area).append(COMMA);
        s.append(garage).append(COMMA);
        s.append(plate).append(COMMA);
        s.append(inTime).append(COMMA);
        s.append(outTime).append(COMMA);
        s.append(timeLen).append(COMMA);
        s.append(captured_time);
        return s.toString();
    }

    public String getRandomData(){
        Random random = new Random();
        setPid(String.valueOf(random.nextInt()));
        setSpace(String.valueOf(random.nextInt()));
        setMap(String.valueOf(random.nextInt()));
        setArea(String.valueOf(random.nextInt()));
        setGarage(String.valueOf(random.nextInt()));
        setPlate(String.valueOf(random.nextInt()));
        setInTime(random.nextInt());
        setOutTime(random.nextInt());
        setTimeLen(random.nextInt());
        setCaptured_time(System.currentTimeMillis());
        return buildRow();
    }


    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getGarage() {
        return garage;
    }

    public void setGarage(String garage) {
        this.garage = garage;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public long getInTime() {
        return inTime;
    }

    public void setInTime(long inTime) {
        this.inTime = inTime;
    }

    public long getOutTime() {
        return outTime;
    }

    public void setOutTime(long outTime) {
        this.outTime = outTime;
    }

    public int getTimeLen() {
        return timeLen;
    }

    public void setTimeLen(int timeLen) {
        this.timeLen = timeLen;
    }

    // Getters and Setters
    // Add getters and setters for each field to properly encapsulate the class fields.
    // Example:
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public long getCaptured_time() {
        return captured_time;
    }

    public void setCaptured_time(long captured_time) {
        this.captured_time = captured_time;
    }

}
