package com.example.dailygaily.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dailygaily.R;
import com.example.dailygaily.dao.UsuarioDao;
import com.example.dailygaily.database.LocalDatabase;
import com.example.dailygaily.entities.Usuario;

public class TelaInicial extends AppCompatActivity {

    private Button btnExerciciosFisicos, btnHumorDiario, btnHabitosAlimentares, btnConsumoAgua, btnAnaliseRotina;
    private ImageButton btnConta;
    private int dbUsuarioId;
    private TextView txtNomeDoUsuario, txtTelaInicial;

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
        txtNomeDoUsuario = findViewById(R.id.txtNomeUsuario);
        txtTelaInicial = findViewById(R.id.txtTelaInicial1);

        SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        dbUsuarioId = sharedPreferences.getInt("usuarioId", -1);

        bemVindoUsuario();
        Log.d("tag", "cheguei");

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
                Intent it = new Intent(TelaInicial.this, TelaTarefa.class);
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

    private void bemVindoUsuario() {
        if (dbUsuarioId != -1) {
            // Obtém o nome do usuário logado no banco de dados
            LocalDatabase db = LocalDatabase.getDatabase(this);
            assert db != null;
            UsuarioDao usuarioDao = db.usuarioModel();
            Usuario usuario = usuarioDao.getUsuario(dbUsuarioId);
            Log.d("ch", "aqui");

            if (usuario != null) {
                String nomeUsuario = usuario.getNome();
                txtNomeDoUsuario.setText(nomeUsuario);
            }
        }
    }

}