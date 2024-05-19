package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelDistributor {

    ArrayList<distributor> distributor;

    public ArrayList<ModelDistributor.distributor> getDistributor() {
        return distributor;
    }

    public void setDistributor(ArrayList<ModelDistributor.distributor> distributor) {
        this.distributor = distributor;
    }

    public static class distributor{
        String kode_dt, nama_distributor, username, password, level, kode_sc, nama_secretary, fotodistributor, kode_mg, perusahaan, foto_url;

        public String getFoto_url() {
            return foto_url;
        }

        public void setFoto_url(String foto_url) {
            this.foto_url = foto_url;
        }

        public String getKode_dt() {
            return kode_dt;
        }

        public void setKode_dt(String kode_dt) {
            this.kode_dt = kode_dt;
        }

        public String getNama_distributor() {
            return nama_distributor;
        }

        public void setNama_distributor(String nama_distributor) {
            this.nama_distributor = nama_distributor;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getKode_sc() {
            return kode_sc;
        }

        public void setKode_sc(String kode_sc) {
            this.kode_sc = kode_sc;
        }

        public String getNama_secretary() {
            return nama_secretary;
        }

        public void setNama_secretary(String nama_secretary) {
            this.nama_secretary = nama_secretary;
        }

        public String getFotodistributor() {
            return fotodistributor;
        }

        public void setFotodistributor(String fotodistributor) {
            this.fotodistributor = fotodistributor;
        }

        public String getKode_mg() {
            return kode_mg;
        }

        public void setKode_mg(String kode_mg) {
            this.kode_mg = kode_mg;
        }

        public String getPerusahaan() {
            return perusahaan;
        }

        public void setPerusahaan(String perusahaan) {
            this.perusahaan = perusahaan;
        }
    }
}
