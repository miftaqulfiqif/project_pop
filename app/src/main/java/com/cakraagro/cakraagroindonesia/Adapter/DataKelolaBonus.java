package com.cakraagro.cakraagroindonesia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cakraagro.cakraagroindonesia.Activity.BonusDistributor.UbahBonusDistributor;
import com.cakraagro.cakraagroindonesia.HapusData;
import com.cakraagro.cakraagroindonesia.Model.ModelBonus;
import com.example.cakraagroindonesia.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class DataKelolaBonus extends RecyclerView.Adapter<DataKelolaBonus.HolderData>{
    private Context context;
    private ArrayList<ModelBonus.Bonusdistributor> listData;

    private boolean setVisibility;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LAST = 1;

    public DataKelolaBonus(Context context, ArrayList<ModelBonus.Bonusdistributor> listData, boolean setVisibility) {
        this.context = context;
        this.listData = listData;
        this.setVisibility = setVisibility;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bonusdistributor,parent,false);
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
            ModelBonus.Bonusdistributor model = listData.get(position);

            holder.tanggal.setText(model.getTanggalbonus());
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
            if (model.getFoto_url() != null){
                Glide.with(context).load(model.getFoto_url()).into(holder.foto);
            }else {
                Glide.with(context).load(model.getFotopenjualan()).into(holder.foto);
            }
            holder.secretary.setText(model.getNama_secretary());
            holder.distributor.setText(model.getNama_distributor());

            String varTotalPenjualan = model.getTotalpenjualan();
            String varBonusPenjualan = model.getBonuspenjualan();
            String varBonusTahunan =  model.getBonustahunan();

            double totalpenjualan = Double.parseDouble(varTotalPenjualan);
            double bonusjual = Double.parseDouble(varBonusPenjualan);
            double bonustahun = Double.parseDouble(varBonusTahunan);

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            currencyFormat.setCurrency(Currency.getInstance("IDR"));

            String TotalPenjualan = currencyFormat.format(totalpenjualan);
            String BonusJual = currencyFormat.format(bonusjual);
            String BonusTahun = currencyFormat.format(bonustahun);

            holder.totalpenjualan.setText(TotalPenjualan);
            holder.bonuspenjualan.setText(BonusJual);
            holder.bonustahunan.setText(BonusTahun);
            holder.nomorinvoice.setText(model.getNomor_invoice());

            if (setVisibility == true){
                holder.edit.setVisibility(View.VISIBLE);
                holder.hapus.setVisibility(View.VISIBLE);

                holder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String varKodeBd = model.getKode_bd();
                        String varKodeMg = model.getKode_mg();
                        String varKodeSc = model.getKode_sc();
                        String varKodeDt = model.getKode_dt();
                        String varNamaDt = model.getNama_distributor();
                        String varFoto = model.getFotopenjualan();
                        String varTotal = model.getTotalpenjualan();
                        String varBonusJual = model.getBonuspenjualan();
                        String varBonusTahun = model.getBonustahunan();
                        String varNamaSc = model.getNama_secretary();
                        String varTgl = model.getTanggalbonus();
                        String varNomorInvoice = model.getNomor_invoice();

                        Intent edit = new Intent(context, UbahBonusDistributor.class);
                        edit.putExtra("xKodeBd", varKodeBd);
                        edit.putExtra("xKodeMg", varKodeMg);
                        edit.putExtra("xKodeSc", varKodeSc);
                        edit.putExtra("xKodeDt", varKodeDt);
                        edit.putExtra("xNamaDt", varNamaDt);
                        edit.putExtra("xFoto", varFoto);
                        edit.putExtra("xTotal", varTotal);
                        edit.putExtra("xBonusJual", varBonusJual);
                        edit.putExtra("xBonusTahun", varBonusTahun);
                        edit.putExtra("xNamaSc", varNamaSc);
                        edit.putExtra("xTgl", varTgl);
                        edit.putExtra("xNomorInvoice", varNomorInvoice);
                        context.startActivity(edit);
                    }
                });

                holder.hapus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Data = "BonusDistributor";

                        Intent hapus = new Intent(context, HapusData.class);
                        hapus.putExtra("IDhapus", model.getKode_bd());
                        hapus.putExtra("Data", Data);
                        context.startActivity(hapus);
                    }
                });
            }


        } else if (getItemViewType(position) == VIEW_TYPE_ITEM_LAST) {
            // Bind data for the last item
        }

    }

    @Override
    public int getItemCount() {
        return listData.size() + 1;
    }

    public class HolderData extends RecyclerView.ViewHolder{

        RelativeLayout btn;
        CardView detail;
        ImageView btndetail, hapus, edit, foto;
        TextView tanggal, secretary, distributor, totalpenjualan, bonuspenjualan, bonustahunan, nomorinvoice;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            btn = itemView.findViewById(R.id.btn);
            detail = itemView.findViewById(R.id.detail);
            btndetail = itemView.findViewById(R.id.btndetail);
            hapus = itemView.findViewById(R.id.hapus);
            edit = itemView.findViewById(R.id.edit);
            tanggal = itemView.findViewById(R.id.tanggal);
            secretary = itemView.findViewById(R.id.secretary);
            distributor = itemView.findViewById(R.id.distributor);
            totalpenjualan = itemView.findViewById(R.id.totalpenjualan);
            bonuspenjualan = itemView.findViewById(R.id.bonuspenjualan);
            bonustahunan = itemView.findViewById(R.id.bonustahunan);
            nomorinvoice = itemView.findViewById(R.id.nomorinvoice);
            foto = itemView.findViewById(R.id.foto);

        }
    }
    @Override
    public int getItemViewType(int position) {
        if (position == listData.size()) {
            return VIEW_TYPE_ITEM_LAST; // Tampilan terakhir
        }
        return VIEW_TYPE_ITEM; // Tampilan item biasa
    }
}
