package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelInsektisida {

    ArrayList<data_insektisida> data_insektisida;

    public ArrayList<ModelInsektisida.data_insektisida> getData_insektisida() {
        return data_insektisida;
    }

    public void setData_insektisida(ArrayList<ModelInsektisida.data_insektisida> data_insektisida) {
        this.data_insektisida = data_insektisida;
    }

    public class data_insektisida{
        int id_insektisida;
        String merk, penjelasan_produk, browsure, browsure_url;

        public int getId_insektisida() {
            return id_insektisida;
        }

        public void setId_insektisida(int id_insektisida) {
            this.id_insektisida = id_insektisida;
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
