package com.cakraagro.cakraagroindonesia.ActivityCustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cakraagro.cakraagroindonesia.ActivityCustomer.Fungisida;
import com.cakraagro.cakraagroindonesia.ActivityCustomer.Herbisida;
import com.cakraagro.cakraagroindonesia.ActivityCustomer.Insektisida;
import com.cakraagro.cakraagroindonesia.ActivityCustomer.Pgr;
import com.example.cakraagroindonesia.R;

public class FragmentProduk extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_produk,container,false);

        TextView insektisida = (TextView) rootView.findViewById(R.id.btninsektisida);
        TextView fungsinida = (TextView) rootView.findViewById(R.id.btnfungsinida);
        TextView herbisida = (TextView) rootView.findViewById(R.id.btnherbisida);
        TextView pgr = (TextView) rootView.findViewById(R.id.btnpgr);

        insektisida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent insektisida = new Intent(getActivity(), Insektisida.class);
                startActivity(insektisida);
            }
        });

        fungsinida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fungsinida = new Intent(getActivity(), Fungisida.class);
                startActivity(fungsinida);
            }
        });

        herbisida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent herbisida = new Intent(getActivity(), Herbisida.class);
                startActivity(herbisida);
            }
        });

        pgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pgr = new Intent(getActivity(), Pgr.class);
                startActivity(pgr);
            }
        });

        return rootView;
    }
}