package com.dam.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void Hacer_peticion_REST(View view) {
        // 1. CORRECCIÓN DE IDS: Ahora buscamos etDni y etPass (como pusimos en el XML)
        EditText etDni = findViewById(R.id.etDni);
        EditText etPass = findViewById(R.id.etPass);

        String dniTexto = etDni.getText().toString();
        String passTexto = etPass.getText().toString();

        // Validamos que no estén vacíos antes de lanzar el diálogo
        if (dniTexto.isEmpty() || passTexto.isEmpty()) {
            Toast.makeText(this, "Por favor, introduce tus credenciales", Toast.LENGTH_SHORT).show();
            return;
        }

        final String URL = "http://10.0.2.2:8080/api/auth?dni=" + dniTexto + "&contrasena=" + passTexto;

        final ProgressDialog dlg = ProgressDialog.show(this, "Conectando con VoltFleet", "Autenticando credenciales...", true);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (dlg != null && dlg.isShowing()) dlg.dismiss();

                        try {
                            int rol_recibido = response.getInt("rol");
                            String dni_recibido = response.getString("dni");
                            String nombre_recibido = response.optString("nombre", "Usuario");

                            Intent intent = new Intent(LoginActivity.this, Mainactivity.class);
                            intent.putExtra("DNI_USUARIO", dni_recibido);
                            intent.putExtra("ROL_USUARIO", rol_recibido);
                            intent.putExtra("NOMBRE_USUARIO", nombre_recibido);

                            // Lógica de roles
                            if (rol_recibido == 1 || rol_recibido == 2) {
                                Toast.makeText(getApplicationContext(), "Bienvenido: " + nombre_recibido, Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Rol no autorizado", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (dlg != null && dlg.isShowing()) dlg.dismiss();
                Log.e("AuthError", "Error: " + error.toString());

                // --- MOCKING PARA PRUEBAS VISUALES ---
                // Si el servidor falla, entramos como Ana Gestora para ver el mapa bonito
                Intent intent = new Intent(LoginActivity.this, Mainactivity.class);
                intent.putExtra("NOMBRE_USUARIO", "Ana Gestora (Mock)");
                startActivity(intent);
                finish();
            }
        });

        VoltFleetApp.getInstance().getRequestQueue().add(request);
    }
}