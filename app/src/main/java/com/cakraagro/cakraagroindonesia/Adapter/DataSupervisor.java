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
import com.cakraagro.cakraagroindonesia.Activity.KelolaDataMaster.UbahDataSupervisor;
import com.cakraagro.cakraagroindonesia.HapusData;
import com.cakraagro.cakraagroindonesia.Model.ModelSupervisor;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSupervisor;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSupervisor extends RecyclerView.Adapter<DataSupervisor.HolderData>{
    private Context context;
    private ArrayList<ModelSupervisor.supervisor> listData;

    private float currentRotation = 0;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LAST = 1;

    public DataSupervisor(Context context, ArrayList<ModelSupervisor.supervisor> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kelolasupervisor,parent,false);
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
            ModelSupervisor.supervisor model = listData.get(position);

            holder.nama.setText(model.getNama_supervisor());
            holder.supervisor.setText(model.getNama_supervisor());
            holder.id.setText(model.getKode_sv());
            holder.budget.setText(model.getBudget_tersedia());
            holder.areaSales.setText(model.getArea_sales());
            holder.provinsi.setText(model.getProvinsi());
            holder.manager.setText(model.getNama_manager());
            holder.username.setText(model.getUsername());
            holder.password.setText(model.getPassword());
            Glide.with(context).load(model.getFoto_url()).into(holder.foto);

            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceSupervisor interfaceSupervisor = RetroServer.KonesiAPI(context).create(InterfaceSupervisor.class);
                    Call<ModelSupervisor> kirim = interfaceSupervisor.getEdit(model.getKode_sv());
                    kirim.enqueue(new Callback<ModelSupervisor>() {
                        @Override
                        public void onResponse(Call<ModelSupervisor> call, Response<ModelSupervisor> response) {
                            String varId = model.getKode_sv();
                            String varNama = model.getNama_supervisor();
                            String varNamaManager = model.getNama_manager();
                            String varBudget = model.getBudget_tersedia();
                            String varAreaSales = model.getArea_sales();
                            String varProvinsi = model.getProvinsi();
                            String varUsername = model.getUsername();
                            String varPassword = model.getPassword();
                            String varTFoto = model.getFoto_url();
                            String varFoto = model.getFotosupervisor();

                            Intent kirim = new Intent(context, UbahDataSupervisor.class);
                            kirim.putExtra("xId",varId);
                            kirim.putExtra("xNama",varNama);
                            kirim.putExtra("xNamaManager",varNamaManager);
                            kirim.putExtra("xBudget",varBudget);
                            kirim.putExtra("xAreaSales",varAreaSales);
                            kirim.putExtra("xProvinsi",varProvinsi);
                            kirim.putExtra("xUsername",varUsername);
                            kirim.putExtra("xPassword",varPassword);
                            kirim.putExtra("xTFoto",varTFoto);
                            kirim.putExtra("xFoto",varFoto);
                            context.startActivity(kirim);
                        }

                        @Override
                        public void onFailure(Call<ModelSupervisor> call, Throwable t) {

                        }
                    });

                }
            });
            holder.btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Data = "Supervisor";

                    Intent hapus = new Intent(context, HapusData.class);
                    hapus.putExtra("IDhapus", model.getKode_sv());
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
        TextView nama, id, supervisor, manager, budget, areaSales, provinsi, username, password;
        ImageView btnEdit, btnHapus, foto, btndetail;
        RelativeLayout btn;
        LinearLayout show,detail;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            foto = itemView.findViewById(R.id.foto);
            id = itemView.findViewById(R.id.id);
            supervisor = itemView.findViewById(R.id.supervisor);
            manager = itemView.findViewById(R.id.manager);
            budget = itemView.findViewById(R.id.budget);
            areaSales = itemView.findViewById(R.id.areasales);
            provinsi = itemView.findViewById(R.id.provinsi);
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
            ModelSupervisor.supervisor lastData = listData.get(lastIndex);
            return lastData.getKode_sv();
        }
        return null;
    }
}
