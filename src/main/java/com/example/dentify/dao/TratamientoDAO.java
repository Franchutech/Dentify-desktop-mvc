package com.example.dentify.dao;

import com.example.dentify.Model.Tratamiento;
import com.example.dentify.Configuration.SQLDataBaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Acceso a Datos (DAO) para la entidad Tratamiento.
 * Centraliza y gestiona todas las operaciones CRUD de persistencia relacional
 * sobre la tabla 'TRATAMIENTO' de la base de datos MySQL, abstrayendo el uso de la API JDBC.
 */
public class TratamientoDAO {

    /**
     * Recupera la totalidad de los registros de tratamientos clínicos almacenados en el sistema.
     * Procesa secuencialmente las filas devueltas por MySQL para instanciar la lista observable.
     *
     * @return Una colección List estructurada con todas las instancias de tipo Tratamiento halladas.
     */
    public static List<Tratamiento> getAllTratamientos() {
        List<Tratamiento> lista = new ArrayList<>();
        String sql = "SELECT id_tratamiento, nombre, descripcion FROM TRATAMIENTO";

        try (Connection conn = SQLDataBaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Tratamiento t = new Tratamiento(
                        rs.getInt("id_tratamiento"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
                lista.add(t);
            }
        } catch (SQLException e) {
            System.err.println("Error en TratamientoDAO.getAllTratamientos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Inserta un nuevo registro de tratamiento clínico en la base de datos MySQL.
     *
     * @param t Objeto Tratamiento que contiene los datos del procedimiento que se va a persistir.
     * @return true si la inserción se ejecutó correctamente; false si ocurrió una excepción SQLException.
     */
    public static boolean createTratamiento(Tratamiento t) {
        String sql = "INSERT INTO TRATAMIENTO (nombre, descripcion) VALUES (?, ?)";
        try (Connection conn = SQLDataBaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, t.getNombre());
            pstmt.setString(2, t.getDescripcion());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en TratamientoDAO.createTratamiento: " + e.getMessage());
            return false;
        }
    }

    /**
     * Modifica los valores de las columnas de un tratamiento preexistente mediante una sentencia UPDATE.
     * Localiza la fila unívoca en base al ID clave primaria inyectado en el objeto.
     *
     * @param t Instancia de Tratamiento que contiene las propiedades modificadas a persistir.
     * @return true si al menos una fila fue alterada en el servidor de MySQL; false si ocurrió un error.
     */
    public static boolean updateTratamiento(Tratamiento t) {
        String sql = "UPDATE TRATAMIENTO SET nombre = ?, descripcion = ? WHERE id_tratamiento = ?";
        try (Connection conn = SQLDataBaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, t.getNombre());
            pstmt.setString(2, t.getDescripcion());
            pstmt.setInt(3, t.getIdTratamiento());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en TratamientoDAO.updateTratamiento: " + e.getMessage());
            return false;
        }
    }

    /**
     * Borra de forma física una fila de la tabla TRATAMIENTO basándose en su clave primaria.
     *
     * @param id Identificador numérico único del tratamiento que se desea eliminar.
     * @return true si la query se procesó correctamente; false en caso de error o fallo relacional.
     */
    public static boolean deleteTratamiento(int id) {
        String sql = "DELETE FROM TRATAMIENTO WHERE id_tratamiento = ?";
        try (Connection conn = SQLDataBaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en TratamientoDAO.deleteTratamiento: " + e.getMessage());
            return false;
        }
    }
}