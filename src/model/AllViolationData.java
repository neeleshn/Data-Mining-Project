package model;

import utils.Date;

/**
 * Models data from AllViolations.csv
 **/

public class AllViolationData {

    private int serialID;
    private Date date;
    private String restaurantID;
    private ViolationEntry violationEntry;

    public AllViolationData() {
        date = new Date();
        restaurantID = "";
        this.violationEntry = new ViolationEntry();
    }

    public AllViolationData(String date, String restID, ViolationEntry ve) {
        this.date = Date.parseAsDate(date);
        this.restaurantID = restID;
        this.violationEntry = new ViolationEntry();
    }

    public AllViolationData(String date, String restID, int v1, int v2, int v3) {
        this.date = Date.parseAsDate(date);
        this.restaurantID = restID;
        this.violationEntry.setMinorViolationCount(v1);
        this.violationEntry.setMajorViolationCount(v2);
        this.violationEntry.setSevereViolationCount(v3);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = Date.parseAsDate(date);
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public ViolationEntry getViolationEntry() {
        return violationEntry;
    }

    @Override
    public String toString() {
        String newline = "\n";
        String serialIDString = "SerialID: " + this.serialID;
        String dateString = "Date: " + this.date;
        String restIDString = "RestaurantID: " + restaurantID;
        String veString = "Violation Entries: " + violationEntry;

        return serialIDString + newline + dateString + newline + restIDString + newline + veString + newline;
    }

    public int getSerialID() {
        return serialID;
    }

    public void setSerialID(int serialID) {
        this.serialID = serialID;
    }
}

