package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelSupervisor {
    ArrayList<supervisor> supervisor;

    public ArrayList<ModelSupervisor.supervisor> getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(ArrayList<ModelSupervisor.supervisor> supervisor) {
        this.supervisor = supervisor;
    }

    public static class supervisor{
        String kode_sv, kode_mg, nama_manager, nama_supervisor, fotosupervisor, area_sales, budget_tersedia, provinsi, username, password, level, foto_url;

        public String getFoto_url() {
            return foto_url;
        }

        public void setFoto_url(String foto_url) {
            this.foto_url = foto_url;
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

        public String getNama_manager() {
            return nama_manager;
        }

        public void setNama_manager(String nama_manager) {
            this.nama_manager = nama_manager;
        }

        public String getNama_supervisor() {
            return nama_supervisor;
        }

        public void setNama_supervisor(String nama_supervisor) {
            this.nama_supervisor = nama_supervisor;
        }

        public String getFotosupervisor() {
            return fotosupervisor;
        }

        public void setFotosupervisor(String fotosupervisor) {
            this.fotosupervisor = fotosupervisor;
        }

        public String getArea_sales() {
            return area_sales;
        }

        public void setArea_sales(String area_sales) {
            this.area_sales = area_sales;
        }

        public String getBudget_tersedia() {
            return budget_tersedia;
        }

        public void setBudget_tersedia(String budget_tersedia) {
            this.budget_tersedia = budget_tersedia;
        }

        public String getProvinsi() {
            return provinsi;
        }

        public void setProvinsi(String provinsi) {
            this.provinsi = provinsi;
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
