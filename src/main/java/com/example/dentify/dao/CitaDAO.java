package com.example.dentify.dao;

import com.example.dentify.Configuration.SQLDataBaseManager;
import com.example.dentify.Model.Cita;
import com.example.dentify.Model.Doctor;
import com.example.dentify.Model.Paciente;
import com.example.dentify.Model.Estado;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Acceso a Datos (DAO) para la entidad Cita.
 * Centraliza las transacciones relacionales SQL sobre las tablas 'CITA', 'PACIENTE',
 * 'DOCTOR' y 'DETALLE_CITA', encargándose de mapear datos compuestos y verificar
 * las restricciones horarias operacionales de la clínica.
 */
public class CitaDAO {

    /**
     * Recupera todas las citas del sistema realizando un mapeo relacional complejo (JOIN).
     * Reconstruye de manera íntegra los objetos Paciente y Doctor asociados, y vincula
     * de forma segura los estados enumerados correspondientes a cada registro.
     * * @return Una colección List con todas las instancias estructuradas de tipo Cita.
     */
    public List<Cita> obtenerTodas() {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT c.id_cita, c.fecha, c.hora, c.motivo, c.id_estado, " +
                "p.id_paciente, p.nombre AS nomP, p.apellido AS apeP, p.telefono, p.correo_electronico AS corrP, p.fecha_nacimiento AS fecP, " +
                "d.id_doctor, d.nombre AS nomD, d.numero_colegiado, d.direccion, d.fecha_nacimiento AS fecD, " +
                "det.id_tratamiento, det.observaciones " +
                "FROM CITA c " +
                "JOIN PACIENTE p ON c.id_paciente = p.id_paciente " +
                "JOIN DOCTOR d ON c.id_doctor = d.id_doctor " +
                "LEFT JOIN DETALLE_CITA det ON c.id_cita = det.id_cita";

        try (Connection connection = SQLDataBaseManager.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Paciente paciente = new Paciente(
                        rs.getInt("id_paciente"),
                        rs.getString("nomP"),
                        rs.getString("apeP"),
                        rs.getString("telefono"),
                        rs.getString("corrP"),
                        rs.getDate("fecP") != null ? rs.getDate("fecP").toLocalDate() : null
                );

                Doctor doctor = new Doctor(
                        rs.getInt("id_doctor"),
                        rs.getString("nomD"),
                        rs.getDate("fecD") != null ? rs.getDate("fecD").toLocalDate() : null,
                        rs.getString("direccion"),
                        rs.getString("numero_colegiado"),
                        null
                );

                int idEstadoBBDD = rs.getInt("id_estado");
                Estado estadoEnum = null;
                if (idEstadoBBDD > 0 && idEstadoBBDD <= Estado.values().length) {
                    estadoEnum = Estado.values()[idEstadoBBDD - 1];
                }

                LocalDate fechaCita = rs.getDate("fecha") != null ? rs.getDate("fecha").toLocalDate() : null;
                Time horaSql = rs.getTime("hora");
                LocalDateTime fechaHoraCombinada = null;
                if (fechaCita != null && horaSql != null) {
                    fechaHoraCombinada = LocalDateTime.of(fechaCita, horaSql.toLocalTime());
                }

                Cita cita = new Cita(
                        rs.getInt("id_cita"),
                        fechaCita,
                        fechaHoraCombinada,
                        rs.getString("motivo"),
                        estadoEnum,
                        paciente,
                        doctor,
                        rs.getInt("id_tratamiento"),
                        rs.getString("observaciones")
                );

                lista.add(cita);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las citas: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Comprueba si existe conflicto de horario para un médico o paciente en una franja horaria.
     * Previene la superposición de agendas en una misma fecha y hora.
     * * @param idDoctor Identificador único del profesional médico.
     * @param idPaciente Identificador único del paciente.
     * @param fecha Fecha de la consulta a comprobar.
     * @param hora Hora exacta del tramo de reserva.
     * @return true si la franja está ocupada por cualquiera de las dos entidades; false si está disponible.
     */
    public boolean estaOcupado(int idDoctor, int idPaciente, LocalDate fecha, LocalTime hora) {
        String sql = "SELECT COUNT(*) FROM CITA WHERE (id_doctor = ? OR id_paciente = ?) AND fecha = ? AND hora = ?";

        try (Connection connection = SQLDataBaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, idDoctor);
            pstmt.setInt(2, idPaciente);
            pstmt.setDate(3, Date.valueOf(fecha));
            pstmt.setTime(4, Time.valueOf(hora));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar ocupación en CITA: " + e.getMessage());
        }
        return true;
    }

    /**
     * Valida e inserta un nuevo registro de cita médica en la base de datos MySQL.
     * Evalúa las reglas de negocio del modelo y verifica la disponibilidad de agenda antes de persistir.
     * * @param cita Objeto Cita con la información completa a agendar.
     * @return true si la operación INSERT afectó registros con éxito; false si falló o rompió validaciones.
     */
    public boolean insertarCita(Cita cita) {
        if (!cita.esValida()) {
            System.err.println("Error: Los datos de la cita no cumplen con las reglas de negocio.");
            return false;
        }

        LocalDate fecha = cita.getFecha();
        LocalTime hora = cita.getHora() != null ? cita.getHora().toLocalTime() : null;

        if (fecha == null || hora == null) {
            System.err.println("Error: La fecha u hora de la cita es nula.");
            return false;
        }

        if (estaOcupado(cita.getIdDoctor(), cita.getIdPaciente(), fecha, hora)) {
            System.err.println("Error: El doctor o el paciente ya tienen una cita programada en esa franja horaria.");
            return false;
        }

        String sql = "INSERT INTO CITA (fecha, hora, motivo, id_estado, id_paciente, id_doctor) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = SQLDataBaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setDate(1, Date.valueOf(fecha));
            pstmt.setTime(2, Time.valueOf(hora));
            pstmt.setString(3, cita.getMotivo());
            pstmt.setInt(4, cita.getEstado() != null ? cita.getEstado().ordinal() + 1 : 1);
            pstmt.setInt(5, cita.getIdPaciente());
            pstmt.setInt(6, cita.getIdDoctor());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar la cita en la base de datos: " + e.getMessage());
            return false;
        }
    }

    /**
     * Remueve físicamente un registro de cita de la tabla mediante su identificador primario.
     * Realiza un control de existencia preventivo antes de lanzar la instrucción DELETE.
     * * @param idCita Clave primaria de la cita médica a eliminar.
     * @return true si la query eliminó el registro correctamente; false en caso contrario.
     */
    public static boolean eliminarCita(int idCita) {
        if (!existeCita(idCita)) {
            System.err.println("Error: No existe una cita con el ID: " + idCita);
            return false;
        }

        String sql = "DELETE FROM CITA WHERE id_cita = ?";

        try (Connection connection = SQLDataBaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, idCita);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Cita con ID " + idCita + " eliminada correctamente.");
                return true;
            } else {
                System.err.println("No se pudo eliminar la cita con ID: " + idCita);
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar la cita: " + e.getMessage());
            return false;
        }
    }

    /**
     * Comprueba la presencia física de una cita en la base de datos MySQL por su ID.
     * * @param idCita Clave primaria a consultar.
     * @return true si el recuento es mayor a cero; false si no existe la fila.
     */
    private static boolean existeCita(int idCita) {
        String sql = "SELECT COUNT(*) FROM CITA WHERE id_cita = ?";

        try (Connection connection = SQLDataBaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, idCita);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de la cita: " + e.getMessage());
        }
        return false;
    }

    /**
     * Actualiza los datos de una cita existente mediante una sentencia UPDATE filtrada por clave primaria.
     * Sincroniza las claves foráneas numéricas y desglosa campos temporales locales a formatos JDBC SQL.
     * * @param cita Instancia estructurada de Cita con los parámetros modificados.
     * @return true si la fila se actualizó correctamente en el servidor MySQL; false si ocurrió un fallo.
     */
    public boolean actualizarCita(Cita cita) {
        String sql = "UPDATE CITA SET fecha = ?, hora = ?, motivo = ?, id_estado = ?, id_paciente = ?, id_doctor = ? WHERE id_cita = ?";
        try (Connection connection = SQLDataBaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setDate(1, Date.valueOf(cita.getFecha()));

            pstmt.setTime(2, Time.valueOf(cita.getHora().toLocalTime()));
            pstmt.setString(3, cita.getMotivo());
            pstmt.setInt(4, cita.getEstado() != null ? cita.getEstado().ordinal() + 1 : 1);
            pstmt.setInt(5, cita.getPaciente().getId_paciente());
            pstmt.setInt(6, cita.getDoctor().getIdDoctor());
            pstmt.setInt(7, cita.getIdCita());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar la cita en la base de datos: " + e.getMessage());
            return false;
        }
    }
}