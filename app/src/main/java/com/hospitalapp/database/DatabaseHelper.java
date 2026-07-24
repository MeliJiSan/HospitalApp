package com.hospitalapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HospitalLocal.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1. Tabla Usuarios
        db.execSQL("CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario TEXT, " +
                "password TEXT, " +
                "rol TEXT)");

        // 2. Tabla Doctores
        db.execSQL("CREATE TABLE doctores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "especialidad TEXT, " +
                "cedula TEXT)");

        // 3. Tabla Pacientes
        db.execSQL("CREATE TABLE pacientes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "edad INTEGER, " +
                "id_doctor INTEGER)");

        // 4. Tabla Consultas
        db.execSQL("CREATE TABLE consultas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_paciente INTEGER, " +
                "hora_inicio TEXT, " +
                "hora_fin TEXT, " +
                "observaciones TEXT)");


        db.execSQL("INSERT INTO usuarios (usuario, password, rol) VALUES ('admin', '1234', 'Admin'), " +
                "('doc_Portillo', 'pass123', 'Doctor'), " +
                "('enf_lopez', '1234', 'Enfermero'), " +
                "('recepcion', 'rec2026', 'Recepcion')");
        db.execSQL("INSERT INTO doctores (nombre, especialidad, cedula) VALUES " +
                "('Dr.  Luis Manuel Borges López', 'Diagnóstico', 'MED-101'), " +
                "('Dr. Alejandro Hernández Bernal', 'Cirugía', 'MED-102'), " +
                "('Dra. Nora Martagón', 'Pediatría', 'MED-103'), " +
                "('Dra. Fabiola Peralta Galindo', 'Endocrinología', 'MED-104')");
        db.execSQL("INSERT INTO pacientes (nombre, edad, id_doctor) VALUES (" +
                "'Carlos Gómez', 45, 1), ('Ana Martínez', 28, 2), " +
                "('Luis Fernández', 60, 3), ('María Rodríguez', 34, 1)");
        db.execSQL("INSERT INTO consultas (id_paciente, hora_inicio, hora_fin, observaciones) " +
                "VALUES (1, '08:00', '08:45', 'Gripa fuerte, paracetamol'), " +
                "(2, '09:00', '09:30', 'Revisión general')," +
                " (3, '10:15', '11:00', 'Chequeo de presión arterial'), " +
                "(4, '11:30', '12:00', 'Dolor de cabeza agudo')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS consultas");
        db.execSQL("DROP TABLE IF EXISTS pacientes");
        db.execSQL("DROP TABLE IF EXISTS doctores");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }
}