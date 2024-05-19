package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelNotifikasi {

    ArrayList<notifikasi> notifikasi;

    public ArrayList<ModelNotifikasi.notifikasi> getNotifikasi() {
        return notifikasi;
    }

    public void setNotifikasi(ArrayList<ModelNotifikasi.notifikasi> notifikasi) {
        this.notifikasi = notifikasi;
    }

    public class notifikasi{
        int id_notifikasi;
        String time, nama_notif, status, status_admin, status_manager, jenis, kode_sv, kode_mg, komentar, komentar_manager, accept_report;

        public int getId_notifikasi() {
            return id_notifikasi;
        }

        public void setId_notifikasi(int id_notifikasi) {
            this.id_notifikasi = id_notifikasi;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getNama_notif() {
            return nama_notif;
        }

        public void setNama_notif(String nama_notif) {
            this.nama_notif = nama_notif;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus_admin() {
            return status_admin;
        }

        public void setStatus_admin(String status_admin) {
            this.status_admin = status_admin;
        }

        public String getStatus_manager() {
            return status_manager;
        }

        public void setStatus_manager(String status_manager) {
            this.status_manager = status_manager;
        }

        public String getJenis() {
            return jenis;
        }

        public void setJenis(String jenis) {
            this.jenis = jenis;
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

        public String getKomentar() {
            return komentar;
        }

        public void setKomentar(String komentar) {
            this.komentar = komentar;
        }

        public String getKomentar_manager() {
            return komentar_manager;
        }

        public void setKomentar_manager(String komentar_manager) {
            this.komentar_manager = komentar_manager;
        }

        public String getAccept_report() {
            return accept_report;
        }

        public void setAccept_report(String accept_report) {
            this.accept_report = accept_report;
        }
    }
}
