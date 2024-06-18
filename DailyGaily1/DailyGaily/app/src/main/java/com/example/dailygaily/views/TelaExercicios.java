package com.example.dailygaily.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dailygaily.R;

public class TelaExercicios extends AppCompatActivity {

    private Button btnAdcExercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_exercicios);
        btnAdcExercicio = findViewById(R.id.btnAdcExercicio);

        btnAdcExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaExercicios.this, TelaAdcExercicios.class);
                startActivity(it);
            }
        });
    }
}