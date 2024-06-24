package com.example.dailygaily.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dailygaily.entities.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Query("SELECT * FROM Usuario WHERE usuarioID=:idUsu LIMIT 1")
    Usuario getUsuario(int idUsu);

    @Query("SELECT * FROM Usuario")
    List<Usuario> getAll();

    @Insert
    void insertAll(Usuario... usuario);

    @Update
    void update(Usuario usuario);

    @Delete
    void delete(Usuario usuario);

    @Query("SELECT * FROM Usuario WHERE email = :email AND senha = :senha LIMIT 1")
    Usuario getUsuarioByEmailAndPassword(String email, String senha);

    @Query("SELECT * FROM Usuario WHERE email = :email AND senha = :senha")
    Usuario getUsuarioEmailAndSenha(String email, String senha);
}