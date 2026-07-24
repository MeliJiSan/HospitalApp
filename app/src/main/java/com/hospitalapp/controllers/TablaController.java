package com.hospitalapp.controllers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hospitalapp.database.DatabaseHelper;

/**
 * Controlador genérico usado únicamente por VisorTablasActivity: permite
 * consultar el contenido crudo de cualquiera de las 4 tablas de la BD
 * (usuarios, doctores, pacientes, consultas) sin que la Activity toque SQL.
 */
public class TablaController {

    private final DatabaseHelper dbHelper;

    public TablaController(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public String obtenerContenidoComoTexto(String nombreTabla) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + nombreTabla, null);

        StringBuilder builder = new StringBuilder();
        builder.append("TABLA: ").append(nombreTabla.toUpperCase()).append("\n");
        builder.append("---------------------------------------\n");

        String[] columnas = cursor.getColumnNames();
        while (cursor.moveToNext()) {
            for (String columna : columnas) {
                int indice = cursor.getColumnIndex(columna);
                builder.append(columna).append(": ").append(cursor.getString(indice)).append("\n");
            }
            builder.append("---------------------------------------\n");
        }
        cursor.close();
        return builder.toString();
    }
}
