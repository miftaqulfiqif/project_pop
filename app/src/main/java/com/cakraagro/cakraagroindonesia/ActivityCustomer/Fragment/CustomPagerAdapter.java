package com.cakraagro.cakraagroindonesia.ActivityCustomer.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cakraagro.cakraagroindonesia.Model.ModelProdukHomepage;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

public class CustomPagerAdapter extends RecyclerView.Adapter<CustomPagerAdapter.ViewHolder> {

    private ArrayList<ModelProdukHomepage.produk_beranda> listData;

    public CustomPagerAdapter(ArrayList<ModelProdukHomepage.produk_beranda> listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.displayproduk, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelProdukHomepage.produk_beranda model = listData.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView foto;
        private TextView merk;
        private TextView penjelasanproduk;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.foto);
            merk = itemView.findViewById(R.id.merk);
            penjelasanproduk = itemView.findViewById(R.id.penjelasanproduk);
        }

        public void bind(ModelProdukHomepage.produk_beranda model) {
            Glide.with(itemView.getContext()).load(model.getFoto_url()).into(foto);
            merk.setText(model.getMerk());
            penjelasanproduk.setText(model.getDeskripsi());
        }
    }
}
