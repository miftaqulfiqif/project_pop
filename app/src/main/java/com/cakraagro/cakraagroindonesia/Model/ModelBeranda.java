package com.cakraagro.cakraagroindonesia.Model;

public class ModelBeranda {
    private int id_beranda;
    private String judul, deskripsi, foto, foto_url;

    public ModelBeranda(int id_beranda, String judul, String deskripsi, String foto, String foto_url) {
        this.id_beranda = id_beranda;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.foto = foto;
        this.foto_url = foto_url;
    }

    public String getFoto_url() {
        return foto_url;
    }

    public void setFoto_url(String foto_url) {
        this.foto_url = foto_url;
    }

    public int getId_beranda() {
        return id_beranda;
    }

    public void setId_beranda(int id_beranda) {
        this.id_beranda = id_beranda;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
