package com.example.dailygaily.views;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dailygaily.R;
import com.example.dailygaily.adapter.CheckboxAdapter;

import java.util.ArrayList;

public class TelaTarefa extends AppCompatActivity implements CheckboxAdapter.OnEditTaskListener {

    private Button btnAdcTarefa;
    private ListView listViewTarefas;
    private CheckboxAdapter checkboxAdapter;
    private ArrayList<String> tarefas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_tarefa);

        btnAdcTarefa = findViewById(R.id.btnAdcTarefa);
        listViewTarefas = findViewById(R.id.listViewTarefas);

        tarefas = new ArrayList<>();
        checkboxAdapter = new CheckboxAdapter(this, tarefas, this);
        listViewTarefas.setAdapter(checkboxAdapter);

        btnAdcTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String novaTarefa = "Nova Tarefa";
                checkboxAdapter.addTarefa(novaTarefa);
            }
        });
    }

    @Override
    public void onEditTask(final int position) {
        final EditText input = new EditText(this);
        input.setText(tarefas.get(position));

        new AlertDialog.Builder(this)
                .setTitle("Editar Tarefa")
                .setView(input)
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newTarefa = input.getText().toString();
                        if (!newTarefa.isEmpty()) {
                            checkboxAdapter.updateTarefa(position, newTarefa);
                        } else {
                            Toast.makeText(TelaTarefa.this, "O nome da tarefa não pode ser vazio", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", null)
                .setNeutralButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkboxAdapter.removeTarefa(position);
                    }
                })
                .show();
    }
}
