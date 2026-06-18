package com.example.dentify.Model;

import java.time.LocalDate;

/**
 * Representa a un odontólogo o facultativo médico de la clínica dental.
 * Almacena sus datos de filiación, su número de colegiado oficial y la
 * especialidad médica (Enum) en la que desempeña sus labores clínicas.
 */
public class Doctor {

    private int idDoctor;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String numColegiado;

    private Especialidad especialidad;

    /**
     * Constructor completo para instanciar un Doctor con todas sus propiedades.
     * * @param idDoctor Identificador único del doctor en la base de datos.
     * @param nombre Nombre y apellidos del profesional médico.
     * @param fechaNacimiento Fecha de nacimiento del facultativo.
     * @param direccion Domicilio o dirección de contacto del doctor.
     * @param numColegiado Número de colegiado oficial para el ejercicio de la medicina.
     * @param especialidad Rama u especialidad odontológica del profesional (Enum).
     */
    public Doctor(int idDoctor, String nombre, LocalDate fechaNacimiento, String direccion, String numColegiado, Especialidad especialidad) {
        this.idDoctor = idDoctor;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.numColegiado = numColegiado;
        this.especialidad = especialidad;
    }

    /**
     * Constructor vacío por defecto para inicialización tardía o mapeo de datos.
     */
    public Doctor() {
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumColegiado() {
        return numColegiado;
    }

    public void setNumColegiado(String numColegiado) {
        this.numColegiado = numColegiado;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * Devuelve una representación textual compacta del doctor.
     * * @return Cadena formateada con el nombre del médico y su especialidad clínica.
     */
    @Override
    public String toString() {
        return nombre + " - " + especialidad;
    }

}