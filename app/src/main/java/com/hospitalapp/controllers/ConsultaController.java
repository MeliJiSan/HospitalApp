package com.hospitalapp.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hospitalapp.database.DatabaseHelper;
import com.hospitalapp.models.Consulta;

/**
 * Controlador (capa Controller de MVC) para la entidad Consulta.
 */
public class ConsultaController {

    private final DatabaseHelper dbHelper;

    public ConsultaController(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public long insertar(Consulta consulta) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("id_paciente", consulta.getIdPaciente());
        valores.put("hora_inicio", consulta.getHoraInicio());
        valores.put("hora_fin", consulta.getHoraFin());
        valores.put("observaciones", consulta.getObservaciones());
        return db.insert("consultas", null, valores);
    }
}
