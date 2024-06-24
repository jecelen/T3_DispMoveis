package com.example.dailygaily.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.dailygaily.R;

public class TelaAdcAgua extends AppCompatActivity {

    private ImageButton imgVoltar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adc_agua);
        imgVoltar = findViewById(R.id.imgVoltar);

        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaAdcAgua.this, TelaConsumoAgua.class);
                startActivity(it);
            }
        });

    }
}