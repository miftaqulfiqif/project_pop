package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelPaketProduk {

    ArrayList<paket_produk> paket_produk;

    public ArrayList<ModelPaketProduk.paket_produk> getPaket_produk() {
        return paket_produk;
    }

    public void setPaket_produk(ArrayList<ModelPaketProduk.paket_produk> paket_produk) {
        this.paket_produk = paket_produk;
    }

    public static class paket_produk{
        int id_paket;
        String tanaman, paket_produk, iterasi, hasil, ppt, ppt_url;

        public String getPpt_url() {
            return ppt_url;
        }

        public void setPpt_url(String ppt_url) {
            this.ppt_url = ppt_url;
        }

        public int getId_paket() {
            return id_paket;
        }

        public void setId_paket(int id_paket) {
            this.id_paket = id_paket;
        }

        public String getTanaman() {
            return tanaman;
        }

        public void setTanaman(String tanaman) {
            this.tanaman = tanaman;
        }

        public String getPaket_produk() {
            return paket_produk;
        }

        public void setPaket_produk(String paket_produk) {
            this.paket_produk = paket_produk;
        }

        public String getIterasi() {
            return iterasi;
        }

        public void setIterasi(String iterasi) {
            this.iterasi = iterasi;
        }

        public String getHasil() {
            return hasil;
        }

        public void setHasil(String hasil) {
            this.hasil = hasil;
        }

        public String getPpt() {
            return ppt;
        }

        public void setPpt(String ppt) {
            this.ppt = ppt;
        }
    }
}
