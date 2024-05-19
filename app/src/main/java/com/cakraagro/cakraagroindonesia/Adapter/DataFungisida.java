package com.cakraagro.cakraagroindonesia.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cakraagro.cakraagroindonesia.Model.ModelFungisida;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;
import java.util.Locale;

public class DataFungisida extends RecyclerView.Adapter<DataFungisida.HolderData> {
    private Context context;
    private ArrayList<ModelFungisida.data_fungsida> listData;
    private ArrayList<ModelFungisida.data_fungsida> filteredList;
    private OnItemClickListner onItemClicked;

    public interface OnItemClickListner{
        void onItemClick(ModelFungisida.data_fungsida modelFungisida);
    }

    public DataFungisida(Context context, ArrayList<ModelFungisida.data_fungsida> listData, OnItemClickListner onItemClicked) {
        this.context = context;
        this.listData = listData;
        this.filteredList = new ArrayList<>(listData);
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fungisida,parent,false);
        return new HolderData(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ModelFungisida.data_fungsida model = listData.get(position);

        holder.id.setText(String.valueOf(model.getId_fungsida()));
        holder.penjelasanproduk.setText(model.getPenjelasan_produk());
        holder.merk.setText(model.getMerk());
        Glide.with(context).load(model.getBrowsure_url()).into(holder.gambarproduk);
        holder.gambarproduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClicked.onItemClick(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView id, penjelasanproduk, merk;
        ImageView gambarproduk;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            penjelasanproduk = itemView.findViewById(R.id.penjelasanproduk);
            merk = itemView.findViewById(R.id.merk);
            gambarproduk = itemView.findViewById(R.id.gambarproduk);
        }
    }

    public void filter(String searchText){
        searchText = searchText.toLowerCase(Locale.getDefault());
        listData.clear();

        if (TextUtils.isEmpty(searchText)){
            listData.addAll(filteredList);
        }else {
            for (ModelFungisida.data_fungsida model : filteredList){
                if (model.getMerk().toLowerCase(Locale.getDefault()).contains(searchText) || model.getPenjelasan_produk().toLowerCase(Locale.getDefault()).contains(searchText)){
                    listData.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }
}
