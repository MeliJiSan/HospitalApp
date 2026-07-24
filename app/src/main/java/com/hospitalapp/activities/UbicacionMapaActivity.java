package com.hospitalapp.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hospitalapp.R;

/**
 * View: muestra la ubicación EN TIEMPO REAL sobre el mapa.
 * CORREGIDO: la versión anterior llamaba a getLastLocation(), que solo
 * devuelve una posición cacheada UNA sola vez. Ahora se usa
 * requestLocationUpdates() con un LocationCallback para que las coordenadas
 * (x = longitud, y = latitud) se sigan actualizando mientras la pantalla
 * está abierta, tal como pide la diapositiva del proyecto.
 */
public class UbicacionMapaActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_CODE_UBICACION = 100;
    private static final long INTERVALO_ACTUALIZACION_MS = 5000;

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private TextView tvCoords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_mapa);

        tvCoords = findViewById(R.id.tvCoords);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    actualizarMapa(location);
                }
            }
        };

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        iniciarActualizacionesDeUbicacion();
    }

    private void iniciarActualizacionesDeUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_UBICACION);
            return;
        }

        mMap.setMyLocationEnabled(true);

        LocationRequest locationRequest = new LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, INTERVALO_ACTUALIZACION_MS)
                .setMinUpdateIntervalMillis(INTERVALO_ACTUALIZACION_MS)
                .build();

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void actualizarMapa(Location location) {
        double lat = location.getLatitude();  // Y
        double lng = location.getLongitude(); // X

        tvCoords.setText(String.format(
                "Coordenadas en tiempo real -> Lat (Y): %.5f | Lng (X): %.5f", lat, lng));

        LatLng posicionActual = new LatLng(lat, lng);
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(posicionActual).title("Ubicación Paciente"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicionActual, 16f));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_UBICACION
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            iniciarActualizacionesDeUbicacion();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Importante: dejar de escuchar ubicación al salir para no drenar la batería
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }
}
