package com.cakraagro.cakraagroindonesia.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cakraagro.cakraagroindonesia.Model.ModelQna;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

public class DataQna extends RecyclerView.Adapter<DataQna.HolderData>{
    private Context context;
    private ArrayList<ModelQna.data_qna> listData;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LAST = 1;

    public DataQna(Context context, ArrayList<ModelQna.data_qna> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qna,parent,false);
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
            ModelQna.data_qna model = listData.get(position);

            holder.pertanyaan.setText(model.getPertanyaan());
            holder.nama.setText(model.getNama());
            holder.jawaban.setText(model.getJawaban());

            if (model.getFoto_qna() != null){
                holder.foto.setVisibility(View.VISIBLE);
                holder.view.setVisibility(View.VISIBLE);
                Glide.with(context).load(model.getFoto_url()).into(holder.foto);
            }
            holder.tampildetail.setOnClickListener(new View.OnClickListener() {
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
        return listData.size()+1;
    }

    public class HolderData extends RecyclerView.ViewHolder{
        RelativeLayout tampildetail;
        LinearLayout detail;
        TextView nama, pertanyaan, jawaban;
        ImageView foto, btndetail;
        CardView view;
        public HolderData(@NonNull View itemView) {
            super(itemView);

            tampildetail = itemView.findViewById(R.id.tampildetail);
            detail = itemView.findViewById(R.id.detail);
            nama = itemView.findViewById(R.id.nama);
            pertanyaan = itemView.findViewById(R.id.pertanyaan);
            foto = itemView.findViewById(R.id.foto);
            jawaban = itemView.findViewById(R.id.jawaban);
            btndetail = itemView.findViewById(R.id.btndetail);
            view = itemView.findViewById(R.id.view);
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
