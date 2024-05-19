package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelLogSuperadmin {

    ArrayList<log_superadmin> log_superadmin;

    public ArrayList<ModelLogSuperadmin.log_superadmin> getLog_superadmin() {
        return log_superadmin;
    }

    public void setLog_superadmin(ArrayList<ModelLogSuperadmin.log_superadmin> log_superadmin) {
        this.log_superadmin = log_superadmin;
    }

    public class log_superadmin{
        int id_logsuperadmin;
        String id_superadmin, nama, deskripsi, tanggal;

        public int getId_logsuperadmin() {
            return id_logsuperadmin;
        }

        public void setId_logsuperadmin(int id_logsuperadmin) {
            this.id_logsuperadmin = id_logsuperadmin;
        }

        public String getId_superadmin() {
            return id_superadmin;
        }

        public void setId_superadmin(String id_superadmin) {
            this.id_superadmin = id_superadmin;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
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
