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
import com.cakraagro.cakraagroindonesia.DataMaster.DetailInfoMaster.InfoSecretary;
import com.cakraagro.cakraagroindonesia.Model.ModelSecretary;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

public class DataInfoSecretary extends RecyclerView.Adapter<DataInfoSecretary.HolderData>{
    private Context context;
    protected ArrayList<ModelSecretary.secretary> listData;

    public DataInfoSecretary(Context context, ArrayList<ModelSecretary.secretary> listData) {
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
        ModelSecretary.secretary model = listData.get(position);
        Glide.with(context).load(model.getFoto_url()).into(holder.foto);
        holder.kode.setText(model.getKode_sc());
        holder.nama.setText(model.getNama_secretary());
        holder.foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String varFoto = model.getFoto_url();
                String varTFoto = model.getFotosecretary();
                String varKodeSc = model.getKode_sc();
                String varNamaSc = model.getKode_sc();
                String varUsername = model.getUsername();
                String varPassword = model.getPassword();

                Intent kirim = new Intent(context, InfoSecretary.class);
                kirim.putExtra("xFoto", varFoto);
                kirim.putExtra("xTFoto", varTFoto);
                kirim.putExtra("xKodeSc", varKodeSc);
                kirim.putExtra("xNamaSc", varNamaSc);
                kirim.putExtra("xUsername", varUsername);
                kirim.putExtra("xPassword", varPassword);
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
