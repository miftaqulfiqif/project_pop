package com.cakraagro.cakraagroindonesia.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cakraagro.cakraagroindonesia.Model.ModelAlamat;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

public class DataAlamat extends RecyclerView.Adapter<DataAlamat.HolderData>{
    private Context context;
    private ArrayList<ModelAlamat.alamat> listData;

    public DataAlamat(Context context, ArrayList<ModelAlamat.alamat> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alamat,parent,false);
        return new HolderData(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ModelAlamat.alamat model = listData.get(position);

        holder.namakantor.setText(model.getNama_kantor());
        holder.alamat.setText(model.getAlamat());
        holder.telepon.setText(model.getTelepon());
        Glide.with(context).load(model.getFoto_url()).into(holder.foto);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class HolderData extends RecyclerView.ViewHolder{
        TextView id, namakantor, alamat, telepon;
        ImageView foto;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idalamat);
            namakantor = itemView.findViewById(R.id.namakantor);
            alamat = itemView.findViewById(R.id.alamat);
            telepon = itemView.findViewById(R.id.telepon);
            foto = itemView.findViewById(R.id.foto);
        }
    }
}
