package com.example.dentify;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Punto de entrada principal de la aplicación Dentify.
 * Hereda de la clase Application de JavaFX para inicializar el ciclo de vida del entorno gráfico,
 * cargar el contenedor de la interfaz de usuario raíz (Main.fxml) y desplegar la ventana principal (Stage).
 */
public class App extends Application {

    /**
     * Inicializa y configura la escena principal de JavaFX.
     * Carga el archivo FXML base, establece las dimensiones por defecto de la ventana (850x650)
     * y muestra el escenario al usuario.
     * * @param stage Escenario principal (Stage) proporcionado por la plataforma JavaFX.
     * @throws IOException Si ocurre un error al intentar leer o cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/example/dentify/Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 650);
        stage.setTitle("Dentify");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método de lanzamiento estándar que sirve como punto de acceso en entornos
     * donde no está completamente integrado el ciclo de vida de JavaFX.
     * * @param args Argumentos de la línea de comandos pasados al iniciar la app.
     */
    public static void main(String[] args) {

        launch();

    }
}