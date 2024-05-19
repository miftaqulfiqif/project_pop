package com.cakraagro.cakraagroindonesia.Model;

public class ItemProduk {
    int gambar;
    String namaProduk;

    public ItemProduk(int gambar, String namaProduk) {
        this.gambar = gambar;
        this.namaProduk = namaProduk;
    }

    public int getGambar() {
        return gambar;
    }

    public String getNamaProduk() {
        return namaProduk;
    }
}
