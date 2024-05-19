package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelHerbisida {

    ArrayList<data_herbisida> data_herbisida;

    public ArrayList<ModelHerbisida.data_herbisida> getData_herbisida() {
        return data_herbisida;
    }

    public void setData_herbisida(ArrayList<ModelHerbisida.data_herbisida> data_herbisida) {
        this.data_herbisida = data_herbisida;
    }

    public class data_herbisida{
        int id_herbisida;
        String merk, penjelasan_produk, browsure, browsure_url;

        public int getId_herbisida() {
            return id_herbisida;
        }

        public void setId_herbisida(int id_herbisida) {
            this.id_herbisida = id_herbisida;
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
