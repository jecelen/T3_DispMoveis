package com.example.dailygaily.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dailygaily.entities.ConsumoDeAgua;

import java.util.List;

@Dao
public interface ConsumoDeAguaDao {

    @Query("SELECT * FROM ConsumoDeAgua WHERE consumoID = :idCon LIMIT 1")
    ConsumoDeAgua getConsumo(int idCon);

    @Query("SELECT * FROM ConsumoDeAgua")
    List<ConsumoDeAgua> getAll();

    @Insert
    void insertAll(ConsumoDeAgua... consumo);

    @Update
    void update(ConsumoDeAgua consumo);

    @Delete
    void delete(ConsumoDeAgua consumo);
}
