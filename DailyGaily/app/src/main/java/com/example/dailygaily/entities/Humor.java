package com.example.dailygaily.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Time;

@Entity(foreignKeys = @ForeignKey(entity = Usuario.class,
        parentColumns = "usuarioID", childColumns = "usuarioID",
        onDelete = ForeignKey.CASCADE))
public class Humor {
    @PrimaryKey(autoGenerate = true)
    private int humorID;
    private String nome;
    private int usuarioID;

    public Humor(){}

    public Humor(String nome){
        this.nome = nome;
    }

    public int getHumorID() {
        return humorID;
    }

    public void setHumorID(int humorID) {
        this.humorID = humorID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }
}
