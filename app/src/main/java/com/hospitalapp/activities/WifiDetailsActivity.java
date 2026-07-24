package com.hospitalapp.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.hospitalapp.R;

/**
 * View: muestra los detalles de la red WiFi conectada.
 * CORREGIDO: desde Android 8+, WifiInfo.getSSID() devuelve "<unknown ssid>"
 * si no se tiene concedido el permiso de ubicación en tiempo de ejecución.
 * Ahora se pide el permiso antes de leer los datos.
 */
public class WifiDetailsActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_UBICACION = 200;
    private TextView tvDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_details);

        tvDetails = findViewById(R.id.tvWifiDetails);
        mostrarDatosWifi();
    }

    private void mostrarDatosWifi() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_UBICACION);
            return;
        }

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager != null ? wifiManager.getConnectionInfo() : null;

        if (info != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("=== DATOS DE LA RED WIFI ===\n\n");
            sb.append("• SSID (Nombre de red): ").append(info.getSSID()).append("\n");
            sb.append("• BSSID (MAC del AP): ").append(info.getBSSID()).append("\n");
            sb.append("• RSSI (Potencia de señal): ").append(info.getRssi()).append(" dBm\n");
            sb.append("• Velocidad de enlace: ").append(info.getLinkSpeed()).append(" Mbps\n");
            sb.append("• Frecuencia: ").append(info.getFrequency()).append(" MHz\n");
            sb.append("• Dirección IP: ").append(info.getIpAddress()).append("\n");
            sb.append("• Dirección MAC dispositivo: ").append(info.getMacAddress()).append("\n");
            sb.append("• Network ID: ").append(info.getNetworkId()).append("\n");
            sb.append("• Estado Supplicant: ").append(info.getSupplicantState()).append("\n");

            tvDetails.setText(sb.toString());
        } else {
            tvDetails.setText("No se pudo obtener información del WiFi.");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_UBICACION) {
            mostrarDatosWifi();
        }
    }
}
