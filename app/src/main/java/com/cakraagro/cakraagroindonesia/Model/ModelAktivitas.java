package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelAktivitas {

    ArrayList<aktivitas> aktivitas;

    public ArrayList<ModelAktivitas.aktivitas> getAktivitas() {
        return aktivitas;
    }

    public void setAktivitas(ArrayList<ModelAktivitas.aktivitas> aktivitas) {
        this.aktivitas = aktivitas;
    }

    public static class aktivitas{
        String kode_ak, nama_supervisor, nama_manager, kode_mg, area_sales, provinsi, nama_kegiatan, tanggal_kegiatan, jumlah_partisipan, produk, nama_kios, budget, foto_kegiatan, kode_sv, lokasi, status, starus_apps, foto_url;

        public String getKode_ak() {
            return kode_ak;
        }

        public void setKode_ak(String kode_ak) {
            this.kode_ak = kode_ak;
        }

        public String getNama_supervisor() {
            return nama_supervisor;
        }

        public void setNama_supervisor(String nama_supervisor) {
            this.nama_supervisor = nama_supervisor;
        }

        public String getNama_manager() {
            return nama_manager;
        }

        public void setNama_manager(String nama_manager) {
            this.nama_manager = nama_manager;
        }

        public String getKode_mg() {
            return kode_mg;
        }

        public void setKode_mg(String kode_mg) {
            this.kode_mg = kode_mg;
        }

        public String getArea_sales() {
            return area_sales;
        }

        public void setArea_sales(String area_sales) {
            this.area_sales = area_sales;
        }

        public String getProvinsi() {
            return provinsi;
        }

        public void setProvinsi(String provinsi) {
            this.provinsi = provinsi;
        }

        public String getNama_kegiatan() {
            return nama_kegiatan;
        }

        public void setNama_kegiatan(String nama_kegiatan) {
            this.nama_kegiatan = nama_kegiatan;
        }

        public String getTanggal_kegiatan() {
            return tanggal_kegiatan;
        }

        public void setTanggal_kegiatan(String tanggal_kegiatan) {
            this.tanggal_kegiatan = tanggal_kegiatan;
        }

        public String getJumlah_partisipan() {
            return jumlah_partisipan;
        }

        public void setJumlah_partisipan(String jumlah_partisipan) {
            this.jumlah_partisipan = jumlah_partisipan;
        }

        public String getProduk() {
            return produk;
        }

        public void setProduk(String produk) {
            this.produk = produk;
        }

        public String getNama_kios() {
            return nama_kios;
        }

        public void setNama_kios(String nama_kios) {
            this.nama_kios = nama_kios;
        }

        public String getBudget() {
            return budget;
        }

        public void setBudget(String budget) {
            this.budget = budget;
        }

        public String getFoto_kegiatan() {
            return foto_kegiatan;
        }

        public void setFoto_kegiatan(String foto_kegiatan) {
            this.foto_kegiatan = foto_kegiatan;
        }

        public String getKode_sv() {
            return kode_sv;
        }

        public void setKode_sv(String kode_sv) {
            this.kode_sv = kode_sv;
        }

        public String getLokasi() {
            return lokasi;
        }

        public void setLokasi(String lokasi) {
            this.lokasi = lokasi;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStarus_apps() {
            return starus_apps;
        }

        public void setStarus_apps(String starus_apps) {
            this.starus_apps = starus_apps;
        }

        public String getFoto_url() {
            return foto_url;
        }

        public void setFoto_url(String foto_url) {
            this.foto_url = foto_url;
        }
    }
}
