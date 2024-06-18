package com.example.dailygaily.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dailygaily.dao.UsuarioDao;
import com.example.dailygaily.entities.Usuario;

@Database(entities = {Usuario.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    private static LocalDatabase INSTANCE;

    public static LocalDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    LocalDatabase.class, "ControleUsuario").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public abstract UsuarioDao usuarioModel();

}
