package com.hospitalapp.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hospitalapp.database.DatabaseHelper;
import com.hospitalapp.models.Paciente;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador (capa Controller de MVC) para la entidad Paciente.
 */
public class PacienteController {

    private final DatabaseHelper dbHelper;

    public PacienteController(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    /** Inserta un paciente y devuelve el id generado (-1 si falla). */
    public long insertar(Paciente paciente) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombre", paciente.getNombre());
        valores.put("edad", paciente.getEdad());

        // Obtenemos el ID del doctor desde el objeto paciente
        valores.put("id_doctor", paciente.getIdDoctor());

        return db.insert("pacientes", null, valores);
    }

    /** Devuelve todos los pacientes junto con el nombre del doctor que los atendió (JOIN). */
    public List<Paciente> obtenerTodosConDoctor() {
        List<Paciente> pacientes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT p.id, p.nombre, p.edad, d.nombre AS doctor " +
                "FROM pacientes p " +
                "LEFT JOIN doctores d ON p.id_doctor = d.id";

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Paciente paciente = new Paciente(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3)
            );
            pacientes.add(paciente);
        }
        cursor.close();
        return pacientes;
    }
}
