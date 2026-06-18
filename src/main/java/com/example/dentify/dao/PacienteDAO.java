package com.example.dentify.dao;

import com.example.dentify.Configuration.SQLDataBaseManager;
import com.example.dentify.Model.Paciente;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class PacienteDAO {

    public static List <Paciente> getpacientesByNameSurnamePhone(String texto){
        List <Paciente> pacientes = new ArrayList<>();

        String sqlpaciente = "SELECT * FROM paciente WHERE nombre LIKE ? OR apellido LIKE ? OR telefono LIKE ? ";

        try(Connection connection = SQLDataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlpaciente)) {

            String filtro = "%" + texto + "%";
            statement.setString(1, filtro);
            statement.setString(2, filtro);
            statement.setString(3, filtro);

            ResultSet resultSets = statement.executeQuery();

            while (resultSets.next()) {
                int id_paciente = resultSets.getInt(1);
                String nombre = resultSets.getNString(2);
                String apellido = resultSets.getNString(3);
                String telefono = resultSets.getNString(4);
                String correo_electronico = resultSets.getNString(5);
                LocalDate fecha_nacimiento = resultSets.getObject(6, LocalDate.class);

                Paciente p = new Paciente(id_paciente, nombre, apellido, telefono, correo_electronico, fecha_nacimiento);
                pacientes.add(p);

            }
        }catch (SQLException e) {
            System.err.println("Error en el filtro " + e.getMessage());
        }
        return pacientes;
    }

    public static boolean createPaciente(Paciente p){

        boolean result = false;

        String sqlInsertPacientes = "INSERT INTO paciente (nombre, apellido, telefono, correo_electronico, fecha_nacimiento) VALUES (?, ?, ?, ?, ?)";

        try(Connection connection = SQLDataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlInsertPacientes)){

            statement.setNString(1, p.getNombre());
            statement.setNString(2, p.getApellido());
            statement.setNString(3, p.getTelefono());
            statement.setNString(4, p.getCorreo_electronico());
            if(p.getFecha_nacimiento()!=null){
                statement.setDate(5, java.sql.Date.valueOf(p.getFecha_nacimiento()));
            }else {
                statement.setNull(5, java.sql.Types.DATE);
            }

            statement.execute();
            result = true;

        }catch(SQLException e){
            System.err.println("Error en createPaciente " + e.getMessage());
        }
        return result;
    }

    public static boolean deletePaciente(int id_paciente){
        boolean result = false;
        String sqlDeletePacientes = "DELETE FROM paciente WHERE id_paciente = ?";

        try(Connection connection = SQLDataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlDeletePacientes)) {

            statement.setInt(1, id_paciente);
            statement.execute();
            result = true;

        } catch (SQLException e) {
            System.err.println("Error al eliminar paciente " + e.getMessage());
        }
        return result;
    }

    public static List<Paciente> getAllpacientes(){
        List<Paciente> pacientes = new LinkedList<>();

        String sqlpacientes = "SELECT * FROM paciente";

        try(Connection connection = SQLDataBaseManager.getConnection();
            Statement stament = connection.createStatement();
            ResultSet resultSets = stament.executeQuery(sqlpacientes)) {

            while (resultSets.next()) {
                int id_paciente = resultSets.getInt(1);
                String nombre = resultSets.getNString(2);
                String apellido = resultSets.getNString(3);
                String telefono = resultSets.getNString(4);
                String correo_electronico = resultSets.getNString(5);
                LocalDate fecha_nacimiento = resultSets.getObject(6, LocalDate.class);

                Paciente p = new Paciente(id_paciente, nombre, apellido, telefono, correo_electronico, fecha_nacimiento);
                pacientes.add(p);
            }
        }catch (SQLException e) {
            System.err.println("Error al listar pacientes " + e.getMessage());
        }
        return pacientes;
    }

    public static boolean actualizarPaciente(Paciente p) {
        String sql = "UPDATE PACIENTE SET nombre=?, apellido=?, telefono=?, correo_electronico=?, fecha_nacimiento=? WHERE id_paciente=?";
        try (Connection conn = SQLDataBaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, p.getNombre());
            pstmt.setString(2, p.getApellido());
            pstmt.setString(3, p.getTelefono());
            pstmt.setString(4, p.getCorreo_electronico());
            pstmt.setDate(5, p.getFecha_nacimiento() != null ? Date.valueOf(p.getFecha_nacimiento()) : null);
            pstmt.setInt(6, p.getId_paciente()); // Clave primaria indispensable

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en DAO al actualizar: " + e.getMessage());
            return false;
        }
    }

}
