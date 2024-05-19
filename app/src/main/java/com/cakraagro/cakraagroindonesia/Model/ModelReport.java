package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelReport {

    ArrayList<report> report;

    public ArrayList<ModelReport.report> getReport() {
        return report;
    }

    public void setReport(ArrayList<ModelReport.report> report) {
        this.report = report;
    }

    public static class report{
        String kode_report, kode_ds, kode_sv, kode_mg, nama_demonstrator, nama_supervisor, kabupaten_ds, provinsi_ds, nama_petani, tanggal_demplot, nomor_telpon, desa, kecamatan, kabupaten, tanaman, produk, dosis, foto_buku, result, status, status_apps, foto_url;

        public String getKabupaten() {
            return kabupaten;
        }

        public void setKabupaten(String kabupaten) {
            this.kabupaten = kabupaten;
        }

        public String getFoto_url() {
            return foto_url;
        }

        public void setFoto_url(String foto_url) {
            this.foto_url = foto_url;
        }

        public String getKode_report() {
            return kode_report;
        }

        public void setKode_report(String kode_report) {
            this.kode_report = kode_report;
        }

        public String getKode_ds() {
            return kode_ds;
        }

        public void setKode_ds(String kode_ds) {
            this.kode_ds = kode_ds;
        }

        public String getKode_sv() {
            return kode_sv;
        }

        public void setKode_sv(String kode_sv) {
            this.kode_sv = kode_sv;
        }

        public String getKode_mg() {
            return kode_mg;
        }

        public void setKode_mg(String kode_mg) {
            this.kode_mg = kode_mg;
        }

        public String getNama_demonstrator() {
            return nama_demonstrator;
        }

        public void setNama_demonstrator(String nama_demonstrator) {
            this.nama_demonstrator = nama_demonstrator;
        }

        public String getNama_supervisor() {
            return nama_supervisor;
        }

        public void setNama_supervisor(String nama_supervisor) {
            this.nama_supervisor = nama_supervisor;
        }

        public String getKabupaten_ds() {
            return kabupaten_ds;
        }

        public void setKabupaten_ds(String kabupaten_ds) {
            this.kabupaten_ds = kabupaten_ds;
        }

        public String getProvinsi_ds() {
            return provinsi_ds;
        }

        public void setProvinsi_ds(String provinsi_ds) {
            this.provinsi_ds = provinsi_ds;
        }

        public String getNama_petani() {
            return nama_petani;
        }

        public void setNama_petani(String nama_petani) {
            this.nama_petani = nama_petani;
        }

        public String getTanggal_demplot() {
            return tanggal_demplot;
        }

        public void setTanggal_demplot(String tanggal_demplot) {
            this.tanggal_demplot = tanggal_demplot;
        }

        public String getNomor_telpon() {
            return nomor_telpon;
        }

        public void setNomor_telpon(String nomor_telepon) {
            this.nomor_telpon = nomor_telepon;
        }

        public String getDesa() {
            return desa;
        }

        public void setDesa(String desa) {
            this.desa = desa;
        }

        public String getKecamatan() {
            return kecamatan;
        }

        public void setKecamatan(String kecamatan) {
            this.kecamatan = kecamatan;
        }

        public String getTanaman() {
            return tanaman;
        }

        public void setTanaman(String tanaman) {
            this.tanaman = tanaman;
        }

        public String getProduk() {
            return produk;
        }

        public void setProduk(String produk) {
            this.produk = produk;
        }

        public String getDosis() {
            return dosis;
        }

        public void setDosis(String dosis) {
            this.dosis = dosis;
        }

        public String getFoto_buku() {
            return foto_buku;
        }

        public void setFoto_buku(String foto_buku) {
            this.foto_buku = foto_buku;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus_apps() {
            return status_apps;
        }

        public void setStatus_apps(String status_apps) {
            this.status_apps = status_apps;
        }
    }
}
