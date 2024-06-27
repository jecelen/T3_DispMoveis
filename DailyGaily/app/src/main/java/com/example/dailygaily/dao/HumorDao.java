package com.example.dailygaily.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.dailygaily.entities.Humor;

import java.util.List;

@Dao
public interface HumorDao {
    @Query("SELECT * FROM Humor WHERE humorID = :idHum LIMIT 1")
    Humor getHumor(int idHum);

    @Query("SELECT * FROM Humor")
    List<Humor> getAll();

    @Insert
    void insertAll(Humor... humor);

    @Update
    void update(Humor humor);

    @Delete
    void delete(Humor humor);

    @Query("SELECT * FROM Humor ORDER BY humorID DESC LIMIT 1")
    Humor getLastHumor();
}
