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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes);

        TableLayout tableLayout = findViewById(R.id.tablePacientes);
        PacienteController pacienteController = new PacienteController(this);
        List<Paciente> pacientes = pacienteController.obtenerTodosConDoctor();

        for (Paciente paciente : pacientes) {
            tableLayout.addView(crearFila(paciente));
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
}
