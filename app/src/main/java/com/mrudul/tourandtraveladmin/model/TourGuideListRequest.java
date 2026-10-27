package com.mrudul.tourandtraveladmin.model;

public class TourGuideListRequest {

    private String guideName;
    private String guideEmail;
    private String guidePhone;
    private String language;
    private String joiningDate;
    private String guideAddress;
    private String profileImageUrl;

    public TourGuideListRequest(
            String guideName,
            String guideEmail,
            String guidePhone,
            String language,
            String joiningDate,
            String guideAddress,
            String profileImageUrl) {

        this.guideName = guideName;
        this.guideEmail = guideEmail;
        this.guidePhone = guidePhone;
        this.language = language;
        this.joiningDate = joiningDate;
        this.guideAddress = guideAddress;
        this.profileImageUrl = profileImageUrl;
    }

    public String getGuideName() {
        return guideName;
    }

    public String getGuideEmail() {
        return guideEmail;
    }

    public String getGuidePhone() {
        return guidePhone;
    }

    public String getLanguage() {
        return language;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public String getGuideAddress() {
        return guideAddress;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
