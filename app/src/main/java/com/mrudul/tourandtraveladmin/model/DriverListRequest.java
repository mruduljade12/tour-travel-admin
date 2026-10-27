package com.mrudul.tourandtraveladmin.model;

public class DriverListRequest {

    private String driverName;
    private String driverEmail;
    private String driverPhone;
    private String licenseNumber;
    private String joiningDate;
    private String driverAddress;
    private String profileImageUrl;

    public DriverListRequest(
            String driverName,
            String driverEmail,
            String driverPhone,
            String licenseNumber,
            String joiningDate,
            String driverAddress,
            String profileImageUrl) {

        this.driverName = driverName;
        this.driverEmail = driverEmail;
        this.driverPhone = driverPhone;
        this.licenseNumber = licenseNumber;
        this.joiningDate = joiningDate;
        this.driverAddress = driverAddress;
        this.profileImageUrl = profileImageUrl;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public String getDriverAddress() {
        return driverAddress;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

}
