package com.example.dentify.Model;

/**
 * Representa el desglose específico de los procedimientos aplicados en una cita.
 * Vincula una cita médica concreta con un tratamiento específico del catálogo,
 * permitiendo almacenar las anotaciones u observaciones clínicas correspondientes.
 */
public class DetalleCita {

    private int idDetalleCita;
    private int idCita;
    private int idTratamiento;
    private String observaciones;

    /**
     * Constructor completo para instanciar el detalle de una cita con todos sus campos.
     * * @param idDetalleCita Identificador único del detalle en la base de datos.
     * @param idCita Identificador de la cita médica asociada.
     * @param idTratamiento Identificador del tratamiento clínico aplicado.
     * @param observaciones Notas informativas o comentarios del odontólogo sobre el procedimiento.
     */
    public DetalleCita(int idDetalleCita, int idCita, int idTratamiento, String observaciones) {
        this.idDetalleCita = idDetalleCita;
        this.idCita = idCita;
        this.idTratamiento = idTratamiento;
        this.observaciones = observaciones;
    }

    /**
     * Constructor vacío por defecto para inicialización diferida o frameworks de persistencia.
     */
    public DetalleCita() {
    }

    public int getIdDetalleCita() {
        return idDetalleCita;
    }

    public void setIdDetalleCita(int idDetalleCita) {
        this.idDetalleCita = idDetalleCita;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Devuelve una representación en cadena de texto del detalle de la cita.
     * * @return Cadena formateada con las propiedades clave del objeto.
     */
    @Override
    public String toString() {
        return "DetalleCita{" +
                "idDetalle=" + idDetalleCita +
                ", idTratamiento=" + idTratamiento +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }

}