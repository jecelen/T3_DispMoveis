package com.example.dailygaily.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.concurrent.Executors;

import com.example.dailygaily.R;
import com.example.dailygaily.database.LocalDatabase;
import com.example.dailygaily.entities.ConsumoDeAgua;

public class TelaAdcAgua extends AppCompatActivity {
    private LocalDatabase db;
    private ImageButton imgVoltar;
    private Button btnAdcAgua;
    private EditText horarioConsumo, mlConsumido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adc_agua);
        db = LocalDatabase.getDatabase(getApplicationContext());
        imgVoltar = findViewById(R.id.imgVoltar);
        btnAdcAgua = findViewById(R.id.btnSalvarConsumo);
        mlConsumido = findViewById(R.id.edtMlConsumido);
        horarioConsumo = findViewById(R.id.editTextTime);

        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaAdcAgua.this, TelaConsumoAgua.class);
                startActivity(it);
            }
        });

        btnAdcAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConsumo();
            }
        });
    }

    private void addConsumo() {
        String mlText = mlConsumido.getText().toString();
        String horCon = horarioConsumo.getText().toString();

        if (!mlText.isEmpty() && !horCon.isEmpty()) {
            try {
                int ml = Integer.parseInt(mlText);

                ConsumoDeAgua consumo = new ConsumoDeAgua();
                consumo.setMlConsumido(ml);
                consumo.setHorario(horCon);

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        db.consumoModel().insertAll(consumo);
                    }
                });

                Toast.makeText(this, "Consumo de água salvo com sucesso!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TelaAdcAgua.this, TelaConsumoAgua.class);
                startActivity(intent);

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Por favor, insira um valor numérico válido", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
    }
}
