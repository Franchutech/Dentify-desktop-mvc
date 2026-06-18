package com.example.dentify.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa una cita médica dentro del sistema de la clínica dental.
 * Contiene la información temporal de la reserva, el motivo de la consulta,
 * el estado actual y las relaciones con el Paciente y el Doctor asignados,
 * además de incluir reglas de negocio para la validación de horarios comerciales.
 */
public class Cita {

    private int idCita;
    private LocalDate fecha;
    private LocalDateTime hora;
    private String motivo;
    private Estado Estado;

    private Paciente paciente;
    private Doctor doctor;

    private int idTratamiento;
    private String observaciones;

    /**
     * Constructor completo para instanciar una Cita con todos sus atributos.
     * * @param idCita Identificador único de la cita en la base de datos.
     * @param fecha Fecha programada para la cita.
     * @param hora Fecha y hora exacta combinada de la reserva.
     * @param motivo Descripción o causa de la consulta dental.
     * @param Estado Estado actual de la cita (Enum).
     * @param paciente Objeto Paciente asociado a la cita.
     * @param doctor Objeto Doctor encargado de atender la cita.
     * @param idTratamiento Identificador del tratamiento clínico a aplicar.
     * @param observaciones Notas aclaratorias o comentarios adicionales médicos.
     */
    public Cita(int idCita, LocalDate fecha, LocalDateTime hora, String motivo, Estado Estado, Paciente paciente, Doctor doctor, int idTratamiento, String observaciones) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.Estado = Estado;
        this.paciente = paciente;
        this.doctor = doctor;
        this.idTratamiento = idTratamiento;
        this.observaciones = observaciones;
    }

    /**
     * Constructor vacío por defecto para inicialización tardía o mapeo de datos.
     */
    public Cita() {
    }

    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalDateTime getHora() { return hora; }
    public void setHora(LocalDateTime hora) { this.hora = hora; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public Estado getEstado() { return Estado; }
    public void setEstado(Estado Estado) { this.Estado = Estado; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    // MÉTODOS PUENTE CORREGIDOS PARA QUE CALCE CON LO DE ANDRE

    /**
     * Método puente para recuperar de forma segura el ID del paciente asociado.
     * * @return Identificador numérico del paciente, o 0 si no hay paciente asignado.
     */
    public int getIdPaciente() {
        return (paciente != null) ? paciente.getId_paciente() : 0; // Cambiado a getId_paciente()
    }

    /**
     * Método puente para recuperar de forma segura el ID del doctor asociado.
     * * @return Identificador numérico del doctor, o 0 si no hay doctor asignado.
     */
    public int getIdDoctor() {
        return (doctor != null) ? doctor.getIdDoctor() : 0;
    }

    public int getIdTratamiento() { return idTratamiento; }
    public void setIdTratamiento(int idTratamiento) { this.idTratamiento = idTratamiento; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    //VALIDACIONES

    /**
     * Ejecuta las reglas de negocio de la clínica para validar la viabilidad de la cita.
     * Comprueba la existencia de campos obligatorios, restringe que la fecha no sea en el pasado,
     * y controla el horario comercial (9:00 a 20:00) impidiendo agendar fines de semana.
     * * @return true si cumple con todas las directrices comerciales de la clínica; false en caso contrario.
     */
    public boolean esValida() {
        // Verificaciones básicas de nulos
        if (this.fecha == null || this.hora == null) return false;
        if (this.motivo == null || this.motivo.trim().isEmpty()) return false;
        if (this.paciente == null || this.doctor == null) return false;
        if (this.getIdPaciente() <= 0 || this.getIdDoctor() <= 0) return false;

        // Control del tiempo: NO PERMITIR CITAS EN EL PASADO (Fecha y Hora juntas)
        if (this.hora.isBefore(LocalDateTime.now())) return false;

        // Control de Horario Comercial (Ejemplo: 9:00 a 20:00)
        int horaInt = this.hora.getHour();
        if (horaInt < 9 || horaInt >= 20) return false;

        // Control de fines de semana (Evitar Sábados y Domingos si la clínica cierra)
        java.time.DayOfWeek dia = this.fecha.getDayOfWeek();
        if (dia == java.time.DayOfWeek.SATURDAY || dia == java.time.DayOfWeek.SUNDAY) return false;

        return true;
    }

    // TOSTRING
    /**
     * Devuelve una representación textual compacta de la información de la cita.
     * * @return Cadena formateada con el ID de la cita y el nombre del paciente.
     */
    @Override
    public String toString() {
        return "Cita #" + idCita + " | Paciente: " + (paciente != null ? paciente.getNombre() : "N/A");
    }
}