package com.example.traore_fousseni_s2110850_resit.DataTypes;

public class DriverDetails {
    private String position;
    private String points;
    private String wins;
    private String driverId;
    private String code;
    private String url;
    private String givenName;
    private String familyName;
    private String dateOfBirth;
    private String nationality;

    public DriverDetails(String position, String points, String wins, String driverId, String code, String url, String givenName, String familyName, String dateOfBirth, String nationality) {
        this.position = position;
        this.points = points;
        this.wins = wins;
        this.driverId = driverId;
        this.code = code;
        this.url = url;
        this.givenName = givenName;
        this.familyName = familyName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
    }

    public String getPosition() {
        return position;
    }

    public String getPoints() {
        return points;
    }

    public String getWins() {
        return wins;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getCode() {
        return code;
    }

    public String getUrl() {
        return url;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
