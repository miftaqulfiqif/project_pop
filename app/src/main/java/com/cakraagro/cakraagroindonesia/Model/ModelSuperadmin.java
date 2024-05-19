package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelSuperadmin {

    ArrayList<data_superadmin> data_superadmin;

    public ArrayList<ModelSuperadmin.data_superadmin> getData_superadmin() {
        return data_superadmin;
    }

    public void setData_superadmin(ArrayList<ModelSuperadmin.data_superadmin> data_superadmin) {
        this.data_superadmin = data_superadmin;
    }

    public static class data_superadmin{
        String id_superadmin, username, password, confirm_password, nama, level;

        public String getConfirm_password() {
            return confirm_password;
        }

        public void setConfirm_password(String confirm_password) {
            this.confirm_password = confirm_password;
        }

        public String getId_superadmin() {
            return id_superadmin;
        }

        public void setId_superadmin(String id_superadmin) {
            this.id_superadmin = id_superadmin;
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
