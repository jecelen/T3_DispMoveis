package com.example.dailygaily.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailygaily.R;
import com.example.dailygaily.dao.AlimentacaoDao;
import com.example.dailygaily.dao.ExercicioDao;
import com.example.dailygaily.database.LocalDatabase;
import com.example.dailygaily.entities.Alimentacao;
import com.example.dailygaily.entities.Exercicio;

public class TelaEdtAlimentacao extends AppCompatActivity {
    private Button btnSalvarAlimentacao;
    private ImageButton btnExcluirAlimentacao;
    private TextView edtDescricaoRefeicao, edtCaloriasRefeicao, edtLatitudeRefeicao, edtLongitudeRefeicao, edtLocalRefeicao;
    private long dbAlimentacaoID;
    private LocalDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_edt_alimentacao);
        edtDescricaoRefeicao = findViewById(R.id.edtDescricaoRefeicao);
        edtCaloriasRefeicao = findViewById(R.id.edtCaloriasRefeicao);
        edtLatitudeRefeicao = findViewById(R.id.edtLatitudeRefeicao);
        edtLongitudeRefeicao = findViewById(R.id.edtLongitudeRefeicao);
        edtLocalRefeicao = findViewById(R.id.edtLocalRefeicao);
        btnExcluirAlimentacao = findViewById(R.id.btnExcluirAlimentacao);
        btnSalvarAlimentacao = findViewById(R.id.btnSalvarRefeicao);

        btnSalvarAlimentacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarAlteracoes();
                startActivity(new Intent(TelaEdtAlimentacao.this, TelaAlimentacao.class));
            }
        });

        btnExcluirAlimentacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirAlimentacao();
            }
        });
        preencherDadosAlimentacao();
    }

    private void salvarAlteracoes() {
        // Obtém a cidade do banco de dados pelo ID
        AlimentacaoDao alimentacaoDao = LocalDatabase.getDatabase(this).alimentacaoModel();

        int alimentacaoIdInt = (int) dbAlimentacaoID;

        // Obtém a cidade do banco de dados com o ID fornecido
        Alimentacao alimentacao = alimentacaoDao.getAlimentacao(alimentacaoIdInt);

        if (alimentacao != null) {
            // Atualiza os dados da cidade
            alimentacao.setDescricao(edtDescricaoRefeicao.getText().toString());
            alimentacao.setCalorias(Double.parseDouble(edtCaloriasRefeicao.getText().toString()));
            alimentacao.setLatitude(Double.parseDouble(edtLatitudeRefeicao.getText().toString()));
            alimentacao.setLongitude(Double.parseDouble(edtLongitudeRefeicao.getText().toString()));
            alimentacao.setNomeDoLocal(edtLocalRefeicao.getText().toString());

            // Atualiza a cidade no banco de dados
            alimentacaoDao.update(alimentacao);

            Toast.makeText(this, "Alterações salvas com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Erro: alimentação não encontrada", Toast.LENGTH_SHORT).show();
        }
    }

    private void excluirAlimentacao() {
        // Obtém a cidade do banco de dados pelo ID
        AlimentacaoDao alimentacaoDao = LocalDatabase.getDatabase(this).alimentacaoModel();

        // Convertendo o long para int
        int alimentacaoIdInt = (int) dbAlimentacaoID;

        // Obtém a cidade do banco de dados com o ID fornecido
        Alimentacao alimentacao = alimentacaoDao.getAlimentacao(alimentacaoIdInt);

        // Verifica se o exercicio foi encontrada
        if (alimentacao != null) {
            // Exclui a cidade do banco de dados
            alimentacaoDao.delete(alimentacao);
            Toast.makeText(this, "Alimentação excluída com sucesso", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(TelaEdtAlimentacao.this, TelaAlimentacao.class));
            finish();
        } else {
            Toast.makeText(this, "Erro: alimentação não encontrada", Toast.LENGTH_SHORT).show();
        }
    }

    private void preencherDadosAlimentacao() {
        SharedPreferences sharedPreferences = getSharedPreferences("AlimentacaoPref", MODE_PRIVATE);
        dbAlimentacaoID = sharedPreferences.getInt("alimentacaoId", -1);
        AlimentacaoDao alimentacaoDao = LocalDatabase.getDatabase(this).alimentacaoModel();
        int alimentacaoIdInt = (int) dbAlimentacaoID;
        Alimentacao alimentacao = alimentacaoDao.getAlimentacao(alimentacaoIdInt);

        if (alimentacao != null) {
            edtDescricaoRefeicao.setText(alimentacao.getDescricao());
            edtCaloriasRefeicao.setText(alimentacao.getCalorias().toString());
            edtLatitudeRefeicao.setText(Double.toString(alimentacao.getLatitude()));
            edtLongitudeRefeicao.setText(Double.toString(alimentacao.getLongitude()));
            edtLocalRefeicao.setText(alimentacao.getNomeDoLocal());
        } else {
            Toast.makeText(this, "Erro: Alimentação não encontrada", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}