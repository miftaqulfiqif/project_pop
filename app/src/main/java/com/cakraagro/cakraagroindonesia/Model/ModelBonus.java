package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelBonus {

    ArrayList<Bonusdistributor> Bonusdistributor;

    public ArrayList<ModelBonus.Bonusdistributor> getBosnusdistributor() {
        return Bonusdistributor;
    }

    public void setBosnusdistributor(ArrayList<ModelBonus.Bonusdistributor> bosnusdistributor) {
        this.Bonusdistributor = Bonusdistributor;
    }

    public class Bonusdistributor{
        String kode_bd, kode_mg, kode_sc, kode_dt, nama_distributor, fotopenjualan, totalpenjualan, bonuspenjualan, bonustahunan, nama_secretary, tanggalbonus, nomor_invoice, foto_url;

        public String getFoto_url() {
            return foto_url;
        }

        public void setFoto_url(String foto_url) {
            this.foto_url = foto_url;
        }

        public String getKode_bd() {
            return kode_bd;
        }

        public void setKode_bd(String kode_bd) {
            this.kode_bd = kode_bd;
        }

        public String getKode_mg() {
            return kode_mg;
        }

        public void setKode_mg(String kode_mg) {
            this.kode_mg = kode_mg;
        }

        public String getKode_sc() {
            return kode_sc;
        }

        public void setKode_sc(String kode_sc) {
            this.kode_sc = kode_sc;
        }

        public String getKode_dt() {
            return kode_dt;
        }

        public void setKode_dt(String kode_dt) {
            this.kode_dt = kode_dt;
        }

        public String getNama_distributor() {
            return nama_distributor;
        }

        public void setNama_distributor(String nama_distributor) {
            this.nama_distributor = nama_distributor;
        }

        public String getFotopenjualan() {
            return fotopenjualan;
        }

        public void setFotopenjualan(String fotopenjualan) {
            this.fotopenjualan = fotopenjualan;
        }

        public String getTotalpenjualan() {
            return totalpenjualan;
        }

        public void setTotalpenjualan(String totalpenjualan) {
            this.totalpenjualan = totalpenjualan;
        }

        public String getBonuspenjualan() {
            return bonuspenjualan;
        }

        public void setBonuspenjualan(String bonuspenjualan) {
            this.bonuspenjualan = bonuspenjualan;
        }

        public String getBonustahunan() {
            return bonustahunan;
        }

        public void setBonustahunan(String bonustahunan) {
            this.bonustahunan = bonustahunan;
        }

        public String getNama_secretary() {
            return nama_secretary;
        }

        public void setNama_secretary(String nama_secretary) {
            this.nama_secretary = nama_secretary;
        }

        public String getTanggalbonus() {
            return tanggalbonus;
        }

        public void setTanggalbonus(String tanggalbonus) {
            this.tanggalbonus = tanggalbonus;
        }

        public String getNomor_invoice() {
            return nomor_invoice;
        }

        public void setNomor_invoice(String nomor_invoice) {
            this.nomor_invoice = nomor_invoice;
        }
    }
}
