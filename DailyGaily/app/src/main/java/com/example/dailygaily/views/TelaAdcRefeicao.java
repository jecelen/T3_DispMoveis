package com.example.dailygaily.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dailygaily.R;
import com.example.dailygaily.database.LocalDatabase;
import com.example.dailygaily.entities.Alimentacao;
import com.example.dailygaily.entities.Exercicio;

public class TelaAdcRefeicao extends AppCompatActivity {

    private LocalDatabase db;
    private EditText edtDescricao, edtCalorias, edtLatitude, edtLongitude, edtNomeLocal;
    private Double calorias, lat, lon;
    private ImageButton imgVoltar;
    private Button btnSalvarRefeicao;
    private SharedPreferences sharedPreferences;
    private int dbUsuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adc_refeicao);
        db = LocalDatabase.getDatabase(getApplicationContext());
        edtDescricao = findViewById(R.id.edtDescricaoRefeicao);
        edtCalorias = findViewById(R.id.edtCaloriasRefeicao);
        edtLatitude = findViewById(R.id.edtLatitudeRefeicao);
        edtLongitude = findViewById(R.id.edtLongitudeRefeicao);
        edtNomeLocal = findViewById(R.id.edtLocalRefeicao);
        btnSalvarRefeicao = findViewById(R.id.btnSalvarRefeicao);
        imgVoltar = findViewById(R.id.imgVoltar);

        btnSalvarRefeicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarRefeicao();
            }
        });

        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaAdcRefeicao.this, TelaAlimentacao.class);
                startActivity(it);
            }
        });
    }

    private void salvarRefeicao() {
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        dbUsuarioId = sharedPreferences.getInt("usuarioId", -1);
        String descricao = edtDescricao.getText().toString();
        String caloria = edtCalorias.getText().toString();
        String latitude = edtLatitude.getText().toString();
        String longitude = edtLongitude.getText().toString();
        String nomeLocal = edtNomeLocal.getText().toString();
        calorias = Double.valueOf(caloria).doubleValue();
        lat = Double.valueOf(latitude).doubleValue();
        lon = Double.valueOf(longitude).doubleValue();
        Alimentacao novaAlimentacao = new Alimentacao();
        novaAlimentacao.setDescricao(descricao);
        novaAlimentacao.setCalorias(calorias);
        novaAlimentacao.setLatitude(lat);
        novaAlimentacao.setLongitude(lon);
        novaAlimentacao.setNomeDoLocal(nomeLocal);
        novaAlimentacao.setUsuarioID(dbUsuarioId);


        db.alimentacaoModel().insertAll(novaAlimentacao);
        Toast.makeText(this, "Alimentação salva com sucesso!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(TelaAdcRefeicao.this, TelaAlimentacao.class);
        startActivity(intent);
    }
}