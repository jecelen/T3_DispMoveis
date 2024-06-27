package com.example.dailygaily.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dailygaily.R;
import com.example.dailygaily.dao.ExercicioDao;
import com.example.dailygaily.database.LocalDatabase;
import com.example.dailygaily.entities.Exercicio;

public class TelaEdtExercicios extends AppCompatActivity {

    private Button btnSalvarExercicio;
    private ImageButton btnExcluirExercicio;
    private TextView edtTipoExercicio, edtTempoExercicio;
    private long dbExercicioID;
    private LocalDatabase db;
    private ImageView fotoExercicioEdt;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_edt_exercicios);
        edtTipoExercicio = findViewById(R.id.edtTipoExercicio);
        edtTempoExercicio = findViewById(R.id.edtTempoExercicio);
        btnExcluirExercicio = findViewById(R.id.btnExcluirExercicio);
        btnSalvarExercicio = findViewById(R.id.btnSalvarExercicio);
        fotoExercicioEdt = findViewById(R.id.fotoExercicioEdt);

        btnSalvarExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarAlteracoes();
                startActivity(new Intent(TelaEdtExercicios.this, TelaExercicios.class));
            }
        });

        btnExcluirExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirExercicio();
            }
        });
        preencherDadosExercicio();
    }

    private void salvarAlteracoes() {

        ExercicioDao exercicioDao = LocalDatabase.getDatabase(this).exercicioModel();

        int exercicioIdInt = (int) dbExercicioID;

        Exercicio exercicio = exercicioDao.getExercicio(exercicioIdInt);

        if (exercicio != null) {

            exercicio.setTipoDeExercicio(edtTipoExercicio.getText().toString());
            exercicio.setTempo(Double.parseDouble(edtTempoExercicio.getText().toString()));


            exercicioDao.update(exercicio);

            Toast.makeText(this, "Alterações salvas com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Erro: exercicio não encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void excluirExercicio() {

        ExercicioDao exercicioDao = LocalDatabase.getDatabase(this).exercicioModel();

        // Convertendo o long para int
        int exercicioIdInt = (int) dbExercicioID;

        Exercicio exercicio = exercicioDao.getExercicio(exercicioIdInt);


        if (exercicio != null) {

            exercicioDao.delete(exercicio);
            Toast.makeText(this, "Exercício excluído com sucesso", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(TelaEdtExercicios.this, TelaExercicios.class));
            finish();
        } else {
            Toast.makeText(this, "Erro: exercício não encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void preencherDadosExercicio() {
        SharedPreferences sharedPreferences = getSharedPreferences("ExercicioPref", MODE_PRIVATE);
        dbExercicioID = sharedPreferences.getInt("exercicioId", -1);
        ExercicioDao exercicioDao = LocalDatabase.getDatabase(this).exercicioModel();
        int exercicioIdInt = (int) dbExercicioID;
        Exercicio exercicio = exercicioDao.getExercicio(exercicioIdInt);

        if (exercicio != null) {
            edtTipoExercicio.setText(exercicio.getTipoDeExercicio());
            edtTempoExercicio.setText(exercicio.getTempo().toString());

            if (exercicio.getFotoUri() != null) {
                Uri photoURI = Uri.parse(exercicio.getFotoUri());
                Log.d("TelaEdtExercicios", "URI da foto: " + photoURI.toString());
                Glide.with(this)
                        .load(photoURI)
                        .into(fotoExercicioEdt);
            }
        } else {
            Toast.makeText(this, "Erro: Exercício não encontrado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}