package com.example.dailygaily.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dailygaily.entities.Alimentacao;
import com.example.dailygaily.entities.Exercicio;

import java.util.List;

@Dao
public interface AlimentacaoDao {
    @Query("SELECT * FROM Alimentacao WHERE alimentacaoID = :idAli LIMIT 1")
    Alimentacao getAlimentacao(int idAli);

    @Query("SELECT * FROM Alimentacao")
    List<Alimentacao> getAll();

    @Insert
    void insertAll(Alimentacao... alimentacao);

    @Update
    void update(Alimentacao alimentacao);

    @Delete
    void delete(Alimentacao alimentacao);

    @Query("SELECT * FROM Alimentacao WHERE usuarioID = :usuarioID")
    List<Alimentacao> getAlimentacaoByUsuarioID(int usuarioID);
}
