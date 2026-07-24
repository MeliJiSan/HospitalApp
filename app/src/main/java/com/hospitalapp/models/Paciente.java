package com.hospitalapp.models;

/**
 * Modelo (capa Model de MVC) que representa un registro de la tabla "pacientes".
 * nombreDoctor solo se llena cuando el controlador hace un JOIN con doctores
 * (ver PacienteController.obtenerTodosConDoctor()).
 */
public class Paciente {

    private int id;
    private String nombre;
    private int edad;
    private int idDoctor;
    private String nombreDoctor;

    public Paciente() {
    }

    /** Constructor usado al crear un paciente nuevo (antes de insertarlo). */
    public Paciente(String nombre, int edad, int idDoctor) {
        this.nombre = nombre;
        this.edad = edad;
        this.idDoctor = idDoctor;
    }

    /** Constructor usado al leer pacientes ya unidos (JOIN) con su doctor. */
    public Paciente(int id, String nombre, int edad, String nombreDoctor) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.nombreDoctor = nombreDoctor;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getNombreDoctor() {
        return nombreDoctor;
    }

    public void setNombreDoctor(String nombreDoctor) {
        this.nombreDoctor = nombreDoctor;
    }
}
