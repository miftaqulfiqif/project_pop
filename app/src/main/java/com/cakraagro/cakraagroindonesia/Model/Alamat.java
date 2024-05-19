package com.cakraagro.cakraagroindonesia.Model;

import com.google.gson.annotations.SerializedName;

public class Alamat {
    @SerializedName("nama_kantor")
    private String nama_kantor;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("telepon")
    private String telepon;
    @SerializedName("foto")
    private String foto;

    private Alamat(){}

    public Alamat(String nama_kantor, String alamat, String telepon, String foto) {
        this.nama_kantor = nama_kantor;
        this.alamat = alamat;
        this.telepon = telepon;
        this.foto = foto;
    }

    public String getNama_kantor() {
        return nama_kantor;
    }

    public void setNama_kantor(String nama_kantor) {
        this.nama_kantor = nama_kantor;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
