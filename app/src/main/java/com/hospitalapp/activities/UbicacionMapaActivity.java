package com.hospitalapp.activities;

import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hospitalapp.R;

public class UbicacionMapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    // 1. DECLARACIÓN DE LA VARIABLE (Esto resuelve el error)
    private TextView tvCoords;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_mapa);

        // 2. ENLAZAR CON EL XML
        tvCoords = findViewById(R.id.tvCoords);

        // Inicializar fragmento del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Ubicación por defecto
        LatLng ubicacionInicial = new LatLng(17.0654, -96.7236);
        mMap.addMarker(new MarkerOptions().position(ubicacionInicial).title("Hospital App"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionInicial, 15f));
    }

    // 3. MÉTODO ACTUALIZAR MAPA Y COORDENADAS
    private void actualizarMapa(Location location) {
        if (location == null) return;

        double lat = location.getLatitude();
        double lng = location.getLongitude();

        // Si tvCoords no es nulo, actualiza el texto en pantalla
        if (tvCoords != null) {
            tvCoords.setText(String.format("Coordenadas en tiempo real -> Lat: %.4f | Lng: %.4f", lat, lng));
        }

        LatLng posicionActual = new LatLng(lat, lng);
        if (mMap != null) {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(posicionActual).title("Tu Ubicación"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicionActual, 16f));
        }
    }
}