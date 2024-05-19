package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelPgr {
    ArrayList<data_pgr> data_pgr;

    public ArrayList<ModelPgr.data_pgr> getData_pgr() {
        return data_pgr;
    }

    public void setdata_pgr(ArrayList<ModelPgr.data_pgr> data_pgr) {
        this.data_pgr = data_pgr;
    }

    public class data_pgr{
        int id_pgr;
        String merk, penjelasan_produk, browsure, browsure_url;

        public String getBrowsure_url() {
            return browsure_url;
        }

        public void setBrowsure_url(String browsure_url) {
            this.browsure_url = browsure_url;
        }

        public int getId_pgr() {
            return id_pgr;
        }

        public void setId_pgr(int id_pgr) {
            this.id_pgr = id_pgr;
        }

        public String getMerk() {
            return merk;
        }

        public void setMerk(String merk) {
            this.merk = merk;
        }

        public String getPenjelasan_produk() {
            return penjelasan_produk;
        }

        public void setPenjelasan_produk(String penjelasan_produk) {
            this.penjelasan_produk = penjelasan_produk;
        }

        public String getBrowsure() {
            return browsure;
        }

        public void setBrowsure(String browsure) {
            this.browsure = browsure;
        }
    }
}
