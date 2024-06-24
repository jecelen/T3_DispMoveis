package com.example.dailygaily.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.dailygaily.R;

public class TelaInicial extends AppCompatActivity {

    private Button btnExerciciosFisicos, btnHumorDiario, btnHabitosAlimentares, btnConsumoAgua, btnAnaliseRotina;
    private ImageButton btnConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        btnExerciciosFisicos = findViewById(R.id.btnExerciciosFisicos);
        btnHumorDiario = findViewById(R.id.btnHumorDiario);
        btnHabitosAlimentares = findViewById(R.id.btnHabitosAlimentares);
        btnConsumoAgua = findViewById(R.id.btnConsumoAgua);
        btnAnaliseRotina = findViewById(R.id.btnAnaliseRotina);
        btnConta = findViewById(R.id.btnConta);

        btnHumorDiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaInicial.this, TelaHumor.class);
                startActivity(it);
            }
        });

        btnExerciciosFisicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaInicial.this, TelaExercicios.class);
                startActivity(it);
            }
        });

        btnHabitosAlimentares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaInicial.this, TelaAlimentacao.class);
                startActivity(it);
            }
        });

        btnConsumoAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaInicial.this, TelaConsumoAgua.class);
                startActivity(it);
            }
        });

        btnAnaliseRotina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaInicial.this, TelaAnaliseRotina.class);
                startActivity(it);
            }
        });

        btnConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaInicial.this, TelaAtualizarConta.class);
                startActivity(it);
            }
        });
    }
}