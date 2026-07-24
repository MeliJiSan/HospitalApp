package com.hospitalapp.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.hospitalapp.R;
import com.hospitalapp.controllers.PacienteController;
import com.hospitalapp.controllers.DoctorController;
import com.hospitalapp.controllers.ConsultaController;

// --- IMPORTACIONES CLAVE QUE FALTABAN ---
import com.hospitalapp.models.Doctor;
import com.hospitalapp.models.Paciente;
import com.hospitalapp.models.Consulta;
import java.util.ArrayList;
import java.util.List;

public class RegistroPacienteActivity extends AppCompatActivity {

    private TextInputEditText etNombrePaciente, etEdadPaciente;
    private Spinner spinnerDoctor;
    private Button btnGuardarTodo;

    // Referencias a las vistas del Fragment de detalles
    private TextInputEditText etHoraInicio, etHoraFin, etObservaciones;

    // Controladores de Base de Datos
    private PacienteController pacienteController;
    private DoctorController doctorController;
    private ConsultaController consultaController;

    // Lista en memoria para asociar la selección del Spinner con el ID del Doctor
    private List<Doctor> listaDoctoresObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paciente);

        // 1. Inicializar Controladores
        pacienteController = new PacienteController(this);
        doctorController = new DoctorController(this);
        consultaController = new ConsultaController(this);

        // 2. Vincular vistas del Layout
        etNombrePaciente = findViewById(R.id.etNombrePaciente);
        etEdadPaciente = findViewById(R.id.etEdadPaciente);
        spinnerDoctor = findViewById(R.id.spinnerDoctor);
        btnGuardarTodo = findViewById(R.id.btnGuardarTodo);

        // 3. Cargar la lista de doctores en el Spinner
        cargarDoctoresEnSpinner();

        // 4. Configurar listener del botón guardar
        btnGuardarTodo.setOnClickListener(v -> validarYGuardarBD());
    }

    private void cargarDoctoresEnSpinner() {
        // Obtenemos los objetos Doctor desde el controlador
        listaDoctoresObj = doctorController.obtenerTodos();

        List<String> nombresDoctores = new ArrayList<>();

        if (listaDoctoresObj != null && !listaDoctoresObj.isEmpty()) {
            for (Doctor doc : listaDoctoresObj) {
                nombresDoctores.add(doc.getNombre());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                nombresDoctores
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctor.setAdapter(adapter);
    }

    private void validarYGuardarBD() {
        // Obtener referencias de los inputs del Fragment
        etHoraInicio = findViewById(R.id.etHoraInicio);
        etHoraFin = findViewById(R.id.etHoraFin);
        etObservaciones = findViewById(R.id.etObservaciones);

        String nombre = etNombrePaciente.getText() != null ? etNombrePaciente.getText().toString().trim() : "";
        String edadStr = etEdadPaciente.getText() != null ? etEdadPaciente.getText().toString().trim() : "";
        String horaInicio = (etHoraInicio != null && etHoraInicio.getText() != null) ? etHoraInicio.getText().toString().trim() : "";
        String horaFin = (etHoraFin != null && etHoraFin.getText() != null) ? etHoraFin.getText().toString().trim() : "";
        String observaciones = (etObservaciones != null && etObservaciones.getText() != null) ? etObservaciones.getText().toString().trim() : "";

        // --- 1. VALIDACIONES ---
        if (nombre.isEmpty()) {
            etNombrePaciente.setError("El nombre es obligatorio");
            etNombrePaciente.requestFocus();
            return;
        } else if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            etNombrePaciente.setError("El nombre solo debe contener letras");
            etNombrePaciente.requestFocus();
            return;
        }

        if (edadStr.isEmpty()) {
            etEdadPaciente.setError("La edad es obligatoria");
            etEdadPaciente.requestFocus();
            return;
        } else if (!edadStr.matches("^\\d+$")) {
            etEdadPaciente.setError("La edad solo permite números");
            etEdadPaciente.requestFocus();
            return;
        }

        if (etHoraInicio != null) {
            if (horaInicio.isEmpty() || !horaInicio.matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")) {
                etHoraInicio.setError("Formato de hora inicio inválido (ej. 10:00)");
                etHoraInicio.requestFocus();
                return;
            }
        }

        if (etHoraFin != null) {
            if (horaFin.isEmpty() || !horaFin.matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")) {
                etHoraFin.setError("Formato de hora fin inválido (ej. 10:30)");
                etHoraFin.requestFocus();
                return;
            }
        }

        if (etObservaciones != null && !observaciones.isEmpty()) {
            if (!observaciones.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
                etObservaciones.setError("Las observaciones solo deben contener letras");
                etObservaciones.requestFocus();
                return;
            }
        }

        // --- 2. INSERCIÓN EN BASE DE DATOS ---
        int edad = Integer.parseInt(edadStr);

// A. Obtener el NOMBRE REAL del doctor seleccionado en el Spinner (ej: "Dr. Gregory House")
        String nombreDoctorSeleccionado = spinnerDoctor.getSelectedItem() != null ?
                spinnerDoctor.getSelectedItem().toString() : "";

// B. Obtener el ID del doctor
        int posicionSeleccionada = spinnerDoctor.getSelectedItemPosition();
        int idDoctorReal = 1;

        if (listaDoctoresObj != null && !listaDoctoresObj.isEmpty() && posicionSeleccionada < listaDoctoresObj.size()) {
            idDoctorReal = listaDoctoresObj.get(posicionSeleccionada).getId();
        } else {
            idDoctorReal = posicionSeleccionada + 1;
        }

// C. Crear Paciente pasando el NOMBRE del doctor que pide la clase
        Paciente nuevoPaciente = new Paciente(0, nombre, edad, nombreDoctorSeleccionado);

// D. Si tu PacienteController requiere el id_doctor para el INSERT, le asignamos el ID numérico
        nuevoPaciente.setIdDoctor(idDoctorReal); // <--- Esto asegura que guarde el id_doctor en SQLite

// E. Insertar en Base de Datos
        long pacienteId = pacienteController.insertar(nuevoPaciente);

        if (pacienteId != -1) {
            // Insertar la Consulta
            Consulta nuevaConsulta = new Consulta((int) pacienteId, horaInicio, horaFin, observaciones);
            consultaController.insertar(nuevaConsulta);

            Toast.makeText(this, "¡Paciente guardado exitosamente!", Toast.LENGTH_SHORT).show();
            finish(); // Cierra y refresca la pantalla anterior
        } else {
            Toast.makeText(this, "Error al guardar el paciente en la BD", Toast.LENGTH_LONG).show();
        }
    }
}