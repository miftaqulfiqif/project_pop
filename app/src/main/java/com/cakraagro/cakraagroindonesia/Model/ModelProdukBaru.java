package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelProdukBaru {

    ArrayList<produk_baru> produk_baru;

    public ArrayList<ModelProdukBaru.produk_baru> getProduk_baru() {
        return produk_baru;
    }

    public void setProduk_baru(ArrayList<ModelProdukBaru.produk_baru> produk_baru) {
        this.produk_baru = produk_baru;
    }

    public static class produk_baru{
        int id_produk;
        String nama_bahan, formulasi, tanaman;

        public int getId_produk() {
            return id_produk;
        }

        public void setId_produk(int id_produk) {
            this.id_produk = id_produk;
        }

        public String getNama_bahan() {
            return nama_bahan;
        }

        public void setNama_bahan(String nama_bahan) {
            this.nama_bahan = nama_bahan;
        }

        public String getFormulasi() {
            return formulasi;
        }

        public void setFormulasi(String formulasi) {
            this.formulasi = formulasi;
        }

        public String getTanaman() {
            return tanaman;
        }

        public void setTanaman(String tanaman) {
            this.tanaman = tanaman;
        }
    }
}
