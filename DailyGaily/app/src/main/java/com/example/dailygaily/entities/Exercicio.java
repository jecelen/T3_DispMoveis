package com.example.dailygaily.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Time;

@Entity(foreignKeys = @ForeignKey(entity = Usuario.class,
        parentColumns = "usuarioID", childColumns = "usuarioID",
        onDelete = ForeignKey.CASCADE))
public class Exercicio {
    @PrimaryKey(autoGenerate = true)
    private int exercicioID;
    private String tipoDeExercicio;
    private Double tempo;
    private int usuarioID;

    public Exercicio() {
    }

    public Exercicio(String tipoDeExercicio, Double tempo) {
        this.tipoDeExercicio = tipoDeExercicio;
        this.tempo = tempo;
    }

    public int getExercicioID() {
        return exercicioID;
    }

    public void setExercicioID(int exercicioID) {
        this.exercicioID = exercicioID;
    }

    public String getTipoDeExercicio() {
        return tipoDeExercicio;
    }

    public void setTipoDeExercicio(String tipoDeExercicio) {
        this.tipoDeExercicio = tipoDeExercicio;
    }

    public Double getTempo() {
        return tempo;
    }

    public void setTempo(Double tempo) {
        this.tempo = tempo;
    }


    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    @Override
    public String toString() {
        return  tipoDeExercicio + '\n' +
                "Tempo:" + tempo +" min";
    }
}
