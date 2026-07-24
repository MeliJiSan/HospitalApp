package com.hospitalapp.controllers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hospitalapp.database.DatabaseHelper;

/**
 * Controlador (capa Controller de MVC) para la entidad Usuario.
 * Toda la lógica de validación de login vive aquí; LoginActivity (View)
 * solo llama a validarUsuario() y reacciona al resultado.
 */
public class UsuarioController {

    private final DatabaseHelper dbHelper;

    public UsuarioController(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public boolean validarUsuario(String usuario, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id FROM usuarios WHERE usuario = ? AND password = ?",
                new String[]{usuario, password}
        );
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }
}
