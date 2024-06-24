package com.example.dailygaily.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailygaily.R;
import com.example.dailygaily.database.LocalDatabase;
import com.example.dailygaily.entities.Usuario;

public class TelaCadastro extends AppCompatActivity {

    private LocalDatabase db;
    private TextView txtLogin;
    private EditText edtNomeCadastro, edtEmailCadastro, edtSenhaCadastro;
    private int dbUsuarioId;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        db = LocalDatabase.getDatabase(getApplicationContext());
        edtNomeCadastro = findViewById(R.id.edtNomeCadastro);
        edtEmailCadastro = findViewById(R.id.edtEmailCadastro);
        edtSenhaCadastro = findViewById(R.id.edtSenhaCadastro);
        txtLogin = findViewById(R.id.txtLogin);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaCadastro.this, MainActivity.class);
                startActivity(it);
            }
        });

        // Initialize dbUsuarioId with a default value
        dbUsuarioId = -1;  // Assuming -1 indicates no user ID

        // Retrieve dbUsuarioId from Intent or SharedPreferences if necessary
        // Example (if stored in SharedPreferences):
        SharedPreferences sharedPreferences = getSharedPreferences("UserPref", MODE_PRIVATE);
        dbUsuarioId = sharedPreferences.getInt("USER_ID", -1);
    }

    public void salvarUsuario(View view) {
        String nome = edtNomeCadastro.getText().toString();
        String email = edtEmailCadastro.getText().toString();
        String senha = edtSenhaCadastro.getText().toString();

        if (!nome.isEmpty() && !email.isEmpty() && !senha.isEmpty()) {
            if (dbUsuarioId != -1) {
                usuario = db.usuarioModel().getUsuario(dbUsuarioId);
                if (usuario != null) {
                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    usuario.setSenha(senha);
                    db.usuarioModel().update(usuario);
                    Toast.makeText(this, "Usuário Atualizado", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Erro: Usuário não encontrado", Toast.LENGTH_LONG).show();
                }
            } else {
                Usuario novoUsuario = new Usuario();
                novoUsuario.setNome(nome);
                novoUsuario.setEmail(email);
                novoUsuario.setSenha(senha);
                db.usuarioModel().insertAll(novoUsuario);
                Toast.makeText(this, "Usuário Inserido", Toast.LENGTH_LONG).show();
            }
            finish();
        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
        }
    }

    /*
    private LocalDatabase db;
    private TextView txtLogin;
    private EditText edtNomeCadastro, edtEmailCadastro, edtSenhaCadastro;
    private int dbUsuarioId;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        db=LocalDatabase.getDatabase(getApplicationContext());
        edtNomeCadastro=findViewById(R.id.edtNomeCadastro);
        edtEmailCadastro=findViewById(R.id.edtEmailCadastro);
        edtSenhaCadastro=findViewById(R.id.edtSenhaCadastro);
        txtLogin = findViewById(R.id.txtLogin);


        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(TelaCadastro.this, MainActivity.class);
                startActivity(it);
            }
        });
    }

    public void salvarUsuario(View view) {
        String nome = edtNomeCadastro.getText().toString();
        String email = edtEmailCadastro.getText().toString();
        String senha = edtSenhaCadastro.getText().toString();
        if (!nome.equals("") && !email.equals("") && !senha.equals("")) {
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome(nome);
            novoUsuario.setEmail(email);
            novoUsuario.setSenha(senha);
            if (dbUsuarioId != -1) {
                usuario = db.usuarioModel().getUsuario(dbUsuarioId);
                usuario.setUsuarioID(dbUsuarioId);
                db.usuarioModel().update(usuario);
            } else {
                db.usuarioModel().insertAll(novoUsuario);
                Toast.makeText(this, "Usuário Inserido",
                        Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }
*/
}