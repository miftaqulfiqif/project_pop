package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelManager {

    ArrayList<manager> manager;

    public ArrayList<ModelManager.manager> getManager() {
        return manager;
    }

    public void setManager(ArrayList<ModelManager.manager> manager) {
        this.manager = manager;
    }

    public static class manager{
        String kode_mg, nama_manager, fotomanager, username, password, level, foto_url;

        public String getFoto_url() {
            return foto_url;
        }

        public void setFoto_url(String foto_url) {
            this.foto_url = foto_url;
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

        public String getFotomanager() {
            return fotomanager;
        }

        public void setFotomanager(String fotomanager) {
            this.fotomanager = fotomanager;
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
    }
}
