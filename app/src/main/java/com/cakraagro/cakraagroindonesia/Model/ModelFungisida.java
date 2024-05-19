package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelFungisida {
    ArrayList<data_fungsida> data_fungsida;

    public ArrayList<ModelFungisida.data_fungsida> getData_fungsida() {
        return data_fungsida;
    }

    public void setData_fungsida(ArrayList<ModelFungisida.data_fungsida> data_fungsida) {
        this.data_fungsida = data_fungsida;
    }

    public class data_fungsida{
        int id_fungsida;
        String merk, penjelasan_produk, browsure, browsure_url;

        public int getId_fungsida() {
            return id_fungsida;
        }

        public void setId_fungsida(int id_fungsida) {
            this.id_fungsida = id_fungsida;
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

        public String getBrowsure_url() {
            return browsure_url;
        }

        public void setBrowsure_url(String browsure_url) {
            this.browsure_url = browsure_url;
        }
    }
}
