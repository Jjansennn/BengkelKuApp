package com.example.bengkelkuapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Booking extends RealmObject {

    @PrimaryKey
    private String id;
    private String emailUser;
    private String idWorkshop;
    private String tanggal;
    private String jam;
    private String jenisLayanan;
    private boolean selesai;

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

    public String getIdWorkshop() {
        return idWorkshop;
    }

    public void setIdWorkshop(String idWorkshop) {
        this.idWorkshop = idWorkshop;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getJenisLayanan() {
        return jenisLayanan;
    }

    public void setJenisLayanan(String jenisLayanan) {
        this.jenisLayanan = jenisLayanan;
    }

    public boolean isSelesai() {
        return selesai;
    }

    public void setSelesai(boolean selesai) {
        this.selesai = selesai;
    }
}
