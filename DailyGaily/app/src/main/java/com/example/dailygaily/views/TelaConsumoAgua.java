package com.example.dailygaily.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dailygaily.R;
import com.example.dailygaily.database.LocalDatabase;

import java.util.List;
import java.util.concurrent.Executors;

public class TelaConsumoAgua extends AppCompatActivity {

    private List listaConsumoAgua;

    private LocalDatabase db;

    private TextView totalagua;
    private Button btnAdcAgua;
    private ImageButton voltar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_consumo_agua);
        voltar = findViewById(R.id.imgVoltar);
        btnAdcAgua = findViewById(R.id.btnAdcAgua);
        btnAdcAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaConsumoAgua.this, TelaAdcAgua.class);
                startActivity(it);
            }
        });
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaConsumoAgua.this, TelaInicial.class);
                startActivity(it);
            }
        });


        db = LocalDatabase.getDatabase(getApplicationContext());
        totalagua = findViewById(R.id.totalConsumido);

        loadTotalConsumido();

    }

    private void loadTotalConsumido(){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final int total = db.consumoModel().getTotalConsumption();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        totalagua.setText("Total Consumido: " + total + "ml");
                    }
                });
            }
        });
    }
}