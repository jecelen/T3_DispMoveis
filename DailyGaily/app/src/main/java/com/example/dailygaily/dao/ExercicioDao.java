package com.example.dailygaily.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dailygaily.entities.Exercicio;


import java.util.List;

@Dao
public interface ExercicioDao {

    @Query("SELECT * FROM Exercicio WHERE exercicioID = :idExe LIMIT 1")
    Exercicio getExercicio(int idExe);

    @Insert
    void insertAll(Exercicio exercicio);

    @Update
    void update(Exercicio exercicio);

    @Delete
    void delete(Exercicio exercicio);

    @Query("SELECT * FROM Exercicio")
    List<Exercicio> getAllExe();

    @Query("SELECT * FROM Exercicio WHERE usuarioID = :usuarioID")
    List<Exercicio> getExerciciosByUsuarioID(int usuarioID);
}
