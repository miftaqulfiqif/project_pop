package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelLogMaster {

    ArrayList<log_master> log_master;

    public ArrayList<ModelLogMaster.log_master> getLog_master() {
        return log_master;
    }

    public void setLog_master(ArrayList<ModelLogMaster.log_master> log_master) {
        this.log_master = log_master;
    }

    public class log_master{

        int id_logmaster;
        String log_master, nama, jenis, deskripsi, tanggal;

        public int getId_logmaster() {
            return id_logmaster;
        }

        public void setId_logmaster(int id_logmaster) {
            this.id_logmaster = id_logmaster;
        }

        public String getLog_master() {
            return log_master;
        }

        public void setLog_master(String log_master) {
            this.log_master = log_master;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getJenis() {
            return jenis;
        }

        public void setJenis(String jenis) {
            this.jenis = jenis;
        }

        public String getDeskripsi() {
            return deskripsi;
        }

        public void setDeskripsi(String deskripsi) {
            this.deskripsi = deskripsi;
        }

        public String getTanggal() {
            return tanggal;
        }

        public void setTanggal(String tanggal) {
            this.tanggal = tanggal;
        }
    }
}
