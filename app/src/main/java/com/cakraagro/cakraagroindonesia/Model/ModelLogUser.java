package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelLogUser {

    ArrayList<log_user> log_user;

    public ArrayList<ModelLogUser.log_user> getLog_user() {
        return log_user;
    }

    public void setLog_user(ArrayList<ModelLogUser.log_user> log_user) {
        this.log_user = log_user;
    }

    public static class log_user{
        String tanggal, nama, aktivitas, jenis;

        public String getTanggal() {
            return tanggal;
        }

        public void setTanggal(String tanggal) {
            this.tanggal = tanggal;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getAktivitas() {
            return aktivitas;
        }

        public void setAktivitas(String aktivitas) {
            this.aktivitas = aktivitas;
        }

        public String getJenis() {
            return jenis;
        }

        public void setJenis(String jenis) {
            this.jenis = jenis;
        }
    }
}
