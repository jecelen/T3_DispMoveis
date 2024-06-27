package com.example.dailygaily.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.dailygaily.R;
import com.example.dailygaily.dao.HumorDao;
import com.example.dailygaily.database.LocalDatabase;
import com.example.dailygaily.entities.Humor;

public class TelaHumor extends AppCompatActivity {

    private RadioGroup radioGroupHumores;
    private Button btnRegistrarHumor;
    private SharedPreferences sharedPreferences;
    private int dbUsuarioId;
    private LocalDatabase db;
    private ImageButton imgVoltar;
    private ImageView imgHumor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_humor);
        imgHumor = findViewById(R.id.imageView3);
        radioGroupHumores = findViewById(R.id.btnHumores);
        btnRegistrarHumor = findViewById(R.id.btnRegistrarHumor);
        db = LocalDatabase.getDatabase(getApplicationContext());
        imgVoltar = findViewById(R.id.imgVoltar);

        Humor lastHumor = db.humorModel().getLastHumor();
        if (lastHumor != null) {
            updateHumorImage(lastHumor.getNome());
        }

        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaHumor.this, TelaInicial.class);
                startActivity(it);
            }
        });

        btnRegistrarHumor.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View v) {
                // Obter o ID do RadioButton selecionado
                int selectedId = radioGroupHumores.getCheckedRadioButtonId();

                if (selectedId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    String selectedText = "";

                    if (selectedId == R.id.btnRuim) {
                        selectedText = "Ruim";
                        salvarHumor(selectedText);
                    } else if (selectedId == R.id.btnMedioRuim) {
                        selectedText = "Médio Ruim";
                        salvarHumor(selectedText);
                    } else if (selectedId == R.id.btnMedioBom) {
                        selectedText = "Médio Bom";
                        salvarHumor(selectedText);
                    } else if (selectedId == R.id.btnBom) {
                        selectedText = "Bom";
                        salvarHumor(selectedText);
                    }

                    Toast.makeText(TelaHumor.this, "Selecionado: " + selectedText, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TelaHumor.this, "Nenhuma opção selecionada", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void salvarHumor(String selectedText) {
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        dbUsuarioId = sharedPreferences.getInt("usuarioId", -1);
        String humor = selectedText;
        Humor novoHumor = new Humor();
        novoHumor.setNome(humor);
        novoHumor.setUsuarioID(dbUsuarioId);


        db.humorModel().insertAll(novoHumor);
        Toast.makeText(this, "Humor salvo com sucesso!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(TelaHumor.this, TelaInicial.class);
        startActivity(intent);
    }
    private void updateHumorImage(String humorName) {
        int imageResId = R.drawable.humor_bom;
        switch (humorName) {
            case "Ruim":
                imageResId = R.drawable.humor_ruim;
                break;
            case "Médio Ruim":
                imageResId = R.drawable.humor_medio_ruim;
                break;
            case "Médio Bom":
                imageResId = R.drawable.humor_medio_bom;
                break;
            case "Bom":
                imageResId = R.drawable.humor_bom;
                break;
        }
        imgHumor.setImageResource(imageResId);
    }
}