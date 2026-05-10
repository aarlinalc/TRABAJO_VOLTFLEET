package com.dam.app;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.util.ArrayList;

public class DetalleEstacionActivity extends AppCompatActivity {

    private ListView lvCargadores;
    private ArrayList<String> listaCargadores = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_estacion);

        lvCargadores = findViewById(R.id.lvCargadores);
        TextView tvTitulo = findViewById(R.id.tvTituloDetalle);

        // Recibimos los datos del mapa
        int id = getIntent().getIntExtra("ESTACION_ID", -1);
        String nombre = getIntent().getStringExtra("ESTACION_NOMBRE");
        tvTitulo.setText(nombre);

        // Preparamos la lista
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCargadores);
        lvCargadores.setAdapter(adapter);

        // Pedimos los datos al servidor
        obtenerCargadoresDelServidor(id);
    }

    private void obtenerCargadoresDelServidor(int id) {
        String url = "http://10.0.2.2:8080/api/estaciones/" + id + "/cargadores";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        listaCargadores.clear();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            String info = obj.getString("tipo") + " [" + obj.getInt("potenciaKw") + "kW]";
                            listaCargadores.add(info);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Error conectando con el servidor", Toast.LENGTH_SHORT).show()
        );
        Volley.newRequestQueue(this).add(request);
    }
}