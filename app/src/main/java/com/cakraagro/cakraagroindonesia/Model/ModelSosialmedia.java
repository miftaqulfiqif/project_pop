package com.cakraagro.cakraagroindonesia.Model;

import java.util.ArrayList;

public class ModelSosialmedia {

    ArrayList<sosialmedia> sosialmedia;

    public ArrayList<ModelSosialmedia.sosialmedia> getSosialmedia() {
        return sosialmedia;
    }

    public void setSosialmedia(ArrayList<ModelSosialmedia.sosialmedia> sosialmedia) {
        this.sosialmedia = sosialmedia;
    }

    public class sosialmedia{
        String linkfacebook, linkinstagram, linkyoutube;
        int id_sosialmedia;

        public int getId_sosialmedia() {
            return id_sosialmedia;
        }

        public void setId_sosialmedia(int id_sosialmedia) {
            this.id_sosialmedia = id_sosialmedia;
        }

        public String getLinkfacebook() {
            return linkfacebook;
        }

        public void setLinkfacebook(String linkfacebook) {
            this.linkfacebook = linkfacebook;
        }

        public String getLinkinstagram() {
            return linkinstagram;
        }

        public void setLinkinstagram(String linkinstagram) {
            this.linkinstagram = linkinstagram;
        }

        public String getLinkyoutube() {
            return linkyoutube;
        }

        public void setLinkyoutube(String linkyoutube) {
            this.linkyoutube = linkyoutube;
        }
    }
}
