package com.hospitalapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hospitalapp.R;
import com.hospitalapp.controllers.UsuarioController;

/**
 * View: solo se encarga de leer la UI y mostrar resultados.
 * Toda la validación real vive en UsuarioController (Controller).
 */
public class LoginActivity extends AppCompatActivity {

    private EditText etUser, etPass;
    private UsuarioController usuarioController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioController = new UsuarioController(this);
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> intentarLogin());
    }

    private void intentarLogin() {
        String usuario = etUser.getText().toString().trim();
        String password = etPass.getText().toString().trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Ingresa usuario y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        if (usuarioController.validarUsuario(usuario, password)) {
            Toast.makeText(this, "¡Bienvenido " + usuario + "!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, VisorTablasActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Datos erróneos. Prueba: admin / 1234", Toast.LENGTH_LONG).show();
        }
    }
}
