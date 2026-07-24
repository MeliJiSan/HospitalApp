package com.hospitalapp.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.hospitalapp.R;
import com.hospitalapp.models.EdadInvalidaException;

import java.util.Calendar;

public class RegistroPacienteActivity extends AppCompatActivity {

    private TextInputEditText etFechaNacimiento;
    private int edadCalculada = -1; // Guardará la edad ya validada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paciente);

        etFechaNacimiento = findViewById(R.id.etFechaNacimiento);

        // Al hacer clic en el campo, abrimos el selector de fecha
        etFechaNacimiento.setOnClickListener(v -> mostrarDatePicker());
        */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paciente);

        // 1. Inicializar vistas
        spinnerDoctor = findViewById(R.id.spinnerDoctor); // Revisa el ID de tu XML
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento);

        // 2. Inicializar controlador
        doctorController = new DoctorController(this);

        // 3. ¡IMPORTANTE! Cargar los doctores
        cargarDoctoresEnSpinner();
    }

    private void mostrarDatePicker() {
        final Calendar c = Calendar.getInstance();
        int anio = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    String fechaSeleccionada = String.format("%02d/%02d/%d", dayOfMonth, (monthOfYear + 1), year);

                    try {
                        // 1. Calcular la edad a partir de los datos seleccionados
                        int edad = calcularEdad(year, monthOfYear, dayOfMonth);

                        // 2. Validar con nuestra Excepción
                        validarEdad(edad);

                        // Si la validación pasa:
                        edadCalculada = edad;
                        etFechaNacimiento.setText(fechaSeleccionada + " (" + edadCalculada + " años)");
                        etFechaNacimiento.setError(null);

                    } catch (EdadInvalidaException e) {
                        // Manejo de la excepción personalizada
                        edadCalculada = -1;
                        etFechaNacimiento.setText("");
                        etFechaNacimiento.setError(e.getMessage());
                        Toast.makeText(RegistroPacienteActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                anio, mes, dia
        );

        datePickerDialog.show();
    }

    /**
     * Calcula la edad exacta comparando la fecha elegida con la fecha actual
     */
    private int calcularEdad(int anioNac, int mesNac, int diaNac) {
        Calendar hoy = Calendar.getInstance();

        int anioActual = hoy.get(Calendar.YEAR);
        int mesActual = hoy.get(Calendar.MONTH);
        int diaActual = hoy.get(Calendar.DAY_OF_MONTH);

        int edad = anioActual - anioNac;

        // Ajustar la edad si aún no ha cumplido años en el año actual
        if (mesActual < mesNac || (mesActual == mesNac && diaActual < diaNac)) {
            edad--;
        }

        return edad;
    }

    /**
     * Lanza una excepción si la edad sobrepasa el límite permitido o es inconsistente
     */
    private void validarEdad(int edad) throws EdadInvalidaException {
        if (edad < 0) {
            throw new EdadInvalidaException("La fecha de nacimiento no puede ser futura.");
        }
        if (edad > 120) { // Límite para restringir edades muy longevas
            throw new EdadInvalidaException("Edad no permitida: Supera el límite máximo de 120 años.");
        }
    }

    // En tu método de guardar en BD simplemente usas 'edadCalculada'
    private void validarYGuardarBD() {
        if (edadCalculada == -1) {
            etFechaNacimiento.setError("Debe seleccionar una fecha de nacimiento válida");
            return;
        }

        // ... Procedes a instanciar Paciente usando 'edadCalculada'
    }

    private void cargarDoctoresEnSpinner() {
        // Obtenemos los objetos Doctor desde la BD
        listaDoctoresObj = doctorController.obtenerTodos();

        List<String> nombresDoctores = new ArrayList<>();

        if (listaDoctoresObj != null && !listaDoctoresObj.isEmpty()) {
            for (Doctor doc : listaDoctoresObj) {
                nombresDoctores.add(doc.getNombre());
            }
        } else {
            // Fallback por si la tabla 'doctores' aún no tiene registros precargados
            nombresDoctores.add("Dr. Gregory House");
            nombresDoctores.add("Dra. Meredith Grey");
            nombresDoctores.add("Dr. Shaun Murphy");
        }

        // Usamos layout simple de Android con un estilo visible
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                nombresDoctores
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerDoctor.setAdapter(adapter);
    }
}