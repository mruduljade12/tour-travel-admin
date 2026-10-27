package com.mrudul.tourandtraveladmin.model;

import java.util.List;

public class TourPackageResponseModel {

    private Integer packageId;

    private String packName;
    private String packDescription;

    private Integer packDurationDay;
    private Integer packDurationNight;

    private Integer packPrice;
    private Integer packMaxCapacity;

    private String packThumbnail;
    private String packStatus;
    private String packStartDate;

    private Float packRating;

    private DriverModel driver;
    private GuideModel guide;

    private List<PackageDestinationResponse> destinations;


    public TourPackageResponseModel() {
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getPackDescription() {
        return packDescription;
    }

    public void setPackDescription(String packDescription) {
        this.packDescription = packDescription;
    }

    public Integer getPackDurationDay() {
        return packDurationDay;
    }

    public void setPackDurationDay(Integer packDurationDay) {
        this.packDurationDay = packDurationDay;
    }

    public Integer getPackDurationNight() {
        return packDurationNight;
    }

    public void setPackDurationNight(Integer packDurationNight) {
        this.packDurationNight = packDurationNight;
    }

    public Integer getPackPrice() {
        return packPrice;
    }

    public void setPackPrice(Integer packPrice) {
        this.packPrice = packPrice;
    }

    public Integer getPackMaxCapacity() {
        return packMaxCapacity;
    }

    public void setPackMaxCapacity(Integer packMaxCapacity) {
        this.packMaxCapacity = packMaxCapacity;
    }

    public String getPackThumbnail() {
        return packThumbnail;
    }

    public void setPackThumbnail(String packThumbnail) {
        this.packThumbnail = packThumbnail;
    }

    public String getPackStatus() {
        return packStatus;
    }

    public void setPackStatus(String packStatus) {
        this.packStatus = packStatus;
    }

    public String getPackStartDate() {
        return packStartDate;
    }

    public void setPackStartDate(String packStartDate) {
        this.packStartDate = packStartDate;
    }

    public Float getPackRating() {
        return packRating;
    }

    public void setPackRating(Float packRating) {
        this.packRating = packRating;
    }

    public DriverModel getDriver() {
        return driver;
    }

    public void setDriver(DriverModel driver) {
        this.driver = driver;
    }

    public GuideModel getGuide() {
        return guide;
    }

    public void setGuide(GuideModel guide) {
        this.guide = guide;
    }

    public List<PackageDestinationResponse> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<PackageDestinationResponse> destinations) {
        this.destinations = destinations;
    }
}
