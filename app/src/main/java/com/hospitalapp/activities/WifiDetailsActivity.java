package com.hospitalapp.activities;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.hospitalapp.R;

public class WifiDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_details);

        TextView tvDetails = findViewById(R.id.tvWifiDetails);

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager != null ? wifiManager.getConnectionInfo() : null;

        if (info != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("=== MÉTODOS Y DATOS DE WIFIINFO ===\n\n");
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
}