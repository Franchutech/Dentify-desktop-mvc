package com.example.dentify.Model;

import java.time.LocalDate;

/**
 * Representa a un paciente de la clínica dental.
 * Almacena la información de filiación, los datos de contacto y la fecha de
 * nacimiento del usuario, sirviendo como entidad base para la gestión de citas
 * e historiales clínicos.
 */
public class Paciente {

    private int id_paciente;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo_electronico;
    private LocalDate fecha_nacimiento;

    /**
     * Constructor completo para instanciar un Paciente con todas sus propiedades.
     * * @param id_paciente Identificador único del paciente en la base de datos.
     * @param nombre Nombre de pila del paciente.
     * @param apellido Apellidos del paciente.
     * @param telefono Número de teléfono de contacto.
     * @param correo_electronico Dirección de correo electrónico.
     * @param fecha_nacimiento Fecha de nacimiento del paciente.
     */
    public Paciente(int id_paciente, String nombre, String apellido, String telefono, String correo_electronico, LocalDate fecha_nacimiento) {
        this.id_paciente = id_paciente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo_electronico = correo_electronico;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    /**
     * Constructor vacío por defecto para inicialización tardía o mapeo de datos.
     */
    public Paciente(){
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    /**
     * Devuelve una representación textual compacta del paciente.
     * * @return Cadena formateada con el nombre completo y el teléfono del paciente.
     */
    @Override
    public String toString() {
        return nombre + " " + apellido + " - " + telefono;
    }

}