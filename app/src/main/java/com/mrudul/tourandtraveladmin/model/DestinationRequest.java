package com.mrudul.tourandtraveladmin.model;

public class DestinationRequest {

    private Integer destinationId;
    private String name;
    private String city;
    private String state;
    private String country;
    private String description;
    private String latitude;
    private String longitude;
    private String image;

    public DestinationRequest(){}

    public DestinationRequest(Integer destinationId, String name, String city, String state, String country, String description, String latitude, String longitude, String image) {
        this.destinationId = destinationId;
        this.name = name;
        this.city = city;
        this.state = state;
        this.country = country;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
    }

    public Integer getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
