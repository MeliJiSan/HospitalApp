package com.hospitalapp.models;

/**
 * Modelo (capa Model de MVC) que representa un registro de la tabla "doctores".
 */
public class Doctor {

    private int id;
    private String nombre;
    private String especialidad;
    private String cedula;

    public Doctor() {
    }

    public Doctor(int id, String nombre, String especialidad, String cedula) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.cedula = cedula;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * Se usa directamente por el ArrayAdapter del Spinner de doctores
     * en RegistroPacienteActivity, así el usuario ve "Nombre (Especialidad)".
     */
    @Override
    public String toString() {
        return nombre + " (" + especialidad + ")";
    }
}
