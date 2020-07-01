package com.example.medlis.notifications;

public class Notification {

    private String title, band;
    private int cover;

    public Notification(String title, String band, int cover) {
        this.title = title;
        this.band = band;
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }
}
