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
    private EditText edtTipoExercicio, edtTempoExercicio, edtLatitude, edtLongitude;
    private Double tempo, lat, lon;
    private Button btnSalvarExercicio;
    private SharedPreferences sharedPreferences;
    private int dbUsuarioId;



    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Ch", "Cheguei aqui");
        setContentView(R.layout.activity_tela_adc_exercicios);
        db = LocalDatabase.getDatabase(getApplicationContext());
        edtTipoExercicio = findViewById(R.id.edtTipoExercicio);
        edtTempoExercicio = findViewById(R.id.edtTempoExercicio);
        edtLatitude = findViewById(R.id.edtLatitudeExercicios);
        edtLongitude = findViewById(R.id.edtLongitudeExercicios);
        btnSalvarExercicio = findViewById(R.id.btnSalvarExercicio);

        btnSalvarExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarExercicio();
            }
        });
    }

    private void salvarExercicio() {
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        dbUsuarioId = sharedPreferences.getInt("usuarioId", -1);
        String tipoExercicio = edtTipoExercicio.getText().toString();
        String tempoExercicio = edtTempoExercicio.getText().toString();
        String latitude = edtLatitude.getText().toString();
        String longitude = edtLongitude.getText().toString();
        tempo = Double.valueOf(tempoExercicio).doubleValue();
        lat = Double.valueOf(latitude).doubleValue();
        lon = Double.valueOf(longitude).doubleValue();
        Exercicio novoExercicio = new Exercicio();
        novoExercicio.setTipoDeExercicio(tipoExercicio);
        novoExercicio.setTempo(tempo);
        novoExercicio.setLatitude(lat);
        novoExercicio.setLongitude(lon);
        novoExercicio.setUsuarioID(dbUsuarioId);


        db.exercicioModel().insertAll(novoExercicio);
        Toast.makeText(this, "Exercício salvo com sucesso!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(TelaAdcExercicios.this, TelaExercicios.class);
        startActivity(intent);
    }

}