package com.example.dentify.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Administrador de la configuración y conectividad con la base de datos MySQL.
 * Implementa el patrón Singleton para asegurar la existencia de una única conexión física
 * activa (reutilizable) a lo largo del ciclo de vida de la aplicación, optimizando la
 * apertura y el cierre de sockets contra el servidor relacional.
 */
public class SQLDataBaseManager {

    /**
     * Nombre cualificado de la clase del Driver JDBC de MySQL Connector/J.
     */
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * Dirección URL base del protocolo de red para la conexión con el servidor MySQL local.
     */
    private static final String URL = "jdbc:mysql://localhost:3306/";

    /**
     * Nombre del esquema o base de datos relacional del proyecto (sistema_medico).
     */
    private static final String SCHEMA = "sistema_medico";

    /**
     * Cuenta de usuario con privilegios de acceso al gestor de bases de datos.
     */
    private static final String USUARIO = "root";

    /**
     * Credencial o contraseña de acceso del usuario de la base de datos local.
     */
    private static final String CLAVE = "1234";

    /**
     * Instancia única y persistente del objeto Connection utilizado por los DAOs.
     */
    private static Connection connection = null;

    /**
     * Constructor privado para restringir la instanciación externa de la clase de configuración.
     */
    private SQLDataBaseManager() {}

    /**
     * Evalúa el estado de la conexión física actual y devuelve una instancia activa de Connection.
     * Si el puntero es nulo o fue cerrado previamente, carga dinámicamente el driver de MySQL
     * e inicializa un nuevo puente síncrono utilizando las credenciales preconfiguradas.
     * * @return Objeto Connection activo listo para procesar sentencias SQL, o null en caso de fallo crítico.
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL + SCHEMA, USUARIO, CLAVE);
//                System.out.println("🦷🦷🦷 ¡Encías sanas y conexión abierta!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("🦷 Error parece que el acceso al driver tiene una Caries: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println(" 🦷 Error parece que el acceso de SQL tiene una Caries: " + e.getMessage());
        }

        return connection;
    }

    /**
     * Libera de forma física los recursos de red del socket abierto contra el servidor de MySQL.
     * Verifica preventivamente que la conexión exista y no haya sido clausurada antes por el gestor.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("[Database] Conexión cerrada: ¡Dientes limpios y servidor feliz!");
            }
        } catch (SQLException e) {
            System.err.println("Error al hacer la limpieza (cerrar conexión): " + e.getMessage());
        }
    }
}