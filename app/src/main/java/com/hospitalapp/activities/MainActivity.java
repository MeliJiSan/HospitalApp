package com.hospitalapp.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.hospitalapp.R;
import java.util.Calendar;

// 1. Excepción personalizada para nombres con números
class NombreInvalidoException extends Exception {
    public NombreInvalidoException(String mensaje) {
        super(mensaje);
    }
}

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etNombre, etFechaNacimiento;
    private TextView tvEdadCalculada;
    private Spinner spDoctor;
    private Button btnGuardar;
    private int edadCalculada = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar Vistas
        etNombre = findViewById(R.id.etNombre);
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento);
        tvEdadCalculada = findViewById(R.id.tvEdadCalculada);
        spDoctor = findViewById(R.id.spDoctor);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Configurar selector de fecha y cálculo automático de edad
        setupDatePicker();

        // Evento Guardar
        btnGuardar.setOnClickListener(v -> intentarGuardar());
    }

    private void setupDatePicker() {
        etFechaNacimiento.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int anio = cal.get(Calendar.YEAR);
            int mes = cal.get(Calendar.MONTH);
            int dia = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                String fechaSel = dayOfMonth + "/" + (month + 1) + "/" + year;
                etFechaNacimiento.setText(fechaSel);

                // Calcular edad exacta
                Calendar hoy = Calendar.getInstance();
                edadCalculada = hoy.get(Calendar.YEAR) - year;
                if (hoy.get(Calendar.DAY_OF_YEAR) < cal.get(Calendar.DAY_OF_YEAR)) {
                    edadCalculada--;
                }

                tvEdadCalculada.setText("Edad: " + edadCalculada + " años");
            }, anio, mes, dia);

            datePicker.show();
        });
    }

    private void validarNombre(String nombre) throws NombreInvalidoException {
        if (nombre.isEmpty()) {
            throw new NombreInvalidoException("El campo nombre no puede estar vacío.");
        }
        if (nombre.matches(".*\\d.*")) { // Detecta cualquier dígito numérico
            throw new NombreInvalidoException("El nombre no puede contener números.");
        }
    }

    private void intentarGuardar() {
        try {
            String nombre = etNombre.getText().toString().trim();

            // Lanza la excepción si el nombre es inválido
            validarNombre(nombre);

            if (edadCalculada < 0) {
                Toast.makeText(this, "Por favor, selecciona la fecha de nacimiento", Toast.LENGTH_SHORT).show();
                return;
            }

            // Si todo está bien:
            Toast.makeText(this, "¡Paciente " + nombre + " registrado con " + edadCalculada + " años!", Toast.LENGTH_LONG).show();

        } catch (NombreInvalidoException e) {
            etNombre.setError(e.getMessage());
            Toast.makeText(this, "Excepción atrapada: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}