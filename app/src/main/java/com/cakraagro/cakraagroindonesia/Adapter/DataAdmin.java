package com.cakraagro.cakraagroindonesia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Activity.KelolaDataMaster.UbahDataAdmin;
import com.cakraagro.cakraagroindonesia.HapusData;
import com.cakraagro.cakraagroindonesia.Model.ModelAdmin;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceAdmin;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataAdmin extends RecyclerView.Adapter<DataAdmin.HolderData> {
    private Context context;
    private ArrayList<ModelAdmin.data_admin> listData;

    private float currentRotation = 0;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LAST = 1;

    public DataAdmin(Context context, ArrayList<ModelAdmin.data_admin> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kelolaadmin,parent,false);
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
            ModelAdmin.data_admin model = listData.get(position);

            holder.Nama.setText(model.getNama());
            holder.Id.setText(model.getId_admin());
            holder.Username.setText(model.getUsername());
            holder.Password.setText(model.getPassword());

            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceAdmin admin = RetroServer.KonesiAPI(context).create(InterfaceAdmin.class);
                    Call<ModelAdmin> kirim = admin.getEdit(model.getId_admin());
                    kirim.enqueue(new Callback<ModelAdmin>() {
                        @Override
                        public void onResponse(Call<ModelAdmin> call, Response<ModelAdmin> response) {

                            String varId = model.getId_admin();
                            String varNama = model.getNama();
                            String varUsername = model.getUsername();
                            String varPassword = model.getPassword();

                            Intent kirim = new Intent(context, UbahDataAdmin.class);
                            kirim.putExtra("xId", varId);
                            kirim.putExtra("xNama", varNama);
                            kirim.putExtra("xUsername", varUsername);
                            kirim.putExtra("xPassword", varPassword);
                            context.startActivity(kirim);

                        }

                        @Override
                        public void onFailure(Call<ModelAdmin> call, Throwable t) {

                        }
                    });
                }
            });
            holder.btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Data = "Admin";
                    Intent hapus = new Intent(context, HapusData.class);
                    hapus.putExtra("IDhapus", model.getId_admin());
                    hapus.putExtra("Data", Data);
                    context.startActivity(hapus);
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
        TextView Nama, Id, Username, Password;
        ImageView btnHapus, btnEdit;
        public HolderData(@NonNull View itemView) {
            super(itemView);

            Nama = itemView.findViewById(R.id.nama);
            Id = itemView.findViewById(R.id.id);
            Username = itemView.findViewById(R.id.username);
            Password = itemView.findViewById(R.id.password);
            btnHapus = itemView.findViewById(R.id.btnhapus);
            btnEdit = itemView.findViewById(R.id.btnedit);
        }
    }

    public String getLastId(){
        if (listData != null && listData.size() > 0){
            int lastIndex = listData.size()-1;
            ModelAdmin.data_admin lastData = listData.get(lastIndex);
            return lastData.getId_admin();
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == listData.size()) {
            return VIEW_TYPE_ITEM_LAST; // Tampilan terakhir
        }
        return VIEW_TYPE_ITEM; // Tampilan item biasa
    }
}
