package com.software.acuity.splick.models;

public class StatisticsModel {

    String date;
    String clicks;

    public StatisticsModel() {
    }

    public StatisticsModel(String date, String clicks) {
        this.date = date;
        this.clicks = clicks;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClicks() {
        return clicks;
    }

    public void setClicks(String clicks) {
        this.clicks = clicks;
    }
}
