package com.hospitalapp.controllers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hospitalapp.database.DatabaseHelper;
import com.hospitalapp.models.Doctor;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador (capa Controller de MVC) para la entidad Doctor.
 */
public class DoctorController {

    private final DatabaseHelper dbHelper;

    public DoctorController(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public List<Doctor> obtenerTodos() {
        List<Doctor> doctores = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, nombre, especialidad, cedula FROM doctores", null);

        while (cursor.moveToNext()) {
            Doctor doctor = new Doctor(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            doctores.add(doctor);
        }
        cursor.close();
        return doctores;
    }
}
