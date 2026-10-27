package com.mrudul.tourandtraveladmin.model;

public class PackageDestinationModel {

    private Integer destinationId;
    private Integer dayNumber;
    private Integer visitOrder;
    private String visitDate;

    public PackageDestinationModel() {
    }

    public PackageDestinationModel(Integer destinationId,
                                   Integer dayNumber,
                                   Integer visitOrder,
                                   String visitDate) {
        this.destinationId = destinationId;
        this.dayNumber = dayNumber;
        this.visitOrder = visitOrder;
        this.visitDate = visitDate;
    }


    public Integer getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public Integer getVisitOrder() {
        return visitOrder;
    }

    public void setVisitOrder(Integer visitOrder) {
        this.visitOrder = visitOrder;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }
}
