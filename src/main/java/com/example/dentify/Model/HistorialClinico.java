package com.example.dentify.Model;

import java.time.LocalDate;

public class HistorialClinico {

    private int idHistorialClinico;
    private Paciente paciente;
    private LocalDate fechaCreacion;
    private String grupoSanguineo;

    public HistorialClinico(int idHistorialClinico, Paciente paciente, LocalDate fechaCreacion, String grupoSanguineo) {
        this.idHistorialClinico = idHistorialClinico;
        this.paciente = paciente;
        this.fechaCreacion = fechaCreacion;
        this.grupoSanguineo = grupoSanguineo;
    }

    public HistorialClinico() {
    }

    public int getIdHistorialClinico() {
        return idHistorialClinico;
    }

    public void setIdHistorialClinico(int idHistorialClinico) {
        this.idHistorialClinico = idHistorialClinico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    // METODO PUENTE (PARA EVITAR ERRORES CON EL ID)
    public int getIdPaciente() {

        return (paciente != null) ? paciente.getId_paciente() : 0;

    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    @Override
    public String toString() {
        return "Historial #" + idHistorialClinico + " - Paciente: " +
               (paciente != null ? paciente.getNombre() + " " + paciente.getApellido() : "Sin asignar");
    }
}