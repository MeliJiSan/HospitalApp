package com.hospitalapp.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.hospitalapp.R;
import com.hospitalapp.database.DatabaseHelper;

public class PacientesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes);

        TableLayout tableLayout = findViewById(R.id.tablePacientes);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Consulta JOIN entre Tabla Pacientes y Tabla Doctores
        String query = "SELECT p.id, p.nombre, p.edad, d.nombre AS doctor " +
                "FROM pacientes p " +
                "LEFT JOIN doctores d ON p.id_doctor = d.id";

        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext()) {
            TableRow row = new TableRow(this);

            TextView tv1 = new TextView(this);
            tv1.setText(c.getString(0) + " | ");
            TextView tv2 = new TextView(this);
            tv2.setText(c.getString(1) + " | ");
            TextView tv3 = new TextView(this);
            tv3.setText(c.getInt(2) + " años | ");
            TextView tv4 = new TextView(this);
            tv4.setText("Atendió: " + c.getString(3));

            row.addView(tv1);
            row.addView(tv2);
            row.addView(tv3);
            row.addView(tv4);

            tableLayout.addView(row);
        }
        c.close();
    }
        /*
        // Encabezado de la tabla (*raro)
        TableRow headerRow = new TableRow(this);
        headerRow.addView(crearCelda("ID", true));
        headerRow.addView(crearCelda("Paciente", true));
        headerRow.addView(crearCelda("Edad", true));
        headerRow.addView(crearCelda("Doctor", true));
        tableLayout.addView(headerRow);

        // Consulta SQL con JOIN entre Pacientes y Doctores
        String query = "SELECT p.id, p.nombre, p.edad, d.nombre AS doctor " +
                "FROM pacientes p " +
                "LEFT JOIN doctores d ON p.id_doctor = d.id";

        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext()) {
            TableRow row = new TableRow(this);
            row.addView(crearCelda(String.valueOf(c.getInt(0)), false));
            row.addView(crearCelda(c.getString(1), false));
            row.addView(crearCelda(c.getInt(2) + " años", false));
            row.addView(crearCelda(c.getString(3), false));
            tableLayout.addView(row);
        }
        c.close();

    }

    private TextView crearCelda(String texto, boolean esEncabezado) {
        TextView tv = new TextView(this);
        tv.setText(texto);
        tv.setPadding(8, 8, 8, 8);
        if (esEncabezado) {
            tv.setTypeface(null, Typeface.BOLD);
        }
        return tv;
    }*/
}