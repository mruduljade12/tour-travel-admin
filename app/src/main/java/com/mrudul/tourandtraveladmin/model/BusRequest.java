package com.mrudul.tourandtraveladmin.model;

public class BusRequest {

    private String busNumber;
    private String busName;
    private String busType;
    private Integer busCapacity;

    public BusRequest(String busNumber, String busName, String busType, Integer busCapacity) {
        this.busNumber = busNumber;
        this.busName = busName;
        this.busType = busType;
        this.busCapacity = busCapacity;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public Integer getBusCapacity() {
        return busCapacity;
    }

    public void setBusCapacity(Integer busCapacity) {
        this.busCapacity = busCapacity;
    }
}
