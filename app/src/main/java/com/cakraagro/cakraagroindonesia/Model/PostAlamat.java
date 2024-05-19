package com.cakraagro.cakraagroindonesia.Model;

import com.google.gson.annotations.SerializedName;

public class PostAlamat {
    @SerializedName("alamat")
    Alamat dataAlamat;

    public Alamat getDataAlamat() {
        return dataAlamat;
    }

    public void setDataAlamat(Alamat dataAlamat) {
        this.dataAlamat = dataAlamat;
    }
}
