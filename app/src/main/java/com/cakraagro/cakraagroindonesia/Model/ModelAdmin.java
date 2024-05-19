package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelAdmin {
    ArrayList<data_admin> data_admin;

    public ArrayList<ModelAdmin.data_admin> getData_admin() {
        return data_admin;
    }

    public void setData_admin(ArrayList<ModelAdmin.data_admin> data_admin) {
        this.data_admin = data_admin;
    }

    public static class data_admin{
        String id_admin, username, password, confirm_password, nama, level;

        public String getConfirm_password() {
            return confirm_password;
        }

        public void setConfirm_password(String confirm_password) {
            this.confirm_password = confirm_password;
        }

        public String getId_admin() {
            return id_admin;
        }

        public void setId_admin(String id_admin) {
            this.id_admin = id_admin;
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

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }
}
