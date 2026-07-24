package com.hospitalapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hospitalapp.R;
import com.hospitalapp.controllers.TablaController;

/**
 * View: funciona como menú principal (navega a las otras 4 pantallas) y como
 * visor del contenido crudo de las 4 tablas de la base de datos.
 * CORREGIDO: se eliminó todo el código muerto/comentado que había aquí antes;
 * el acceso a SQLite ahora vive en TablaController (Controller).
 */
public class VisorTablasActivity extends AppCompatActivity {

    private static final String[] TABLAS = {"usuarios", "doctores", "pacientes", "consultas"};

    private Spinner spinnerTablas;
    private TextView tvContenidoTabla;
    private TablaController tablaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_tablas);

        tablaController = new TablaController(this);

        Button btnWifi = findViewById(R.id.btnWifi);
        Button btnMapa = findViewById(R.id.btnMapa);
        Button btnRegistro = findViewById(R.id.btnRegistro);
        Button btnPacientes = findViewById(R.id.btnPacientes);

        btnWifi.setOnClickListener(v -> startActivity(new Intent(this, WifiDetailsActivity.class)));
        btnMapa.setOnClickListener(v -> startActivity(new Intent(this, UbicacionMapaActivity.class)));
        btnRegistro.setOnClickListener(v -> startActivity(new Intent(this, RegistroPacienteActivity.class)));
        btnPacientes.setOnClickListener(v -> startActivity(new Intent(this, PacientesActivity.class)));

        spinnerTablas = findViewById(R.id.spinnerTablas);
        tvContenidoTabla = findViewById(R.id.tvContenidoTabla);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, TABLAS);
        spinnerTablas.setAdapter(adapter);

        spinnerTablas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mostrarContenidoTabla(TABLAS[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void mostrarContenidoTabla(String nombreTabla) {
        tvContenidoTabla.setText(tablaController.obtenerContenidoComoTexto(nombreTabla));
    }
}
