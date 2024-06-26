package com.example.dailygaily.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.dailygaily.dao.ConsumoDeAguaDao;

import java.sql.Time;

@Entity
public class ConsumoDeAgua {
    @PrimaryKey(autoGenerate = true)
    private int consumoID;
    private int mlConsumido;
    private String date;
    private String horario;


    public ConsumoDeAgua(){
    };


    // Getters e Setters
    public int getConsumoID() {
        return consumoID;
    }

    public void setConsumoID(int consumoID) {
        this.consumoID = consumoID;
    }
    public String getHorario() {
        return horario;
    }
    public void setHorario(String horario) {
        this.horario = horario;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public int getMlConsumido() {
        return mlConsumido;
    }

    public void setMlConsumido(int mlConsumido) {
        this.mlConsumido = mlConsumido;
    }

}
