package com.example.dailygaily.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.dailygaily.R;
import com.example.dailygaily.dao.AlimentacaoDao;
import com.example.dailygaily.database.LocalDatabase;
import com.example.dailygaily.entities.Alimentacao;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaEndereco extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapaEndereco";

    private GoogleMap map;

    private double lati, longi;
    private String endMarcado;
    private int alimentacaoId;
    private SharedPreferences sharedPreferences;
    private Button voltar;
    private Button edtEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_endereco);

        // Initialize location data
//        LatLng latLng = obterLocalizacao();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(this, "Erro ao carregar o mapa", Toast.LENGTH_SHORT).show();
        }
        voltar = findViewById(R.id.btn_voltarEndereco);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapaEndereco.this, TelaAlimentacao.class);
                startActivity(intent);
            }
        });
        edtEnd = findViewById(R.id.edtEndMapa);
        edtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapaEndereco.this, TelaAlimentacao.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        sharedPreferences = getSharedPreferences("Local", MODE_PRIVATE);
        endMarcado = sharedPreferences.getString("local", "endereço não encontrado.");
        lati = sharedPreferences.getFloat("lati", 0);
        longi = sharedPreferences.getFloat("longi", 0);
        LatLng latLng = new LatLng(lati, longi);
        map.addMarker(new MarkerOptions().position(latLng).title(endMarcado));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
    }

}
