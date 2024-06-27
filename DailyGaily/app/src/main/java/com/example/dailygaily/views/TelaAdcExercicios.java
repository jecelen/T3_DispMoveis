package com.example.dailygaily.views;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.dailygaily.R;
import com.example.dailygaily.database.LocalDatabase;
import com.example.dailygaily.entities.Exercicio;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TelaAdcExercicios extends AppCompatActivity {

    private LocalDatabase db;
    private EditText edtTipoExercicio, edtTempoExercicio;
    private Double tempo;
    private Button btnSalvarExercicio;
    private SharedPreferences sharedPreferences;
    private int dbUsuarioId;
    private ImageButton imgVoltar;
    private ImageView fotoExercicio;
    private Uri photoURI;
    private Button btnAdicionarFoto;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_GALLERY_PICK = 2;
    private static final int REQUEST_PERMISSIONS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adc_exercicios);
        db = LocalDatabase.getDatabase(getApplicationContext());

        edtTipoExercicio = findViewById(R.id.edtTipoExercicio);
        edtTempoExercicio = findViewById(R.id.edtTempoExercicio);
        btnSalvarExercicio = findViewById(R.id.btnSalvarExercicio);
        imgVoltar = findViewById(R.id.imgVoltar);
        fotoExercicio = findViewById(R.id.fotoExercicio);
        btnAdicionarFoto = findViewById(R.id.btn_adcfoto);

        btnSalvarExercicio.setOnClickListener(this::salvarExercicio);
        imgVoltar.setOnClickListener(v -> finish());
        btnAdicionarFoto.setOnClickListener(this::handleAddPhotoClick);
    }

    private void handleAddPhotoClick(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSIONS);
            } else {
                abrirDialogSelecaoImagem();
            }
        } else {
            abrirDialogSelecaoImagem();
        }
    }

    private void abrirDialogSelecaoImagem() {
        final CharSequence[] items = {"Tirar Foto", "Escolher da Galeria", "Cancelar"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Adicionar Foto");
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Tirar Foto")) {
                iniciarCapturaFoto();
            } else if (items[item].equals("Escolher da Galeria")) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_GALLERY_PICK);
            } else if (items[item].equals("Cancelar")) {
                dialog.dismiss();
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
                photoURI = FileProvider.getUriForFile(this, "com.example.dailygaily.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } else {
                Toast.makeText(this, "Não foi possível criar o arquivo da imagem.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Não há aplicativo de câmera disponível.", Toast.LENGTH_SHORT).show();
        }
    }

    private File criarArquivoImagem() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
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
        if (requestCode == REQUEST_PERMISSIONS && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            abrirDialogSelecaoImagem();
        } else {
            Toast.makeText(this, "Permissão negada.", Toast.LENGTH_SHORT).show();
        }
    }

    private void salvarExercicio(View view) {
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        dbUsuarioId = sharedPreferences.getInt("usuarioId", -1);
        String tipoExercicio = edtTipoExercicio.getText().toString();
        String tempoExercicio = edtTempoExercicio.getText().toString();
        tempo = Double.parseDouble(tempoExercicio);

        Exercicio novoExercicio = new Exercicio();
        novoExercicio.setTipoDeExercicio(tipoExercicio);
        novoExercicio.setTempo(tempo);
        novoExercicio.setUsuarioID(dbUsuarioId);
        if (photoURI != null) {
            novoExercicio.setFotoUri(photoURI.toString());
        }

        db.exercicioModel().insertAll(novoExercicio);
        Toast.makeText(this, "Exercício salvo com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
