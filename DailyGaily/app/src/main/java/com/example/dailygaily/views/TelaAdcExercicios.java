package com.example.dailygaily.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dailygaily.R;
import com.example.dailygaily.dao.ExercicioDao;
import com.example.dailygaily.dao.UsuarioDao;
import com.example.dailygaily.database.LocalDatabase;
import com.example.dailygaily.entities.Exercicio;
import com.example.dailygaily.entities.Usuario;

public class TelaAdcExercicios extends AppCompatActivity {

    private LocalDatabase db;
    private EditText edtTipoExercicio, edtTempoExercicio;
    private Double tempo;
    private Button btnSalvarExercicio;
    private SharedPreferences sharedPreferences;
    private int dbUsuarioId;

    private ImageButton imgVoltar;



    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Ch", "Cheguei aqui");
        setContentView(R.layout.activity_tela_adc_exercicios);
        db = LocalDatabase.getDatabase(getApplicationContext());

        edtTipoExercicio = findViewById(R.id.edtTipoExercicio);
        edtTempoExercicio = findViewById(R.id.edtTempoExercicio);
        btnSalvarExercicio = findViewById(R.id.btnSalvarExercicio);
        imgVoltar = findViewById(R.id.imgVoltar);

        btnSalvarExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarExercicio();
            }
        });

        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaAdcExercicios.this, TelaExercicios.class);
                startActivity(it);
            }
        });
    }

    private void salvarExercicio() {
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        dbUsuarioId = sharedPreferences.getInt("usuarioId", -1);
        String tipoExercicio = edtTipoExercicio.getText().toString();
        String tempoExercicio = edtTempoExercicio.getText().toString();
        tempo = Double.valueOf(tempoExercicio).doubleValue();
        Exercicio novoExercicio = new Exercicio();
        novoExercicio.setTipoDeExercicio(tipoExercicio);
        novoExercicio.setTempo(tempo);
        novoExercicio.setUsuarioID(dbUsuarioId);


        db.exercicioModel().insertAll(novoExercicio);
        Toast.makeText(this, "Exercício salvo com sucesso!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(TelaAdcExercicios.this, TelaExercicios.class);
        startActivity(intent);
    }

}