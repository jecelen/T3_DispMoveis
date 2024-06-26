package com.example.dailygaily.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.dailygaily.R;
import com.example.dailygaily.database.LocalDatabase;
import com.example.dailygaily.entities.Alimentacao;
import com.example.dailygaily.entities.Exercicio;
import com.example.dailygaily.adapter.AlimentacaoAdapter;

import java.util.List;

public class TelaAlimentacao extends AppCompatActivity {

    private Button btnAdcRefeicao;
    private LocalDatabase db;
    private List<Alimentacao> alimentacaos;
    private ListView listViewAlimentacao;
    private ImageButton imgVoltar;
    private Intent edtIntent;
    private int dbUsuarioId;
    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_alimentacao);
        db = LocalDatabase.getDatabase(getApplicationContext());
        listViewAlimentacao = findViewById(R.id.listViewAlimentacao);
        btnAdcRefeicao = findViewById(R.id.btnAdcRefeicao);
        imgVoltar = findViewById(R.id.imgVoltar);

        btnAdcRefeicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaAlimentacao.this, TelaAdcRefeicao.class);
                startActivity(it);
            }
        });
        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaAlimentacao.this, TelaInicial.class);
                startActivity(it);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        edtIntent = new Intent(this, TelaEdtAlimentacao.class);
        preencheAlimentacao();
    }

    /*private void preencheAlimentacao() {
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        dbUsuarioId = sharedPreferences.getInt("usuarioId", -1);
        alimentacaos = db.alimentacaoModel().getAlimentacaoByUsuarioID(dbUsuarioId);
        ArrayAdapter<Alimentacao> alimentacaoAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, alimentacaos);
        listViewAlimentacao.setAdapter(alimentacaoAdapter);
        Log.d("IDexe", "idUsuario:"+dbUsuarioId);

        listViewAlimentacao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Alimentacao alimentacaoSelecionada = alimentacaos.get(position);
                int alimentacaoID = alimentacaoSelecionada.getAlimentacaoID();
                String descricao = alimentacaoSelecionada.getDescricao();
                saveAlimentacaoToSharedPreferences(alimentacaoID, descricao);
                edtIntent.putExtra("ALIMENTACAO_SELECIONADA_ID",
                        alimentacaoSelecionada.getAlimentacaoID());

                startActivity(edtIntent);
            }
        });*/
    private void preencheAlimentacao() {
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        dbUsuarioId = sharedPreferences.getInt("usuarioId", -1);
        alimentacaos = db.alimentacaoModel().getAlimentacaoByUsuarioID(dbUsuarioId);
        AlimentacaoAdapter alimentacaoAdapter = new AlimentacaoAdapter(this, alimentacaos);
        listViewAlimentacao.setAdapter(alimentacaoAdapter);
        Log.d("IDexe", "idUsuario:" + dbUsuarioId);

        listViewAlimentacao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Alimentacao alimentacaoSelecionada = alimentacaos.get(position);
                int alimentacaoID = alimentacaoSelecionada.getAlimentacaoID();
                String descricao = alimentacaoSelecionada.getDescricao();
                saveAlimentacaoToSharedPreferences(alimentacaoID, descricao);
                edtIntent.putExtra("ALIMENTACAO_SELECIONADA_ID", alimentacaoSelecionada.getAlimentacaoID());

                startActivity(edtIntent);
            }
        });

    }

    private void saveAlimentacaoToSharedPreferences(int alimentacaoID, String descricao) {
        SharedPreferences sharedPreferences = getSharedPreferences("AlimentacaoPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("alimentacaoId", alimentacaoID);
        editor.putString("AlimentacaoMarcada", descricao);
        editor.apply();
    }

}