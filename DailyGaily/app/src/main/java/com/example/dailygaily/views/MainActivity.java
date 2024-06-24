package com.example.dailygaily.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailygaily.R;
import com.example.dailygaily.dao.UsuarioDao;
import com.example.dailygaily.database.LocalDatabase;
import com.example.dailygaily.entities.Usuario;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView txtCadastro;
    private EditText edtEmailLogin, edtSenhaLogin;
    private int dbUsuarioId;
    private Button btnEntrar;
    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtSenhaLogin = findViewById(R.id.edtSenhaLogin);
        btnEntrar = findViewById(R.id.btnEntrar);
        txtCadastro = findViewById(R.id.txtCadastro);
        dbUsuarioId=getIntent().getIntExtra("usuario_id",-1);
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);

        //listAllUsers();

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String email = edtEmailLogin.getText().toString();
                //String senha = edtSenhaLogin.getText().toString();
                login();

            }
        });

        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, TelaCadastro.class);
                startActivity(it);
            }
        });
    }

    private void login() {
        String username = edtEmailLogin.getText().toString();
        String password = edtSenhaLogin.getText().toString();

        LocalDatabase db = LocalDatabase.getDatabase(this);
        assert db != null;
        UsuarioDao usuarioDao = db.usuarioModel();
        Usuario usuario = usuarioDao.getUsuarioEmailAndSenha(username, password);

        if (usuario != null) {
            // Usuário autenticado com sucesso
            // Salvar o ID do usuário no SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("usuarioId", usuario.getUsuarioID());
            editor.apply();

            startActivity(new Intent(this, TelaInicial.class));
            finish();
        } else {
            // Falha na autenticação
            Toast.makeText(this, "Usuário ou senha inválidos", Toast.LENGTH_LONG).show();
        }
    }
}

/*public static boolean isValidEmail(CharSequence target) {
    return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
}

    private boolean isValidLogin(String email, String senha) {
        UsuarioDao usuarioDao = db.usuarioModel();
        Usuario usuario = usuarioDao.getUsuarioByEmailAndPassword(email, senha);
        if (usuario != null) {
            Log.d("Login", "Usuário encontrado: " + usuario.getEmail());
            return true;
        } else {
            Log.d("Login", "Nenhum usuário encontrado com email: " + email);
            return false;
        }
    }

    private void listAllUsers() {
        UsuarioDao usuarioDao = db.usuarioModel();
        List<Usuario> usuarios = usuarioDao.getAll();
        for (Usuario usuario : usuarios) {
            Log.d("DB", "Usuario: " + usuario.getEmail() + ", Senha: " + usuario.getSenha());
        }
    }
 */