package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelFaq {

    ArrayList<data_faq> data_faq;

    public ArrayList<ModelFaq.data_faq> getData_faq() {
        return data_faq;
    }

    public void setData_faq(ArrayList<ModelFaq.data_faq> data_faq) {
        this.data_faq = data_faq;
    }

    public static class data_faq{
        int id_faq;
        String judul, pertanyaan, jawaban, foto_faq, foto_url;

        public String getFoto_url() {
            return foto_url;
        }

        public void setFoto_url(String foto_url) {
            this.foto_url = foto_url;
        }

        public int getId_faq() {
            return id_faq;
        }

        public void setId_faq(int id_faq) {
            this.id_faq = id_faq;
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

        public String getFoto_faq() {
            return foto_faq;
        }

        public void setFoto_faq(String foto_faq) {
            this.foto_faq = foto_faq;
        }
    }
}
