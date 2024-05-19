package com.cakraagro.cakraagroindonesia.ActivityCustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cakraagro.cakraagroindonesia.ActivityCustomer.Aktivitas;
import com.cakraagro.cakraagroindonesia.ActivityCustomer.Demplot;
import com.cakraagro.cakraagroindonesia.ActivityCustomer.FAQ;
import com.cakraagro.cakraagroindonesia.ActivityCustomer.PaketProduk;
import com.cakraagro.cakraagroindonesia.ActivityCustomer.ProdukBaru;
import com.example.cakraagroindonesia.R;

public class FragmentAktivitas extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_aktivitas,container,false);

        ImageView aktivitas = (ImageView) rootView.findViewById(R.id.btnaktivitas);
        aktivitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aktivitas = new Intent(getActivity(), Aktivitas.class);
                startActivity(aktivitas);
            }
        });

        ImageView demplot = (ImageView) rootView.findViewById(R.id.btndemplot);
        demplot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent demplot = new Intent(getActivity(), Demplot.class);
                startActivity(demplot);
            }
        });

        ImageView produkbaru = (ImageView) rootView.findViewById(R.id.btnprodukbaru);
        produkbaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent produkbaru = new Intent(getActivity(), ProdukBaru.class);
                startActivity(produkbaru);
            }
        });

        ImageView paketproduk = (ImageView) rootView.findViewById(R.id.btnpaketproduk);
        paketproduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent paketproduk = new Intent(getActivity(), PaketProduk.class);
                startActivity(paketproduk);
            }
        });

        TextView btnFaq = rootView.findViewById(R.id.btnfaq);
        btnFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent faq = new Intent(getActivity(), FAQ.class);
                startActivity(faq);
            }
        });

        return rootView;
    }
}
