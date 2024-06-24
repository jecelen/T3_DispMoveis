package com.example.dailygaily.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dailygaily.R;

public class TelaAlimentacao extends AppCompatActivity {

    private Button btnAdcRefeicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_alimentacao);
        btnAdcRefeicao = findViewById(R.id.btnAdcRefeicao);

        btnAdcRefeicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaAlimentacao.this, TelaAdcRefeicao.class);
                startActivity(it);
            }
        });
    }
}