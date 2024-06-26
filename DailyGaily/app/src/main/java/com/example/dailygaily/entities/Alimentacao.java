package com.example.dailygaily.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(foreignKeys = @ForeignKey(entity = Usuario.class,
        parentColumns = "usuarioID", childColumns = "usuarioID",
        onDelete = ForeignKey.CASCADE))
public class Alimentacao {
    @PrimaryKey(autoGenerate = true)
    private int alimentacaoID;
    private String descricao;
    private Double calorias;
    private String nomeDoLocal;
    private double latitude;
    private double longitude;
    private int usuarioID;

    public Alimentacao(){
    }

    public Alimentacao(String descricao, Double calorias){
        this.descricao = descricao;
        this.calorias = calorias;
    }

    public int getAlimentacaoID() {
        return alimentacaoID;
    }

    public void setAlimentacaoID(int alimentacaoID) {
        this.alimentacaoID = alimentacaoID;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getCalorias() {
        return calorias;
    }

    public void setCalorias(Double calorias) {
        this.calorias = calorias;
    }

    public String getNomeDoLocal() {
        return nomeDoLocal;
    }

    public void setNomeDoLocal(String nomeDoLocal) {
        this.nomeDoLocal = nomeDoLocal;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    @Override
    public String toString() {
        return  descricao + '\n' +
                calorias +" calorias";
    }
}
