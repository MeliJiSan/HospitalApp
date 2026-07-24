package com.hospitalapp.activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.hospitalapp.R;
import com.hospitalapp.database.DatabaseHelper;
import com.hospitalapp.ConsultaFragment;

public class RegistroPacienteActivity extends AppCompatActivity {

    private EditText etNombre, etEdad;
    private ConsultaFragment consultaFragment;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paciente);

        dbHelper = new DatabaseHelper(this);
        etNombre = findViewById(R.id.etNombrePaciente);
        etEdad = findViewById(R.id.etEdadPaciente);

        // Instancia e inserta el Fragment dinámicamente
        consultaFragment = new ConsultaFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, consultaFragment)
                .commit();

        Button btnGuardar = findViewById(R.id.btnGuardarTodo);
        btnGuardar.setOnClickListener(v -> guardarPacienteYConsulta());
    }

    private void guardarPacienteYConsulta() {
        String nombre = etNombre.getText().toString().trim();
        String edadStr = etEdad.getText().toString().trim();
        String hInicio = consultaFragment.getHoraInicio();
        String hFin = consultaFragment.getHoraFin();
        String obs = consultaFragment.getObservaciones();

        if (nombre.isEmpty() || edadStr.isEmpty() || hInicio.isEmpty() || hFin.isEmpty()) {
            Toast.makeText(this, "Por favor llena todos los datos requeridos", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 1. Guardar Paciente
        ContentValues cvP = new ContentValues();
        cvP.put("nombre", nombre);
        cvP.put("edad", Integer.parseInt(edadStr));
        cvP.put("id_doctor", 1); // Asignado por defecto al doctor id 1
        long idPaciente = db.insert("pacientes", null, cvP);

        // 2. Guardar Consulta asociada
        ContentValues cvC = new ContentValues();
        cvC.put("id_paciente", idPaciente);
        cvC.put("hora_inicio", hInicio);
        cvC.put("hora_fin", hFin);
        cvC.put("observaciones", obs);
        db.insert("consultas", null, cvC);

        Toast.makeText(this, "¡Paciente y Consulta guardados con éxito!", Toast.LENGTH_LONG).show();
        finish();
    }
}