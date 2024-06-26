package com.example.dailygaily.views;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.dailygaily.dao.ExercicioDao;
import com.example.dailygaily.database.LocalDatabase;
import com.example.dailygaily.entities.Exercicio;

import java.util.List;

public class TelaExercicios extends AppCompatActivity {
    private LocalDatabase db;
    private List<Exercicio> exercicios;
    private ListView listViewExercicios;
    private Intent edtIntent;
    private Button btnAdcExercicio;
    private int dbUsuarioId;
    private SharedPreferences sharedPreferences;

    private ImageButton imgVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_exercicios);
        db = LocalDatabase.getDatabase(getApplicationContext());
        listViewExercicios = findViewById(R.id.listViewExercicio);
        btnAdcExercicio = findViewById(R.id.btnAdcExercicio);
        imgVoltar = findViewById(R.id.imgVoltar);

        btnAdcExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaExercicios.this, TelaAdcExercicios.class);
                startActivity(it);
            }
        });

        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaExercicios.this, TelaInicial.class);
                startActivity(it);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        edtIntent = new Intent(this, TelaEdtExercicios.class);
        preencheExercicios();
    }

    private void preencheExercicios() {
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        dbUsuarioId = sharedPreferences.getInt("usuarioId", -1);
        exercicios = db.exercicioModel().getExerciciosByUsuarioID(dbUsuarioId);
        ArrayAdapter<Exercicio> exerciciosAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, exercicios);
        listViewExercicios.setAdapter(exerciciosAdapter);
        Log.d("IDexe", "idUsuario:"+dbUsuarioId);

        listViewExercicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Exercicio exercicioSelecionado = exercicios.get(position);
                int exercicioID = exercicioSelecionado.getExercicioID();
                String tipoDeExercicio = exercicioSelecionado.getTipoDeExercicio();
                saveTipoExercicioToSharedPreferences(exercicioID, tipoDeExercicio);
                edtIntent.putExtra("EXERCICIO_SELECIONADO_ID",
                        exercicioSelecionado.getExercicioID());

                startActivity(edtIntent);
            }
        });
    }

    private void saveTipoExercicioToSharedPreferences(int exercicioID, String tipoDeExercicio) {
        SharedPreferences sharedPreferences = getSharedPreferences("ExercicioPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("exercicioId", exercicioID);
        editor.putString("ExercicioMarcado", tipoDeExercicio);
        editor.apply();
    }

    /*
    private Button btnAdcExercicio;
    private LocalDatabase db;
    private ListView listViewExercicio;
    private Context context;

        btnAdcExercicio = findViewById(R.id.btnAdcExercicio);
        listViewExercicio = findViewById(R.id.listViewExercicio);
        db = LocalDatabase.getDatabase(getApplicationContext());
        context = this;
        listAllExe();
        preencheExercicios();

        btnAdcExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaExercicios.this, TelaAdcExercicios.class);
                startActivity(it);
            }
        });
    }


    private void preencheExercicios() {
        List<Exercicio> exercicio = db.exercicioModel().getAllExe();
        ArrayAdapter<Exercicio> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, exercicio);
        listViewExercicio.setAdapter(adapter);

        listViewExercicio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Exercicio selectedExercicio = (Exercicio) adapter.getItemAtPosition(position);
                int exercicioID = selectedExercicio.getExercicioID();
                String tipoDeExercicio = selectedExercicio.getTipoDeExercicio();
                saveEnderecoToSharedPreferences(exercicioID, tipoDeExercicio);

                Intent intent = new Intent(context, TelaAdcExercicios.class);
                intent.putExtra("EXERCICIO_SELECIONADO_ID", exercicioID);
                context.startActivity(intent);

                Log.d("TAG", "Saved exercicioID: " + exercicioID);
                Log.d("TAG", "Saved tipoDeExercicio: " + tipoDeExercicio);
            }
        });
    }

    private void saveEnderecoToSharedPreferences(int exercicioID, String tipoDeExercicio) {
        SharedPreferences sharedPreferences = getSharedPreferences("locPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("EXERCICIO_SELECIONADO_ID", exercicioID);
        editor.putString("ExercicioMarcado", tipoDeExercicio);
        editor.apply();
    }


    private void listAllExe() {
        ExercicioDao exercicioDao = db.exercicioModel();
        List<Exercicio> exercicios = exercicioDao.getAllExe();
        for (Exercicio exercicio : exercicios) {
            //Log.d("DB", "Endereco: " + exercicio.getDescricao() + ", CidadeID: " + endereco.getCidadeID());
        }
    }
*/


}

