package org.example.carpoolopgave.entities;

public class Trip {

    private int tripId;
    private String driverName;
    private String startDestination;
    private String endDestination;
    private String date;
    private String time;
    private int seats;

    public Trip(int tripId, String driverName,
                String startDestination,
                String endDestination,
                String date,
                String time,
                int seats) {

        this.tripId = tripId;
        this.driverName = driverName;
        this.startDestination = startDestination;
        this.endDestination = endDestination;
        this.date = date;
        this.time = time;
        this.seats = seats;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getStartDestination() {
        return startDestination;
    }

    public void setStartDestination(String startDestination) {
        this.startDestination = startDestination;
    }

    public String getEndDestination() {
        return endDestination;
    }

    public void setEndDestination(String endDestination) {
        this.endDestination = endDestination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    // getters her
}