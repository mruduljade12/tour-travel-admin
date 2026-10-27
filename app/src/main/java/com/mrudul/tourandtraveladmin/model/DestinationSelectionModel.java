package com.mrudul.tourandtraveladmin.model;

public class DestinationSelectionModel {

    private DestinationRequest destination;

    private boolean selected;

    private Integer dayNumber;
    private Integer visitOrder;
    private String visitDate;

    public DestinationSelectionModel(DestinationRequest destination) {
        this.destination = destination;
        this.selected = false;
    }

    public DestinationRequest getSelectionDestination() {
        return destination;
    }


    public DestinationRequest getDestination() {
        return destination;
    }

    public void setDestination(DestinationRequest destination) {
        this.destination = destination;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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
