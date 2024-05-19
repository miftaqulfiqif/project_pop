package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelAlamat{

    ArrayList<alamat> alamat;

    public ArrayList<ModelAlamat.alamat> getDataAlamat() {
        return alamat;
    }

    public void setDataAlamat(ArrayList<ModelAlamat.alamat> alamat) {
        this.alamat = alamat;
    }

    public static class alamat{

        int id_alamat;
        String nama_kantor, alamat, telepon, foto, foto_url;

        public int getId_alamat() {
            return id_alamat;
        }

        public void setId_alamat(int id_alamat) {
            this.id_alamat = id_alamat;
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

        public String getFoto_url() {
            return foto_url;
        }

        public void setFoto_url(String foto_url) {
            this.foto_url = foto_url;
        }
    }
}
