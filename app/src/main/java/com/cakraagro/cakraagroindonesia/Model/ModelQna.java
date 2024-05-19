package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelQna {
    ArrayList<data_qna> data_qna;

    public ArrayList<ModelQna.data_qna> getData_qna() {
        return data_qna;
    }

    public void setData_qna(ArrayList<ModelQna.data_qna> data_qna) {
        this.data_qna = data_qna;
    }

    public static class data_qna{
        int id_qna;
        String judul, pertanyaan, jawaban, foto_qna, nama, telepon, tanggal, status, foto_url;

        public String getFoto_url() {
            return foto_url;
        }

        public void setFoto_url(String foto_url) {
            this.foto_url = foto_url;
        }

        public int getId_qna() {
            return id_qna;
        }

        public void setId_qna(int id_qna) {
            this.id_qna = id_qna;
        }

        public String getJudul() {
            return judul;
        }

        public void setJudul(String judul) {
            this.judul = judul;
        }

        public String getPertanyaan() {
            return pertanyaan;
        }

        public void setPertanyaan(String pertanyaan) {
            this.pertanyaan = pertanyaan;
        }

        public String getJawaban() {
            return jawaban;
        }

        public void setJawaban(String jawaban) {
            this.jawaban = jawaban;
        }

        public String getFoto_qna() {
            return foto_qna;
        }

        public void setFoto_qna(String foto_qna) {
            this.foto_qna = foto_qna;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getTelepon() {
            return telepon;
        }

        public void setTelepon(String telepon) {
            this.telepon = telepon;
        }

        public String getTanggal() {
            return tanggal;
        }

        public void setTanggal(String tanggal) {
            this.tanggal = tanggal;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
