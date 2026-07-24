package com.hospitalapp.activities;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hospitalapp.R;
import com.hospitalapp.controllers.PacienteController;
import com.hospitalapp.models.Paciente;

import java.util.List;

/**
 * View: muestra todos los pacientes junto con el doctor que los atendió.
 * La consulta JOIN ya no vive aquí, sino en PacienteController (Controller).
 */
public class PacientesActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private PacienteController pacienteController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes);

        // Inicializamos las referencias
        tableLayout = findViewById(R.id.tablePacientes);
        pacienteController = new PacienteController(this);

        // Carga inicial
        cargarListaPacientes();
    }

    private void cargarListaPacientes() {
        if (tableLayout == null || pacienteController == null) return;

        // 1. Limpiamos las filas anteriores para no duplicar elementos al regresar a esta pantalla
        tableLayout.removeAllViews();

        // 2. Consultamos la base de datos
        List<Paciente> pacientes = pacienteController.obtenerTodosConDoctor();

        // 3. Agregamos cada fila de paciente
        if (pacientes != null) {
            for (Paciente paciente : pacientes) {
                tableLayout.addView(crearFila(paciente));
            }
        }
    }

    private TableRow crearFila(Paciente paciente) {
        TableRow row = new TableRow(this);

        TextView tvId = new TextView(this);
        tvId.setText(paciente.getId() + " | ");

        TextView tvNombre = new TextView(this);
        tvNombre.setText(paciente.getNombre() + " | ");

        TextView tvEdad = new TextView(this);
        tvEdad.setText(paciente.getEdad() + " años | ");

        TextView tvDoctor = new TextView(this);
        tvDoctor.setText("Atendió: " + paciente.getNombreDoctor());

        row.addView(tvId);
        row.addView(tvNombre);
        row.addView(tvEdad);
        row.addView(tvDoctor);
        return row;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Vuelve a consultar la base de datos y refresca la tabla automáticamente
        cargarListaPacientes();
    }
}