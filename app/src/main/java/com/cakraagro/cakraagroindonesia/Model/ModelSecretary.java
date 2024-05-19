package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelSecretary {
    ArrayList<secretary> secretary;

    public ArrayList<ModelSecretary.secretary> getSecretary() {
        return secretary;
    }

    public void setSecretary(ArrayList<ModelSecretary.secretary> secretary) {
        this.secretary = secretary;
    }

    public static class secretary{
        String kode_sc, nama_secretary, username, password, level, kode_mg, nama_manager, fotosecretary, foto_url;

        public String getFoto_url() {
            return foto_url;
        }

        public void setFoto_url(String foto_url) {
            this.foto_url = foto_url;
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

        public String getKode_mg() {
            return kode_mg;
        }

        public void setKode_mg(String kode_mg) {
            this.kode_mg = kode_mg;
        }

        public String getNama_manager() {
            return nama_manager;
        }

        public void setNama_manager(String nama_manager) {
            this.nama_manager = nama_manager;
        }

        public String getFotosecretary() {
            return fotosecretary;
        }

        public void setFotosecretary(String fotosecretary) {
            this.fotosecretary = fotosecretary;
        }
    }
}
