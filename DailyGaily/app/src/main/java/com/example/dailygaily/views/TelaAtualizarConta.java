package com.example.dailygaily.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dailygaily.R;
import com.example.dailygaily.dao.UsuarioDao;
import com.example.dailygaily.database.LocalDatabase;
import com.example.dailygaily.entities.Exercicio;
import com.example.dailygaily.entities.Usuario;

public class TelaAtualizarConta extends AppCompatActivity {

    private EditText edtEmailAtt, edtSenhaAtt, edtNomeAtt;
    private int dbUsuarioId;
    private Button btnDeletarConta, btnAtualizarConta;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_atualizar_conta);
        edtNomeAtt = findViewById(R.id.edtNomeAtt);
        edtEmailAtt = findViewById(R.id.edtEmailAtt);
        edtSenhaAtt = findViewById(R.id.edtSenhaAtt);
        btnAtualizarConta = findViewById(R.id.btnAtualizarConta);
        btnDeletarConta = findViewById(R.id.btnDeletarConta);
        Log.d("IDusu", "ID:"+ dbUsuarioId);

        btnAtualizarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarUsuarioAtt();
            }
        });

        preencherDadosUsuario();
    }


    private void salvarUsuarioAtt() {
        String novoNome = edtNomeAtt.getText().toString();
        String novoEmail = edtEmailAtt.getText().toString();
        String novaSenha = edtSenhaAtt.getText().toString();

        // Obtém o usuário do banco de dados pelo ID
        UsuarioDao usuarioDao = LocalDatabase.getDatabase(this).usuarioModel();
        Usuario usuario = usuarioDao.getUsuario(dbUsuarioId);

        // Verifica se o usuário foi encontrado
        if (usuario != null) {
            // Verifica se houve alterações nos dados do usuário
            if (!usuario.getNome().equals(novoNome) ||
                    !usuario.getEmail().equals(novoEmail) ||
                    !usuario.getSenha().equals(novaSenha)) {

                // Atualiza os dados do usuário no banco de dados
                usuario.setNome(novoNome);
                usuario.setEmail(novoEmail);
                usuario.setSenha(novaSenha);
                usuarioDao.update(usuario);
                showToast("Dados atualizados com sucesso");

                // Volta para a tela inicial
                Intent intent = new Intent(TelaAtualizarConta.this, TelaInicial.class);
                startActivity(intent);
                finish();
            } else {
                showToast("Nenhuma alteração realizada");
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void preencherDadosUsuario() {
        // Obtém o usuário do banco de dados pelo ID
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        dbUsuarioId = sharedPreferences.getInt("usuarioId", -1);
        UsuarioDao usuarioDao = LocalDatabase.getDatabase(this).usuarioModel();
        Usuario usuario = usuarioDao.getUsuario(dbUsuarioId);
        Log.d("IDexe", "idUsuario:"+dbUsuarioId);

        // Verifica se o usuário foi encontrado
        if (usuario != null) {
            // Preenche os TextViews com os dados do usuário
            edtNomeAtt.setText(usuario.getNome());
            edtEmailAtt.setText(usuario.getEmail());
            edtSenhaAtt.setText(usuario.getSenha());
        } else {
            // Mostra uma mensagem de erro se o usuário não foi encontrado
            Toast.makeText(this, "Erro: usuário não encontrado", Toast.LENGTH_SHORT).show();

            // Volta para a tela de login
            Intent intent = new Intent(TelaAtualizarConta.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}

/*
public void salvarUsuarioAtt(View view) {
        String nomeUsuario = edtNomeAtt.getText().toString();
        String emailUsuario = edtEmailAtt.getText().toString();
        String senhaUsuario = edtSenhaAtt.getText().toString();
        if (nomeUsuario.equals("")) {
            Toast.makeText(this, "Adicione um nome.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (emailUsuario.equals("")) {
            Toast.makeText(this, "Adicione um email.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (senhaUsuario.equals("")) {
            Toast.makeText(this, "Adicione uma senha.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario thisUsuario = new Usuario(nomeUsuario, emailUsuario, senhaUsuario);

        if (dbUsuario != null) {
            thisUsuario.setUsuarioID(dbUsuarioId);
            db.usuarioModel().update(thisUsuario);
            Toast.makeText(this, "Usuário atualizada com sucesso.", Toast.LENGTH_SHORT).show();
        } else {
            db.usuarioModel().insertAll(thisUsuario);
            Toast.makeText(this, "Usuário criado com sucesso.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
 */