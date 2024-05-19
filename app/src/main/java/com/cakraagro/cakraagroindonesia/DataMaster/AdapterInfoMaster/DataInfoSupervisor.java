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
import com.cakraagro.cakraagroindonesia.DataMaster.DetailInfoMaster.InfoSupervisor;
import com.cakraagro.cakraagroindonesia.Model.ModelSupervisor;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

public class DataInfoSupervisor extends RecyclerView.Adapter<DataInfoSupervisor.HolderData>{
    private Context context;
    protected ArrayList<ModelSupervisor.supervisor> listData;

    public DataInfoSupervisor(Context context, ArrayList<ModelSupervisor.supervisor> listData) {
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
        ModelSupervisor.supervisor model = listData.get(position);
        Glide.with(context).load(model.getFotosupervisor()).into(holder.foto);
        holder.kode.setText(model.getKode_sv());
        holder.nama.setText(model.getNama_supervisor());
        holder.foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String varFoto = model.getFoto_url();
                String varTFoto = model.getFotosupervisor();
                String varKodeSv = model.getKode_sv();
                String varNamaSv = model.getNama_supervisor();
                String varUsername = model.getUsername();
                String varPassword = model.getPassword();
                String varArea = model.getArea_sales();
                String varProvinsi = model.getProvinsi();
                String varBudget = model.getBudget_tersedia();

                Intent kirim = new Intent(context, InfoSupervisor.class);
                kirim.putExtra("xFoto", varFoto);
                kirim.putExtra("xTFoto", varTFoto);
                kirim.putExtra("xKodeSv", varKodeSv);
                kirim.putExtra("xNamaSv", varNamaSv);
                kirim.putExtra("xUsername", varUsername);
                kirim.putExtra("xPassword", varPassword);
                kirim.putExtra("xArea", varArea);
                kirim.putExtra("xProvinsi", varProvinsi);
                kirim.putExtra("xBudget", varBudget);
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
