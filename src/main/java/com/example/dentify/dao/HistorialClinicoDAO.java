package com.example.dentify.dao;

import com.example.dentify.Configuration.SQLDataBaseManager;
import com.example.dentify.Model.HistorialClinico;
import com.example.dentify.Model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorialClinicoDAO {

    //CONSULTA 1, PARA INSERTAR HISTORIAL
    private static final String SQL_INSERT =
        "INSERT INTO HISTORIAL_CLINICO (id_paciente, fecha_creacion, grupo_sanguineo) VALUES (?, ?, ?)";

    //CONSULTA 2, PARA SELECCIONAR TODOS LOS HISTORIALES

        private static final String SQL_SELECT_ALL =
        "SELECT h.id_historial_clinico, h.id_paciente, h.fecha_creacion, h.grupo_sanguineo, " +
        "p.nombre, p.apellido, p.telefono, p.correo_electronico, p.fecha_nacimiento " +
        "FROM HISTORIAL_CLINICO h " +
        "JOIN PACIENTE p ON h.id_paciente = p.id_paciente " +
        "ORDER BY h.id_historial_clinico";

        //CONSULTA 3, PARA ACTUALIZAR HISTORIAL
    private static final String SQL_UPDATE =
        "UPDATE HISTORIAL_CLINICO SET id_paciente = ?, fecha_creacion = ?, grupo_sanguineo = ? " +
        "WHERE id_historial_clinico = ?";

    //CONSULTA 4 , SEGUIMIENTO CRONOLOGICO DE INTERVENCIONES DE UN PACIENTE

    private static final String SQL_SELECT_CRONOLOGICO =
        "SELECT c.fecha, t.nombre AS tratamiento, dc.observaciones " +
        "FROM CITA c " +
        "JOIN DETALLE_CITA dc ON c.id_cita = dc.id_cita " +
        "JOIN TRATAMIENTO t ON dc.id_tratamiento = t.id_tratamiento " +
        "WHERE c.id_paciente = ? " +
        "ORDER BY c.fecha DESC";


    //METODO PARA REGISTRAR UN NUEVO HISTORIAL
    public boolean insertarHistorial(HistorialClinico historial) {
        Connection conn = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        try {
            //CONEXION A BASE DE DATOS
            conn = SQLDataBaseManager.getConnection();
            ps = conn.prepareStatement(SQL_INSERT);

            //MAPEO DATOS CONTROLANDO POSIBLE NULOS
            ps.setInt(1, historial.getIdPaciente());
            ps.setDate(2, historial.getFechaCreacion() != null ? Date.valueOf(historial.getFechaCreacion()) : Date.valueOf(java.time.LocalDate.now()));
            ps.setString(3, historial.getGrupoSanguineo());

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al insertar el historial clínico: " + e.getMessage());
        } finally {

            try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }

        return rowsAffected > 0;

    }


    public List<HistorialClinico> listarHistoriales() {
        List<HistorialClinico> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = SQLDataBaseManager.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();

            while (rs.next()) {
                // 1.RECONSTRUYO EL OBJETO PACIENTE CON LOS DATOS DE SQL
                Paciente paciente = new Paciente(
                    rs.getInt("id_paciente"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("correo_electronico"),
                    rs.getDate("fecha_nacimiento") != null ? rs.getDate("fecha_nacimiento").toLocalDate() : null
                );

                // CONSTRUYO EL HISTORIAL CON MI CONSTRUCTOR
                HistorialClinico historial = new HistorialClinico(
                    rs.getInt("id_historial_clinico"),
                    paciente,
                    rs.getDate("fecha_creacion") != null ? rs.getDate("fecha_creacion").toLocalDate() : null,
                    rs.getString("grupo_sanguineo")
                );

                lista.add(historial);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los historiales clínicos: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
            try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }

        return lista;
    }


    public boolean actualizarHistorial(HistorialClinico historial) {
        Connection conn = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        try {
            conn = SQLDataBaseManager.getConnection();
            ps = conn.prepareStatement(SQL_UPDATE);

            ps.setInt(1, historial.getIdPaciente());
            ps.setDate(2, historial.getFechaCreacion() != null ? Date.valueOf(historial.getFechaCreacion()) : null);
            ps.setString(3, historial.getGrupoSanguineo());
            ps.setInt(4, historial.getIdHistorialClinico());

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar el historial clínico: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }

        return rowsAffected > 0;

    }


    public List<String> obtenerIntervencionesCronologicas(int idPaciente) {
        List<String> intervenciones = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = SQLDataBaseManager.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_CRONOLOGICO);
            ps.setInt(1, idPaciente);
            rs = ps.executeQuery();

            while (rs.next()) {
                String fecha = rs.getDate("fecha") != null ? rs.getDate("fecha").toString() : "Sin fecha";
                String tratamiento = rs.getString("tratamiento");
                String observaciones = rs.getString("observaciones");

                String linea = "[" + fecha + "] " + tratamiento + " - Obs: " + (observaciones != null ? observaciones : "Ninguna");
                intervenciones.add(linea);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener intervenciones cronológicas: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
            try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }

        return intervenciones;
    }

}

