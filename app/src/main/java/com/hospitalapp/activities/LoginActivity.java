package com.hospitalapp.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.hospitalapp.R;
import com.hospitalapp.database.DatabaseHelper;

//import com.hospitalapp.VisorTablasActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUser, etPass;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String user = etUser.getText().toString().trim();
            String pass = etPass.getText().toString().trim();

            if (validarUsuario(user, pass)) {
                Toast.makeText(this, "¡Bienvenido " + user + "!", Toast.LENGTH_SHORT).show();
                // Al autenticar, pasa al menú visor de tablas
                startActivity(new Intent(this, VisorTablasActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Datos erróneos. Prueba: admin / 1234", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validarUsuario(String user, String pass) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM usuarios WHERE usuario=? AND password=?", new String[]{user, pass});
        boolean existe = c.getCount() > 0;
        c.close();
        return existe;
    }
}