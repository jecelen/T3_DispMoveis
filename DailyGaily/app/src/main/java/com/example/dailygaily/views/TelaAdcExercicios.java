package com.example.dailygaily.views;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dailygaily.R;
import com.example.dailygaily.dao.ExercicioDao;
import com.example.dailygaily.dao.UsuarioDao;
import com.example.dailygaily.database.LocalDatabase;
import com.example.dailygaily.entities.Exercicio;
import com.example.dailygaily.entities.Usuario;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaAdcExercicios extends AppCompatActivity {

    private LocalDatabase db;
    private EditText edtTipoExercicio, edtTempoExercicio;
    private Double tempo;
    private Button btnSalvarExercicio;
    private SharedPreferences sharedPreferences;
    private int dbUsuarioId;
    private ImageButton imgVoltar;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_GALLERY_PICK = 2;
    private static final int REQUEST_PERMISSIONS = 3;
    private ImageView fotoExercicio;
    private Uri photoURI;
    private Button btnAdicionarFoto;

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
        fotoExercicio = findViewById(R.id.fotoExercicio);
        btnAdicionarFoto = findViewById(R.id.btn_adcfoto);

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
        btnAdicionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(TelaAdcExercicios.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(TelaAdcExercicios.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(TelaAdcExercicios.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_PERMISSIONS);
                    } else {
                        abrirDialogSelecaoImagem();
                    }
                } else {
                    abrirDialogSelecaoImagem();
                }
            }
        });
    }

    private void abrirDialogSelecaoImagem() {
        final CharSequence[] items = {"Tirar Foto", "Escolher da Galeria", "Cancelar"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TelaAdcExercicios.this);
        builder.setTitle("Adicionar Foto");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Tirar Foto")) {
                    iniciarCapturaFoto();
                } else if (items[item].equals("Escolher da Galeria")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_GALLERY_PICK);
                } else if (items[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void iniciarCapturaFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = criarArquivoImagem();
            } catch (IOException ex) {
                Log.e("Erro", ex.getMessage());
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.dailygaily.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                            if (result.getResultCode() == RESULT_OK) {
                                if (result.getData() != null) {
                                    onActivityResult(REQUEST_IMAGE_CAPTURE, RESULT_OK, result.getData());
                                }
                            }
                        });

                someActivityResultLauncher.launch(takePictureIntent);
            } else {
                Toast.makeText(this, "Não foi possível criar o arquivo da imagem.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Não há aplicativo de câmera disponível.", Toast.LENGTH_SHORT).show();
        }
    }

    private File criarArquivoImagem() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nomeArquivoImagem = "JPEG_" + timeStamp + "_";
        File diretorioArmazenamento = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagem = File.createTempFile(
                nomeArquivoImagem,  /* prefixo */
                ".jpg",         /* sufixo */
                diretorioArmazenamento      /* diretório */
        );
        return imagem;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                fotoExercicio.setImageURI(photoURI);
            } else if (requestCode == REQUEST_GALLERY_PICK) {
                photoURI = data.getData();
                fotoExercicio.setImageURI(photoURI);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirDialogSelecaoImagem();
            } else {
                Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show();
            }
        }
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
        if (photoURI != null) {
            novoExercicio.setFotoUri(photoURI.toString());
        }

        db.exercicioModel().insertAll(novoExercicio);
        Toast.makeText(this, "Exercício salvo com sucesso!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(TelaAdcExercicios.this, TelaExercicios.class);
        startActivity(intent);
    }

}
