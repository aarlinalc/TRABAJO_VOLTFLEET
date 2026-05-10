package com.dam.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

public class Mainactivity extends FragmentActivity implements OnMapReadyCallback {

    private TextView tvBienvenida;
    private Button btnVerMapa;
    private GoogleMap mMap;
    private JSONArray estacionesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivyty);

        tvBienvenida = findViewById(R.id.tvBienvenida);
        btnVerMapa = findViewById(R.id.btnVerMapa);

        String nombre = getIntent().getStringExtra("NOMBRE_USUARIO");
        if (nombre != null) {
            tvBienvenida.setText("Hola, " + nombre);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        btnVerMapa.setOnClickListener(v -> obtenerEstaciones());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // 1. Centrar en Sevilla al empezar
        LatLng sevilla = new LatLng(37.3891, -5.9845);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sevilla, 12));

        // 2. CONFIGURACIÓN CLAVE: Escuchar clic en el cartelito del pin
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                // Recuperamos el ID que guardamos previamente en el "Tag"
                Integer idEstacion = (Integer) marker.getTag();

                if (idEstacion != null) {
                    Intent intent = new Intent(Mainactivity.this, DetalleEstacionActivity.class);
                    intent.putExtra("ESTACION_ID", idEstacion);
                    intent.putExtra("ESTACION_NOMBRE", marker.getTitle());
                    startActivity(intent);
                }
            }
        });
    }

    private void obtenerEstaciones() {
        String url = "http://10.0.2.2:8080/api/estaciones";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url, null,
                response -> {
                    estacionesData = response;
                    mostrarEstacionesEnMapa();
                    Toast.makeText(Mainactivity.this, "Estaciones actualizadas", Toast.LENGTH_SHORT).show();
                },
                error -> Log.e("VOLTFLEET", "Error: " + error.toString())
        );
        Volley.newRequestQueue(this).add(request);
    }

    private void mostrarEstacionesEnMapa() {
        if (mMap == null || estacionesData == null) return;

        mMap.clear();

        try {
            for (int i = 0; i < estacionesData.length(); i++) {
                JSONObject estacion = estacionesData.getJSONObject(i);

                // Extraer datos del JSON
                int id = estacion.getInt("id");
                double lat = estacion.getDouble("latitud");
                double lng = estacion.getDouble("longitud");
                String nombre = estacion.getString("nombre");
                int estado = estacion.getInt("estadoOperativo");

                LatLng posicion = new LatLng(lat, lng);

                // Definir color según estado
                float color;
                String textoEstado;
                switch (estado) {
                    case 1: color = BitmapDescriptorFactory.HUE_GREEN; textoEstado = "Disponible"; break;
                    case 2: color = BitmapDescriptorFactory.HUE_YELLOW; textoEstado = "Ocupada"; break;
                    default: color = BitmapDescriptorFactory.HUE_RED; textoEstado = "Averiada"; break;
                }

                // Crear el marcador
                Marker m = mMap.addMarker(new MarkerOptions()
                        .position(posicion)
                        .title(nombre)
                        .snippet("Estado: " + textoEstado)
                        .icon(BitmapDescriptorFactory.defaultMarker(color)));

                // GUARDAR EL ID EN EL TAG (Fundamental para la Fase 2)
                if (m != null) {
                    m.setTag(id);
                }
            }
        } catch (Exception e) {
            Log.e("VOLTFLEET", "Error procesando JSON: " + e.getMessage());
        }
    }
}