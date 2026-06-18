package com.example.dentify.Model;

/**
 * Representa un servicio o procedimiento clínico que ofrece la clínica dental.
 * Almacena el nombre comercial de la intervención y una breve descripción médica,
 * utilizándose como catálogo para asociar procedimientos concretos a las citas y
 * desgloses de los historiales clínicos.
 */
public class Tratamiento {

    private int idTratamiento;
    private String nombre;
    private String descripcion;

    /**
     * Constructor completo para instanciar un Tratamiento con todas sus propiedades.
     * * @param idTratamiento Identificador único del tratamiento en la base de datos.
     * @param nombre Nombre identificativo del procedimiento (ej. "Endodoncia", "Limpieza").
     * @param descripcion Detalle o especificación técnica de lo que incluye el procedimiento.
     */
    public Tratamiento(int idTratamiento, String nombre, String descripcion) {
        this.idTratamiento = idTratamiento;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}