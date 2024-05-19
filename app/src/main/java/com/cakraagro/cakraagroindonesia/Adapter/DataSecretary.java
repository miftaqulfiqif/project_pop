package com.cakraagro.cakraagroindonesia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Activity.KelolaDataMaster.UbahDataSecretary;
import com.cakraagro.cakraagroindonesia.HapusData;
import com.cakraagro.cakraagroindonesia.Model.ModelSecretary;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSecretary;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSecretary extends RecyclerView.Adapter<DataSecretary.HolderData>{
    private Context context;
    private ArrayList<ModelSecretary.secretary> listData;

    private float currentRotation = 0;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LAST = 1;

    public DataSecretary(Context context, ArrayList<ModelSecretary.secretary> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kelolasecretary,parent,false);
            return new HolderData(layout);
        } else if (viewType == VIEW_TYPE_ITEM_LAST) {
            View footer = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer,parent,false);
            return new HolderData(footer);
        }

        throw new IllegalArgumentException("Invalid view type");

    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            // Bind item data
            ModelSecretary.secretary model = listData.get(position);

            holder.nama.setText(model.getNama_secretary());
            holder.id.setText(model.getKode_sc());
            holder.secretary.setText(model.getNama_secretary());
            holder.manager.setText(model.getNama_manager());
            holder.username.setText(model.getUsername());
            holder.password.setText(model.getPassword());
            Glide.with(context).load(model.getFoto_url()).into(holder.foto);

            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceSecretary interfaceSecretary = RetroServer.KonesiAPI(context).create(InterfaceSecretary.class);
                    Call<ModelSecretary> kirim = interfaceSecretary.getEdit(model.getKode_sc());
                    kirim.enqueue(new Callback<ModelSecretary>() {
                        @Override
                        public void onResponse(Call<ModelSecretary> call, Response<ModelSecretary> response) {
                            String varId = model.getKode_sc();
                            String varNama = model.getNama_secretary();
                            String varNamaManager = model.getNama_manager();
                            String varUsername = model.getUsername();
                            String varPassword = model.getPassword();
                            String varTFoto = model.getFoto_url();
                            String varFoto = model.getFotosecretary();

                            Intent kirim = new Intent(context, UbahDataSecretary.class);
                            kirim.putExtra("xId", varId);
                            kirim.putExtra("xNama", varNama);
                            kirim.putExtra("xNamaManager", varNamaManager);
                            kirim.putExtra("xUsername", varUsername);
                            kirim.putExtra("xPassword", varPassword);
                            kirim.putExtra("xTFoto", varTFoto);
                            kirim.putExtra("xFoto", varFoto);
                            context.startActivity(kirim);
                        }

                        @Override
                        public void onFailure(Call<ModelSecretary> call, Throwable t) {

                        }
                    });
                }
            });
            holder.btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Data = "Secretary";
                    Intent hapus = new Intent(context, HapusData.class);
                    hapus.putExtra("IDhapus", model.getKode_sc());
                    hapus.putExtra("Data", Data);
                    context.startActivity(hapus);
                }
            });
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int invisible = holder.detail.getVisibility();
                    if (invisible == View.VISIBLE) {
                        // Jika LinearLayout terlihat, ubah menjadi GONE
                        holder.detail.setVisibility(View.GONE);
                        holder.btndetail.setRotation(0);
                    } else {
                        // Jika LinearLayout tidak terlihat, ubah menjadi VISIBLE
                        holder.detail.setVisibility(View.VISIBLE);
                        holder.btndetail.setRotation(+180);
                    }
                }
            });
        } else if (getItemViewType(position) == VIEW_TYPE_ITEM_LAST) {
            // Bind data for the last item
        }
    }

    @Override
    public int getItemCount() {
        return listData.size() + 1;
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView nama,id,secretary,manager,username,password;
        ImageView btnEdit, btnHapus, foto, btndetail;
        RelativeLayout btn;
        LinearLayout show,detail;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            foto = itemView.findViewById(R.id.foto);
            id = itemView.findViewById(R.id.id);
            secretary = itemView.findViewById(R.id.secretary);
            manager = itemView.findViewById(R.id.manager);
            username = itemView.findViewById(R.id.username);
            password = itemView.findViewById(R.id.password);
            btnEdit = itemView.findViewById(R.id.btnedit);
            btnHapus = itemView.findViewById(R.id.btnhapus);
            detail = itemView.findViewById(R.id.detail);
            btndetail = itemView.findViewById(R.id.btndetail);
            btn = itemView.findViewById(R.id.btn);
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (position == listData.size()) {
            return VIEW_TYPE_ITEM_LAST; // Tampilan terakhir
        }
        return VIEW_TYPE_ITEM; // Tampilan item biasa
    }
    public String getLastId(){
        if (listData != null && listData.size() > 0){
            int lastIndex = listData.size()-1;
            ModelSecretary.secretary lastData = listData.get(lastIndex);
            return lastData.getKode_sc();
        }
        return null;
    }
}
