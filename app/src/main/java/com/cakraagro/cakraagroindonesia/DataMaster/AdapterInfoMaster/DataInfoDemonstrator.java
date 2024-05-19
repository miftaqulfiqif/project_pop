package com.cakraagro.cakraagroindonesia.DataMaster.AdapterInfoMaster;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cakraagro.cakraagroindonesia.DataMaster.DetailInfoMaster.InfoDemonstrator;
import com.cakraagro.cakraagroindonesia.Model.ModelDemonstrator;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

public class DataInfoDemonstrator extends RecyclerView.Adapter<DataInfoDemonstrator.HolderData>{
    private Context context;
    protected ArrayList<ModelDemonstrator.demonstrator> listData;

    public DataInfoDemonstrator(Context context, ArrayList<ModelDemonstrator.demonstrator> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_infomaster,parent,false);
        return new HolderData(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ModelDemonstrator.demonstrator model = listData.get(position);
        Glide.with(context).load(model.getFotodemonstrator()).into(holder.foto);
        holder.kode.setText(model.getKode_ds());
        holder.nama.setText(model.getNama_demonstrator());
        holder.foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String varKodeDs = model.getKode_ds();
                String varNamaDs = model.getNama_demonstrator();
                String varUsername = model.getUsername();
                String varPassword = model.getPassword();
                String varTFoto = model.getFotodemonstrator();
                String varFoto = model.getFoto_url();
                String varNamaSv = model.getNama_supervisor();
                String varProvinsi = model.getProvinsi();
                String varKabupaten = model.getKabupaten();

                Intent kirim = new Intent(context, InfoDemonstrator.class);
                kirim.putExtra("xKodeDs", varKodeDs);
                kirim.putExtra("xNamaDs", varNamaDs);
                kirim.putExtra("xUsername", varUsername);
                kirim.putExtra("xPassword", varPassword);
                kirim.putExtra("xTFoto", varTFoto);
                kirim.putExtra("xFoto", varFoto);
                kirim.putExtra("xNamaSv", varNamaSv);
                kirim.putExtra("xProvinsi", varProvinsi);
                kirim.putExtra("xKabupaten", varKabupaten);
                context.startActivity(kirim);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class HolderData extends RecyclerView.ViewHolder{
        TextView kode,nama;
        ImageView foto;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.foto);
            nama = itemView.findViewById(R.id.nama);
            kode = itemView.findViewById(R.id.kode);
        }
    }
}
