package com.example.traore_fousseni_s2110850_resit.DataTypes;

public class Schedule_DataTypes {
    private String raceName;
    private String circuit;
    private String round;
    private String date;
    private String time;

    public Schedule_DataTypes(String raceName, String circuit, String round, String date, String time) {
        this.raceName = raceName;
        this.circuit = circuit;
        this.round = round;
        this.date = date;
        this.time = time;
    }

    public String getRaceName() {
        return raceName;
    }

    public String getCircuit() {
        return circuit;
    }

    public String getRound() {
        return round;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public void setCircuit(String circuit) {
        this.circuit = circuit;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Schedule_DataTypes{" +
                "raceName='" + raceName + '\'' +
                ", circuit='" + circuit + '\'' +
                ", round='" + round + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
