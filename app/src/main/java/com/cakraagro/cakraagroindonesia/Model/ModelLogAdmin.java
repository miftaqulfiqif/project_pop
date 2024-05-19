package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelLogAdmin {

    ArrayList<log_admin> log_admin;

    public ArrayList<ModelLogAdmin.log_admin> getLog_admin() {
        return log_admin;
    }

    public void setLog_admin(ArrayList<ModelLogAdmin.log_admin> log_admin) {
        this.log_admin = log_admin;
    }

    public class log_admin{
        int id_logadmin;
        String id_admin, nama, deskripsi, tanggal;

        public int getId_logadmin() {
            return id_logadmin;
        }

        public void setId_logadmin(int id_logadmin) {
            this.id_logadmin = id_logadmin;
        }

        public String getId_admin() {
            return id_admin;
        }

        public void setId_admin(String id_admin) {
            this.id_admin = id_admin;
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
