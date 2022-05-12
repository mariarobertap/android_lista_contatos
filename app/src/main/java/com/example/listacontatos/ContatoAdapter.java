package com.example.listacontatos;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class ContatoAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Contato> data;

    public ContatoAdapter(@NonNull Context context,
                          int layoutId,
                          ArrayList<Contato> list) {
        super(context, layoutId, list);
        this.context = context;
        this.data = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.lista_dinamica,null);
        TextView tNome = (TextView) view.findViewById(R.id.tvNome);
        TextView tEndereco = (TextView) view.findViewById(R.id.tvEndereco);
        TextView tTelefone1 = (TextView) view.findViewById(R.id.tvTelefone1);
        TextView tTelefone2 = (TextView) view.findViewById(R.id.tvTelefone2);

        tNome.setText(data.get(position).getNome());
        tEndereco.setText(data.get(position).getEndereco());
        tTelefone1.setText(data.get(position).getTelefone1());
        tTelefone2.setText(data.get(position).getTelefone2());



        return view;
    }
}
