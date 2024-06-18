package com.example.dailygaily.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dailygaily.R;

public class TelaConsumoAgua extends AppCompatActivity {

    private Button btnAdcAgua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_consumo_agua);
        btnAdcAgua = findViewById(R.id.btnAdcAgua);

        btnAdcAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaConsumoAgua.this, TelaAdcAgua.class);
                startActivity(it);
            }
        });

    }
}