package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelBerita {

    ArrayList<data_berita> data_berita;

    public ArrayList<ModelBerita.data_berita> getData_berita() {
        return data_berita;
    }

    public void setData_berita(ArrayList<ModelBerita.data_berita> data_berita) {
        this.data_berita = data_berita;
    }

    public static class data_berita{
        int id_berita;
        String judul_berita, isi_berita;

        public int getId_berita() {
            return id_berita;
        }

        public void setId_berita(int id_berita) {
            this.id_berita = id_berita;
        }

        public String getJudul_berita() {
            return judul_berita;
        }

        public void setJudul_berita(String judul_berita) {
            this.judul_berita = judul_berita;
        }

        public String getIsi_berita() {
            return isi_berita;
        }

        public void setIsi_berita(String isi_berita) {
            this.isi_berita = isi_berita;
        }
    }
}
