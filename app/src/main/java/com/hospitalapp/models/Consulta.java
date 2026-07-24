package com.hospitalapp.models;

/**
 * Modelo (capa Model de MVC) que representa un registro de la tabla "consultas".
 */
public class Consulta {

    private int id;
    private int idPaciente;
    private String horaInicio;
    private String horaFin;
    private String observaciones;

    public Consulta() {
    }

    public Consulta(int idPaciente, String horaInicio, String horaFin, String observaciones) {
        this.idPaciente = idPaciente;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.observaciones = observaciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
