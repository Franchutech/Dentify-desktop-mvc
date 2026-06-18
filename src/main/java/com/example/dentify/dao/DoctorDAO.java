package com.example.dentify.dao;

import com.example.dentify.Configuration.SQLDataBaseManager;
import com.example.dentify.Model.Doctor;
import com.example.dentify.Model.Especialidad;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DoctorDAO {

    //METODO PARA BUSCAR DOCTOR POR NOMBRE, NÚMERO DE COLEGIADO O ESPECIALIDAD
    public static List<Doctor> getdoctoresByNameColegiadoEspecialidad(String texto){
        List<Doctor> doctores = new ArrayList<>();

        String sqldoctor = "SELECT * FROM doctor WHERE nombre LIKE ? OR numero_colegiado LIKE ? OR especialidad LIKE ?";

        try(Connection connection = SQLDataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqldoctor)) {

            String filtro = "%" + texto + "%";
            statement.setString(1, filtro);
            statement.setString(2, filtro);
            statement.setString(3, filtro);

            ResultSet resultSets = statement.executeQuery();

            while (resultSets.next()) {
                int idDoctor = resultSets.getInt(1);
                String nombre = resultSets.getNString(2);
                LocalDate fechaNacimiento = resultSets.getObject(3, LocalDate.class);
                String direccion = resultSets.getNString(4);
                String numColegiado = resultSets.getNString(5);

                Especialidad especialidad = null;
                String espStr = resultSets.getString(6);
                if (espStr != null) {
                    try {
                        especialidad = Especialidad.valueOf(espStr.toUpperCase().trim());
                    } catch (IllegalArgumentException e) {
                        especialidad = Especialidad.GENERAL;
                    }
                }

                Doctor d = new Doctor(idDoctor, nombre, fechaNacimiento, direccion, numColegiado, especialidad);
                doctores.add(d);
            }
        } catch (SQLException e) {
            System.err.println("Error en el filtro doctores " + e.getMessage());
        }
        return doctores;
    }

    //METODO PARA AÑADIR UN NUEVO DOCTOR
    public static boolean createDoctor(Doctor d){
        boolean result = false;

        // CORREGIDO: Cambiada la columna 'especialidad' por 'id_especialidad'
        String sqlInsertDoctores = "INSERT INTO doctor (nombre, fecha_nacimiento, direccion, numero_colegiado, id_especialidad) VALUES (?, ?, ?, ?, ?)";

        try(Connection connection = SQLDataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlInsertDoctores)){

            statement.setNString(1, d.getNombre());

            if(d.getFechaNacimiento() != null){
                statement.setDate(2, java.sql.Date.valueOf(d.getFechaNacimiento()));
            } else {
                statement.setNull(2, java.sql.Types.DATE);
            }

            statement.setNString(3, d.getDireccion());
            statement.setNString(4, d.getNumColegiado());


            int idEsp = 1;
            if(d.getEspecialidad() != null){
                switch (d.getEspecialidad()) {
                    case GENERAL -> idEsp = 1;
                    case ENDODONCITAS -> idEsp = 2;
                    case ORTODONCISTA -> idEsp = 3;
                    case CIRUGIA -> idEsp = 4;
                    case PERIODONCISTA -> idEsp = 5;
                }
                statement.setInt(5, idEsp);
            } else {
                statement.setInt(5, 1); // Si viene nulo, le asignamos GENERAL por seguridad
            }

            statement.executeUpdate();
            result = true;

        } catch(SQLException e){
            System.err.println("Error en createDoctor: " + e.getMessage());
        }
        return result;
    }

    //METODO PARA ELIMINAR UN DOCTOR POR SU ID
    public static boolean deleteDoctor(int idDoctor){
        boolean result = false;
        String sqlDeleteDoctores = "DELETE FROM doctor WHERE id_doctor = ?";

        try(Connection connection = SQLDataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlDeleteDoctores)) {

            statement.setInt(1, idDoctor);
            statement.execute();
            result = true;

        } catch (SQLException e) {
            System.err.println("Error al eliminar doctor " + e.getMessage());
        }
        return result;
    }

    //METODO PARA MOSTRAR TODOS LOS DOCTORES
    public static List<Doctor> getAllDoctores(){
        List<Doctor> doctores = new LinkedList<>();

        String sqldoctores = "SELECT id_doctor, nombre, fecha_nacimiento, direccion, numero_colegiado, id_especialidad FROM doctor";

        try(Connection connection = SQLDataBaseManager.getConnection();
            Statement stament = connection.createStatement();
            ResultSet resultSets = stament.executeQuery(sqldoctores)) {

            while (resultSets.next()) {

                int idDoctor = resultSets.getInt("id_doctor");
                String nombre = resultSets.getNString("nombre");

                java.sql.Date dbDate = resultSets.getDate("fecha_nacimiento");
                LocalDate fechaNacimiento = (dbDate != null) ? dbDate.toLocalDate() : null;

                String direccion = resultSets.getNString("direccion");

                String numColegiado = resultSets.getNString("numero_colegiado");

                Especialidad especialidad = Especialidad.GENERAL;
                int idEsp = resultSets.getInt("id_especialidad");
                switch(idEsp) {
                    case 1 -> especialidad = Especialidad.GENERAL;
                    case 2 -> especialidad = Especialidad.ENDODONCITAS;
                    case 3 -> especialidad = Especialidad.ORTODONCISTA;
                    case 4 -> especialidad = Especialidad.CIRUGIA;
                    case 5 -> especialidad = Especialidad.PERIODONCISTA;
                }

                Doctor d = new Doctor(idDoctor, nombre, fechaNacimiento, direccion, numColegiado, especialidad);
                doctores.add(d);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar doctores en el DAO: " + e.getMessage());
            e.printStackTrace();
        }
        return doctores;
    }

    //METODO PARA ACTUALIZAR UN DOCTOR QUE YA EXISTE
    public static boolean updateDoctor(Doctor d) {
        boolean result = false;
        String sqlUpdate = "UPDATE doctor SET nombre = ?, fecha_nacimiento = ?, direccion = ?, numero_colegiado = ?, id_especialidad = ? WHERE id_doctor = ?";

        try (Connection connection = SQLDataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlUpdate)) {

            statement.setNString(1, d.getNombre());

            if (d.getFechaNacimiento() != null) {
                statement.setDate(2, java.sql.Date.valueOf(d.getFechaNacimiento()));
            } else {
                statement.setNull(2, java.sql.Types.DATE);
            }

            statement.setNString(3, d.getDireccion());
            statement.setNString(4, d.getNumColegiado());

            int idEsp = 1; // GENERAL por defecto
            if (d.getEspecialidad() != null) {
                switch (d.getEspecialidad()) {
                    case GENERAL -> idEsp = 1;
                    case ENDODONCITAS -> idEsp = 2;
                    case ORTODONCISTA -> idEsp = 3;
                    case CIRUGIA -> idEsp = 4;
                    case PERIODONCISTA -> idEsp = 5;
                }
            }
            statement.setInt(5, idEsp);
            statement.setInt(6, d.getIdDoctor());

            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                result = true;
            }

        } catch (SQLException e) {
            System.err.println("Error en updateDoctor: " + e.getMessage());
        }
        return result;
    }
}
