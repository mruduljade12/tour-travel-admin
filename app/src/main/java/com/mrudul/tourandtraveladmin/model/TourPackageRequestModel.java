package com.mrudul.tourandtraveladmin.model;

import java.util.List;

public class TourPackageRequestModel {

    private String packName;
    private String packDescription;
    private Integer packDurationDay;
    private Integer packDurationNight;
    private Integer packPrice;
    private Integer packMaxCapacity;
    private String packThumbnail;
    private String packStatus;
    private String packStartDate;

    private Integer driverId;
    private Integer guideId;

    private List<PackageDestinationModel> destinations;

    public TourPackageRequestModel() {
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

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Integer getGuideId() {
        return guideId;
    }

    public void setGuideId(Integer guideId) {
        this.guideId = guideId;
    }

    public List<PackageDestinationModel> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<PackageDestinationModel> destinations) {
        this.destinations = destinations;
    }
}