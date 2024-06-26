package com.example.dailygaily.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailygaily.R;
import com.example.dailygaily.entities.Alimentacao;
import com.example.dailygaily.views.TelaInicial;

import java.util.List;

public class AlimentacaoAdapter extends ArrayAdapter<Alimentacao> {
    private final Context context;
    private final List<Alimentacao> alimentacaos;

    public AlimentacaoAdapter(Context context, List<Alimentacao> alimentacaos) {
        super(context, 0, alimentacaos);
        this.context = context;
        this.alimentacaos = alimentacaos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_alimentacao, parent, false);
        }

        final Alimentacao currentItem = alimentacaos.get(position);

        ImageView icon = convertView.findViewById(R.id.iconeMapa);
        TextView text = convertView.findViewById(R.id.textoItem);
        TextView text2 = convertView.findViewById(R.id.textoItem2);

        // Aqui você pode definir a imagem que deseja usar
        icon.setImageResource(R.drawable.map); // Substitua com sua imagem desejada
        text.setText(currentItem.getDescricao());
        text2.setText(String.valueOf(currentItem.getCalorias()) + " kcal");

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar para o mapa
                Toast.makeText(context, "Abrindo mapa", Toast.LENGTH_SHORT).show();
                Intent mapIntent = new Intent(context, TelaInicial.class);
                context.startActivity(mapIntent);
            }
        });

        return convertView;
    }
}
