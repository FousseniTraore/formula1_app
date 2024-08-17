package com.example.traore_fousseni_s2110850_resit.DataTypes;

public class DriversStanding_DataTypes {
    private int position;
    private String driverGivenName;
    private String driverFamilyName;
    private int points;
    private int wins;

    public DriversStanding_DataTypes(int position, String driverGivenName, String driverFamilyName, int points, int wins) {
        this.position = position;
        this.driverGivenName = driverGivenName;
        this.driverFamilyName = driverFamilyName;
        this.points = points;
        this.wins = wins;
    }

    public int getPosition() {
        return position;
    }

    public String getDriverGivenName() {
        return driverGivenName;
    }

    public String getDriverFamilyName() {
        return driverFamilyName;
    }

    public int getPoints() {
        return points;
    }

    public int getWins() {
        return wins;
    }

    public void setDriverGivenName(String driverGivenName) {
        this.driverGivenName = driverGivenName;
    }

    public void setDriverFamilyName(String driverFamilyName) {
        this.driverFamilyName = driverFamilyName;
    }

    @Override
    public String toString() {
        return "DriversStanding_DataTypes{" +
                "position=" + position +
                ", driverGivenName='" + driverGivenName + '\'' +
                ", driverFamilyName='" + driverFamilyName + '\'' +
                ", points=" + points +
                ", wins=" + wins +
                '}';
    }

}
