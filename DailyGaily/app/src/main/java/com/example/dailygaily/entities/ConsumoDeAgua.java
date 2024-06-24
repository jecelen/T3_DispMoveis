package com.example.dailygaily.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.dailygaily.dao.ConsumoDeAguaDao;

import java.sql.Time;

@Entity(foreignKeys = @ForeignKey(entity = Usuario.class,
        parentColumns = "usuarioID", childColumns = "usuarioID",
        onDelete = ForeignKey.CASCADE))
public class ConsumoDeAgua {
    @PrimaryKey(autoGenerate = true)
    private int consumoID;
    private Double mlConsumido;
    private int usuarioID;

    public ConsumoDeAgua(){}

    public ConsumoDeAgua(Double mlConsumido){
        this.mlConsumido = mlConsumido;
    }

    public int getConsumoID() {
        return consumoID;
    }

    public void setConsumoID(int consumoID) {
        this.consumoID = consumoID;
    }

    public Double getMlConsumido() {
        return mlConsumido;
    }

    public void setMlConsumido(Double mlConsumido) {
        this.mlConsumido = mlConsumido;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }
}
