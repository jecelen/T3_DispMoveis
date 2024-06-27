package com.example.dailygaily.views;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.dailygaily.R;
import com.example.dailygaily.adapter.CheckboxAdapter;

import java.util.ArrayList;

public class TelaTarefa extends AppCompatActivity implements CheckboxAdapter.OnEditTaskListener {

    private Button btnAdcTarefa;
    private ListView listViewTarefas;
    private CheckboxAdapter checkboxAdapter;
    private ArrayList<String> tarefas;
    String novaTarefa, newTarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_tarefa);

        createNotificationChannel();

        btnAdcTarefa = findViewById(R.id.btnAdcTarefa);
        listViewTarefas = findViewById(R.id.listViewTarefas);

        tarefas = new ArrayList<>();
        checkboxAdapter = new CheckboxAdapter(this, tarefas, this);
        listViewTarefas.setAdapter(checkboxAdapter);

        btnAdcTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                novaTarefa = "Nova Tarefa";
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
                        newTarefa = input.getText().toString();
                        if (!newTarefa.isEmpty()) {
                            checkboxAdapter.updateTarefa(position, newTarefa);
                            createNotification();
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

    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 101;


    public void createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "YOUR_CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Tarefa concluída:")
                .setContentText(newTarefa)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_REQUEST_CODE);
        } else {
            notificationManager.notify(1, builder.build());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão concedida, enviar a notificação
                createNotification();
            } else {
                // Permissão negada, lidar com a falta de permissão de notificação
                Toast.makeText(this, "Permissão para mostrar notificações negada.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
