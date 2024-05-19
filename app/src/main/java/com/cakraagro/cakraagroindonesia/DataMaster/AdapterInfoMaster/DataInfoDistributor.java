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
import com.cakraagro.cakraagroindonesia.DataMaster.DetailInfoMaster.InfoDistributor;
import com.cakraagro.cakraagroindonesia.Model.ModelDistributor;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

public class DataInfoDistributor extends RecyclerView.Adapter<DataInfoDistributor.HolderData>{
    private Context context;
    protected ArrayList<ModelDistributor.distributor> listData;

    public DataInfoDistributor(Context context, ArrayList<ModelDistributor.distributor> listData) {
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
        ModelDistributor.distributor model = listData.get(position);
        Glide.with(context).load(model.getFotodistributor()).into(holder.foto);
        holder.kode.setText(model.getKode_dt());
        holder.nama.setText(model.getNama_distributor());
        holder.foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String varFoto = model.getFotodistributor();
                String varKodeDt = model.getKode_dt();
                String varNamaDt = model.getNama_distributor();
                String varPerusahaan = model.getPerusahaan();
                String varUsername = model.getUsername();
                String varPassword = model.getPassword();

                Intent kirim = new Intent(context, InfoDistributor.class);
                kirim.putExtra("xFoto", varFoto);
                kirim.putExtra("xKodeDt", varKodeDt);
                kirim.putExtra("xNamaDt", varNamaDt);
                kirim.putExtra("xUsername", varUsername);
                kirim.putExtra("xPassword", varPassword);
                kirim.putExtra("xPerusahaan", varPerusahaan);
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
