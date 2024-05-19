package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelDemonstrator {

    ArrayList<demonstrator> demonstrator;

    public ArrayList<ModelDemonstrator.demonstrator> getDemonstrator() {
        return demonstrator;
    }

    public void setDemonstrator(ArrayList<ModelDemonstrator.demonstrator> demonstrator) {
        this.demonstrator = demonstrator;
    }

    public static class demonstrator{
        String kode_ds, nama_demonstrator, username, password, nama_supervisor, fotodemonstrator, kode_sv, kode_mg, kabupaten, provinsi, level, foto_url;

        public String getFoto_url() {
            return foto_url;
        }

        public void setFoto_url(String foto_url) {
            this.foto_url = foto_url;
        }

        public String getKode_ds() {
            return kode_ds;
        }

        public void setKode_ds(String kode_ds) {
            this.kode_ds = kode_ds;
        }

        public String getNama_demonstrator() {
            return nama_demonstrator;
        }

        public void setNama_demonstrator(String nama_demonstrator) {
            this.nama_demonstrator = nama_demonstrator;
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

        public String getNama_supervisor() {
            return nama_supervisor;
        }

        public void setNama_supervisor(String nama_supervisor) {
            this.nama_supervisor = nama_supervisor;
        }

        public String getFotodemonstrator() {
            return fotodemonstrator;
        }

        public void setFotodemonstrator(String fotodemonstrator) {
            this.fotodemonstrator = fotodemonstrator;
        }

        public String getKode_sv() {
            return kode_sv;
        }

        public void setKode_sv(String kode_sv) {
            this.kode_sv = kode_sv;
        }

        public String getKode_mg() {
            return kode_mg;
        }

        public void setKode_mg(String kode_mg) {
            this.kode_mg = kode_mg;
        }

        public String getKabupaten() {
            return kabupaten;
        }

        public void setKabupaten(String kabupaten) {
            this.kabupaten = kabupaten;
        }

        public String getProvinsi() {
            return provinsi;
        }

        public void setProvinsi(String provinsi) {
            this.provinsi = provinsi;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }
}
