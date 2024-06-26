package com.example.dailygaily.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dailygaily.dao.AlimentacaoDao;
import com.example.dailygaily.dao.ConsumoDeAguaDao;
import com.example.dailygaily.dao.ExercicioDao;
import com.example.dailygaily.dao.HumorDao;
import com.example.dailygaily.dao.UsuarioDao;
import com.example.dailygaily.entities.Alimentacao;
import com.example.dailygaily.entities.ConsumoDeAgua;
import com.example.dailygaily.entities.Exercicio;
import com.example.dailygaily.entities.Humor;
import com.example.dailygaily.entities.Usuario;

@Database(entities = {Usuario.class, Exercicio.class, Alimentacao.class, Humor.class, ConsumoDeAgua.class}, version = 6)
public abstract class LocalDatabase extends RoomDatabase {
    private static LocalDatabase INSTANCE;

    public static LocalDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LocalDatabase.class, "ControleUsuario")
                    .fallbackToDestructiveMigration() // Permitir migrações destrutivas
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public abstract UsuarioDao usuarioModel();
    public abstract AlimentacaoDao alimentacaoModel();
    public abstract ConsumoDeAguaDao consumoModel();
    public abstract ExercicioDao exercicioModel();
    public abstract HumorDao humorModel();
}
