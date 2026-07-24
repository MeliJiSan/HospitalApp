package com.hospitalapp.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.hospitalapp.R;
import com.hospitalapp.database.DatabaseHelper;

public class VisorTablasActivity extends AppCompatActivity {

    /*private Spinner spinnerTablas;
    private TextView tvContenidoTabla;
    //private TextView tvContenido;
    private DatabaseHelper dbHelper;*/
    //private Button btnWifi, btnMapa, btnRegistro, btnPacientes;
    private Button btnWifi, btnMapa, btnRegistro, btnPacientes;
    private Spinner spinnerTablas;
    private TextView tvContenidoTabla;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_tablas);

        // 1. Inicializar la Base de Datos
        dbHelper = new DatabaseHelper(this);

        // Vincular cada botón con su ID del archivo XML
        btnWifi = findViewById(R.id.btnWifi);
        btnMapa = findViewById(R.id.btnMapa);
        btnRegistro = findViewById(R.id.btnRegistro);
        btnPacientes = findViewById(R.id.btnPacientes);

        // Navegar a Detalles de WiFi
        btnWifi.setOnClickListener(v -> startActivity(new Intent(this, WifiDetailsActivity.class)));

        // Navegar a Ubicación / Mapa
        btnMapa.setOnClickListener(v -> startActivity(new Intent(this, UbicacionMapaActivity.class)));

        // Navegar a Registrar Paciente (con Fragment)
        btnRegistro.setOnClickListener(v -> startActivity(new Intent(this, RegistroPacienteActivity.class)));

        // Navegar a Ver Pacientes y Doctor
        btnPacientes.setOnClickListener(v -> startActivity(new Intent(this, PacientesActivity.class)));

        /*dbHelper = new DatabaseHelper(this);
        //Spinner spinnerTablas = findViewById(R.id.spinnerTablas);
        spinnerTablas = findViewById(R.id.spinnerTablas);
        //tvContenido = findViewById(R.id.tvContenidoTabla);
        tvContenidoTabla = findViewById(R.id.tvContenidoTabla);*/

        String[] tablas = {"usuarios", "doctores", "pacientes", "consultas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tablas);
        spinnerTablas.setAdapter(adapter);

        spinnerTablas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //mostrarTabla(tablas[position]);
                mostrarContenidoTabla(tablas[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    /*private void mostrarTabla(String nombreTabla) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + nombreTabla, null);

        StringBuilder sb = new StringBuilder();
        sb.append("REGISTROS DE LA TABLA: ").append(nombreTabla.toUpperCase()).append("\n");
        sb.append("=========================================\n\n");

        String[] columnas = cursor.getColumnNames();

        while (cursor.moveToNext()) {
            for (String col : columnas) {
                int index = cursor.getColumnIndex(col);
                sb.append(col).append(": ").append(cursor.getString(index)).append("\n");
            }
            sb.append("-----------------------------------------\n");
        }
        cursor.close();

        tvContenido.setText(sb.toString());
    }*/
    private void mostrarContenidoTabla(String nombreTabla) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + nombreTabla, null);

        StringBuilder builder = new StringBuilder();
        builder.append("TABLA: ").append(nombreTabla.toUpperCase()).append("\n");
        builder.append("---------------------------------------\n");

        String[] columnNames = cursor.getColumnNames();

        while (cursor.moveToNext()) {
            for (String col : columnNames) {
                int index = cursor.getColumnIndex(col);
                builder.append(col).append(": ").append(cursor.getString(index)).append("\n");
            }
            builder.append("---------------------------------------\n");
        }
        cursor.close();

        tvContenidoTabla.setText(builder.toString());
    }
}