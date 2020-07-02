package com.example.medlis.notifications;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Notification {

    private String title;
    private Timestamp alertDate;
    private String description;
    private String id_userMed;
    private String id_user;
    private boolean checkIntake;

    public Notification(String title, Timestamp alertDate, boolean checkIntake, String description, String id_user, String id_userMed ) {
        this.title = title;
        this.alertDate = alertDate;
        this.checkIntake = checkIntake;
        this.description = description;
        this.id_user = id_user;
        this.id_userMed = id_userMed;
    }
//  id_user, id_userMed
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getAlertDate() {
        return alertDate;
    }

    public void setAlertDate(Timestamp alertDate) {
        this.alertDate = alertDate;
    }

    public boolean getCheckIntake() {
        return checkIntake;
    }

    public void setCheckIntake(boolean checkIntake) {
        this.checkIntake = checkIntake;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_userMed() {
        return id_userMed;
    }

    public void setId_userMed(String id_userMed) {
        this.description = id_userMed;
    }


}
