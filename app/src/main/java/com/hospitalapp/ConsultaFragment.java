package com.hospitalapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.hospitalapp.R;

public class ConsultaFragment extends Fragment {

    private EditText etHoraInicio, etHoraFin, etObservaciones;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_consulta, container, false);
        etHoraInicio = v.findViewById(R.id.etHoraInicio);
        etHoraFin = v.findViewById(R.id.etHoraFin);
        etObservaciones = v.findViewById(R.id.etObservaciones);
        return v;
    }

    public String getHoraInicio() { return etHoraInicio.getText().toString().trim(); }
    public String getHoraFin() { return etHoraFin.getText().toString().trim(); }
    public String getObservaciones() { return etObservaciones.getText().toString().trim(); }
}