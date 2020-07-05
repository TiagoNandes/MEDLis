package com.example.medlis;

import com.google.firebase.Timestamp;

public class MedicationClass {

    private String description;
    private String dosage_hours;
    private String expiry_date;
    private String id_medicine;
    private int remaining_quantity;
    private String id_user;

    public MedicationClass(String description, String dosage_hours, String expiry_date, String id_medicine, int remaining_quantity, String id_user ) {
        this.description = description;
        this.dosage_hours = dosage_hours;
        this.expiry_date = expiry_date;
        this.id_medicine = id_medicine;
        this.remaining_quantity = remaining_quantity;
        this.id_user = id_user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDosageHours() {
        return dosage_hours;
    }

    public void setDosageHours(String dosage_hours) {
        this.dosage_hours = dosage_hours;
    }

    public String getExpiryDate() {
        return expiry_date;
    }

    public void setExpiryDate(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getIdMedicine() {
        return id_medicine;
    }

    public void setIdMedicine(String id_medicine) {
        this.id_medicine = id_medicine;
    }

    public int getRemainingQuantity() {
        return remaining_quantity;
    }

    public void setRemainingQuantity(int remaining_quantity) {
        this.remaining_quantity = remaining_quantity;
    }

    public String getIdUser() {
        return id_user;
    }

    public void setIdUser(String id_user) {
        this.id_user = id_user;
    }


}




