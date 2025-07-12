package com.example.bengkelkuapp.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class EditProfileLog extends RealmObject {

    @PrimaryKey
    private String id;
    private String emailUser;
    private String fieldDiubah;
    private String nilaiLama;
    private String nilaiBaru;
    private Date waktuEdit;

    // Getter dan Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getFieldDiubah() {
        return fieldDiubah;
    }

    public void setFieldDiubah(String fieldDiubah) {
        this.fieldDiubah = fieldDiubah;
    }

    public String getNilaiLama() {
        return nilaiLama;
    }

    public void setNilaiLama(String nilaiLama) {
        this.nilaiLama = nilaiLama;
    }

    public String getNilaiBaru() {
        return nilaiBaru;
    }

    public void setNilaiBaru(String nilaiBaru) {
        this.nilaiBaru = nilaiBaru;
    }

    public Date getWaktuEdit() {
        return waktuEdit;
    }

    public void setWaktuEdit(Date waktuEdit) {
        this.waktuEdit = waktuEdit;
    }
}
