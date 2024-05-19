package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelProdukHomepage {

    ArrayList<produk_beranda> produk_beranda;

    public ArrayList<ModelProdukHomepage.produk_beranda> getProduk_beranda() {
        return produk_beranda;
    }

    public void setProduk_beranda(ArrayList<ModelProdukHomepage.produk_beranda> produk_beranda) {
        this.produk_beranda = produk_beranda;
    }

    public static class produk_beranda{
        int id_produkberanda;
        String merk,deskripsi,foto,foto_url;

        public int getId_produkberanda() {
            return id_produkberanda;
        }

        public void setId_produkberanda(int id_produkberanda) {
            this.id_produkberanda = id_produkberanda;
        }

        public String getMerk() {
            return merk;
        }

        public void setMerk(String merk) {
            this.merk = merk;
        }

        public String getDeskripsi() {
            return deskripsi;
        }

        public void setDeskripsi(String deskripsi) {
            this.deskripsi = deskripsi;
        }

        public String getFoto() {
            return foto;
        }

        public void setFoto(String foto) {
            this.foto = foto;
        }

        public String getFoto_url() {
            return foto_url;
        }

        public void setFoto_url(String foto_url) {
            this.foto_url = foto_url;
        }
    }
}
