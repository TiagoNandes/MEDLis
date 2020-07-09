package com.example.medlis.badge;

import com.google.firebase.Timestamp;

public class BadgeClass {

    private String id_badge;
    private String id_user_badge;
    private Timestamp unblock_date;


    public BadgeClass(String id_user_badge, String id_badge, Timestamp unblock_date ) {
        this.id_badge = id_badge;
        this.id_user_badge = id_user_badge;
        this.unblock_date = unblock_date;
    }

    public String getIdBadge() {
        return id_badge;
    }

    public void setIdBadge(String id_badge) {
        this.id_badge = id_badge;
    }

    public String get_user_badge() {
        return id_user_badge;
    }

    public void set_user_badge(String id_user_badge) {
        this.id_user_badge = id_user_badge;
    }

    public Timestamp get_unblock_date() {
        return unblock_date;
    }

    public void set_unblock_date(Timestamp unblock_date) {
        this.unblock_date = unblock_date;
    }
}
