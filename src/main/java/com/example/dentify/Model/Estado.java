package com.example.dentify.Model;

/**
 * Define los estados posibles en los que puede encontrarse una cita médica.
 * Se utiliza para controlar el flujo de trabajo de la clínica, la aplicación de
 * reglas de negocio y el filtrado reactivo de datos en las tablas de la interfaz.
 */
public enum Estado  {

    /**
     * La cita ha sido programada pero el paciente aún no ha asistido ni ha sido atendido.
     */
    PENDIENTE,

    /**
     * La cita se encuentra en proceso de atención activa por parte del doctor en el gabinete.
     */
    ACTIVO,

    /**
     * La cita ha sido anulada por el paciente o la clínica, liberando el hueco en el horario.
     */
    CANCELADO,

}
