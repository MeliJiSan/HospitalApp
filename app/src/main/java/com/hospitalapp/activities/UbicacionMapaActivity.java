package com.hospitalapp.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.hospitalapp.R;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class UbicacionMapaActivity extends AppCompatActivity {

    private MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Cargar configuración de OSMDroid
        Configuration.getInstance().load(this, getSharedPreferences("osmdroid", MODE_PRIVATE));

        setContentView(R.layout.activity_ubicacion_mapa);

        map = findViewById(R.id.mapView);
        map.setTileSource(TileSourceFactory.MAPNIK); // Estilo del mapa
        map.setMultiTouchControls(true); // Permitir hacer zoom con los dedos

        // Coordenadas del Hospital
        GeoPoint puntoHospital = new GeoPoint(19.043700, -98.198000);

        // Centrar mapa y dar zoom
        map.getController().setZoom(16.0);
        map.getController().setCenter(puntoHospital);

        // Crear Pin/Marcador
        Marker startMarker = new Marker(map);
        startMarker.setPosition(puntoHospital);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setTitle("Hospital General Central");
        map.getOverlays().add(startMarker);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (map != null) map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (map != null) map.onPause();
    }
}