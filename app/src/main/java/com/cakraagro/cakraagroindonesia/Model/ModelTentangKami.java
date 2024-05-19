package com.cakraagro.cakraagroindonesia.Model;

public class ModelTentangKami {
    private int id_tentangkamiapp;
    private String deskripsi;
    private String visi;
    private String misi;

    public ModelTentangKami(int id_tentangkamiapp, String deskripsi, String visi, String misi) {
        this.id_tentangkamiapp = id_tentangkamiapp;
        this.deskripsi = deskripsi;
        this.visi = visi;
        this.misi = misi;
    }

    public int getId_tentangkamiapp() {
        return id_tentangkamiapp;
    }

    public void setId_tentangkamiapp(int id_tentangkamiapp) {
        this.id_tentangkamiapp = id_tentangkamiapp;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getVisi() {
        return visi;
    }

    public void setVisi(String visi) {
        this.visi = visi;
    }

    public String getMisi() {
        return misi;
    }

    public void setMisi(String misi) {
        this.misi = misi;
    }
}
