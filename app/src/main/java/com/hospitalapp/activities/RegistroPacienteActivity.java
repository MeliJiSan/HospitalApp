package com.hospitalapp.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hospitalapp.ConsultaFragment;
import com.hospitalapp.R;
import com.hospitalapp.controllers.ConsultaController;
import com.hospitalapp.controllers.DoctorController;
import com.hospitalapp.controllers.PacienteController;
import com.hospitalapp.models.Consulta;
import com.hospitalapp.models.Doctor;
import com.hospitalapp.models.Paciente;

import java.util.List;

/**
 * View: registra un paciente junto con su consulta (a través del ConsultaFragment).
 * CORREGIDO: antes el doctor quedaba fijo en id=1; ahora se elige de un Spinner
 * cargado con los doctores reales de la base de datos.
 */
public class RegistroPacienteActivity extends AppCompatActivity {

    private EditText etNombre, etEdad;
    private Spinner spinnerDoctor;
    private ConsultaFragment consultaFragment;

    private PacienteController pacienteController;
    private ConsultaController consultaController;
    private DoctorController doctorController;
    private List<Doctor> doctores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paciente);

        pacienteController = new PacienteController(this);
        consultaController = new ConsultaController(this);
        doctorController = new DoctorController(this);

        etNombre = findViewById(R.id.etNombrePaciente);
        etEdad = findViewById(R.id.etEdadPaciente);
        spinnerDoctor = findViewById(R.id.spinnerDoctor);

        cargarDoctoresEnSpinner();

        // Instancia e inserta el Fragment que contiene hora de inicio, hora de salida
        // y observaciones de la consulta
        consultaFragment = new ConsultaFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, consultaFragment)
                .commit();

        Button btnGuardar = findViewById(R.id.btnGuardarTodo);
        btnGuardar.setOnClickListener(v -> guardarPacienteYConsulta());
    }

    private void cargarDoctoresEnSpinner() {
        doctores = doctorController.obtenerTodos();
        ArrayAdapter<Doctor> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, doctores);
        spinnerDoctor.setAdapter(adapter);
    }

    private void guardarPacienteYConsulta() {
        String nombre = etNombre.getText().toString().trim();
        String edadStr = etEdad.getText().toString().trim();
        String horaInicio = consultaFragment.getHoraInicio();
        String horaFin = consultaFragment.getHoraFin();
        String observaciones = consultaFragment.getObservaciones();

        if (nombre.isEmpty() || edadStr.isEmpty() || horaInicio.isEmpty() || horaFin.isEmpty()) {
            Toast.makeText(this, "Por favor llena todos los datos requeridos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (doctores == null || doctores.isEmpty()) {
            Toast.makeText(this, "No hay doctores registrados en la base de datos", Toast.LENGTH_SHORT).show();
            return;
        }

        Doctor doctorSeleccionado = doctores.get(spinnerDoctor.getSelectedItemPosition());

        Paciente paciente = new Paciente(nombre, Integer.parseInt(edadStr), doctorSeleccionado.getId());
        long idPaciente = pacienteController.insertar(paciente);

        Consulta consulta = new Consulta((int) idPaciente, horaInicio, horaFin, observaciones);
        consultaController.insertar(consulta);

        Toast.makeText(this, "¡Paciente y Consulta guardados con éxito!", Toast.LENGTH_LONG).show();
        finish();
    }
}
