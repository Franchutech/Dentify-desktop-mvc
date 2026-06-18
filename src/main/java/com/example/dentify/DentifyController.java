package com.example.dentify;

import com.example.dentify.Model.Cita;
import com.example.dentify.Model.Doctor;
import com.example.dentify.Model.Paciente;
import com.example.dentify.dao.DoctorDAO;
import com.example.dentify.dao.CitaDAO;
import com.example.dentify.dao.PacienteDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import com.example.dentify.Model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


import java.util.List;


public class DentifyController {

    // LISTAS DE RESPALDO Y FILTRADO DE PACIENTES
    private javafx.collections.ObservableList<Paciente> todosLosPacientes = javafx.collections.FXCollections.observableArrayList();
    private javafx.collections.ObservableList<Paciente> pacientesFiltrados = javafx.collections.FXCollections.observableArrayList();

    // ------ Elementos de tratamientos -----
    @FXML
    private TableView<Tratamiento> tablaTratamientos;
    @FXML
    private TableColumn<Tratamiento, Integer> colIdTratamiento;
    @FXML
    private TableColumn<Tratamiento, String> colNombreTratamiento;
    @FXML
    private TableColumn<Tratamiento, String> colDescripcion;
    @FXML
    private TableColumn<Tratamiento, Void> colAccionesTratamientos;
    @FXML
    private Label lblTituloFormularioTratamiento;
    @FXML
    private TextField TFNombreTratamiento;
    @FXML
    private TextArea TFDescripcion;
    @FXML
    private Button btnGuardarTratamiento;

    // ------ Elementos de Citas ------
    @FXML
    private ChoiceBox<Estado> choiceBoxEstados;
    @FXML
    private ChoiceBox<Paciente> choicePaciente;
    @FXML
    private ChoiceBox<Doctor> choiceDoctor;
    @FXML
    private ComboBox<java.time.LocalTime> cboHora;
    @FXML
    private Label lblTituloCita;
    @FXML
    private Button btnGuardarCita;
    @FXML
    private Button btnNuevo;//AGREGADO POR FRANCELLA
    @FXML
    private DatePicker DatePickerFecha;
    @FXML
    private TextArea txtMotivo;
    @FXML
    private TableView<Cita> tablaCitas;
    @FXML
    private TableColumn<Cita, Void> colAccionesCitas;
    @FXML
    private TableColumn<Cita, Integer> colId;
    @FXML
    private TableColumn<Cita, LocalDate> colFecha;
    @FXML
    private TableColumn<Cita, LocalDateTime> colHora;
    @FXML
    private TableColumn<Cita, String> colPaciente;
    @FXML
    private TableColumn<Cita, String> colDoctor;
    @FXML
    private TableColumn<Cita, String> colMotivo;
    @FXML
    private TableColumn<Cita, Estado> colEstado;


    // ------ Elementos de Pacientes ------
    @FXML
    private Label lblTituloFormulario;
    @FXML
    private TextField TFNombre;
    @FXML
    private TextField TFApellido;
    @FXML
    private TextField TFTelefono;
    @FXML
    private TextField TFCorreo;
    @FXML
    private DatePicker TFNacimiento;
    @FXML
    private Button btnGuardar;
    @FXML
    private TableColumn<Paciente, String> colNombre;
    @FXML
    private TableColumn<Paciente, String> colApellido;
    @FXML
    private TableColumn<Paciente, String> colTelefono;
    @FXML
    private TableColumn<Paciente, String> colCorreo;
    @FXML
    private TableColumn<Paciente, Void> colAcciones;
    @FXML
    private TableView<Paciente> tablaPacientes;
    @FXML
    private TextField txtBuscar;


    // ------- Elementos de Doctores ---------
    @FXML
    private TableView<Doctor> tablaDoctores;
    @FXML
    private TableColumn<Doctor, Integer> colIdDoctor;
    @FXML
    private TableColumn<Doctor, String> colNombreDoctor;
    @FXML
    private TableColumn<Doctor, java.time.LocalDate> colFechaNacimiento;
    @FXML
    private TableColumn<Doctor, String> colDireccion;
    @FXML
    private TableColumn<Doctor, String> colNumColegiado;
    @FXML
    private TableColumn<Doctor, Especialidad> colEspecialidad;
    @FXML
    private TableColumn<Doctor, Void> colAccionesDoc;
    @FXML
    private TextField TFNumColegiado;
    @FXML
    private TextField TFDireccion;
    @FXML
    private DatePicker TFNacimientoDoctor;
    @FXML
    private ChoiceBox<Especialidad> choiceBoxEspecialidad;
    @FXML
    private TextField TFNombreCompleto;
    private javafx.collections.ObservableList<Doctor> todosLosDoctores = javafx.collections.FXCollections.observableArrayList();
    private javafx.collections.ObservableList<Doctor> doctoresFiltrados = javafx.collections.FXCollections.observableArrayList();

    // Objetos auxiliares para saber si estamos editando
    private Tratamiento tratamientoAEditar = null;
    private Paciente pacienteAEditar = null;
    private Cita citaAEditar = null;
    private Doctor doctorAEditar = null;


    @FXML
    public void initialize() {
        if (tablaCitas != null) {
            try {
                com.example.dentify.dao.CitaDAO citaDAO = new com.example.dentify.dao.CitaDAO();
                List<Cita> todasLasCitas = citaDAO.obtenerTodas();
                tablaCitas.getItems().setAll(todasLasCitas);

                configurarColumnasCitas();
                if (colAccionesCitas != null) {
                    configurarColumnaAccionesCitas();
                }

                if (choiceBoxEstados != null) {
                    choiceBoxEstados.getItems().clear();
                    choiceBoxEstados.getItems().add(null);
                    choiceBoxEstados.getItems().addAll(com.example.dentify.Model.Estado.values());
                    choiceBoxEstados.setConverter(new javafx.util.StringConverter<com.example.dentify.Model.Estado>() {
                        @Override
                        public String toString(com.example.dentify.Model.Estado estado) {
                            return (estado == null) ? "TODOS" : estado.toString();
                        }

                        @Override
                        public com.example.dentify.Model.Estado fromString(String string) {
                            return null;
                        }
                    });

                    choiceBoxEstados.getSelectionModel().selectFirst();
                    choiceBoxEstados.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, nuevoEstado) -> {
                        if (nuevoEstado == null) {
                            tablaCitas.getItems().setAll(todasLasCitas);
                        } else {
                            List<Cita> citasFiltradas = todasLasCitas.stream()
                                    .filter(cita -> cita.getEstado() == nuevoEstado)
                                    .toList();

                            tablaCitas.getItems().setAll(citasFiltradas);
                        }
                    });
                }
            } catch (Exception e) {
                System.err.println("Error al cargar las citas e inicializar filtros: " + e.getMessage());
            }
        }

        if (colAcciones != null) {
            configurarColumnaAccionesPaciente();
        }

        if (tablaPacientes != null) {
            colNombre.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nombre"));
            colApellido.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("apellido"));
            colTelefono.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("telefono"));
            colCorreo.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("correo_electronico"));

            configurarColumnaAccionesPaciente();

            tablaPacientes.setItems(pacientesFiltrados);

            cargarPacientes();

            if (txtBuscar != null) {
                txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
                    pacientesFiltrados.clear();

                    if (newValue == null || newValue.trim().isEmpty()) {
                        pacientesFiltrados.addAll(todosLosPacientes);
                    } else {
                        String busqueda = newValue.toLowerCase().trim();

                        for (Paciente p : todosLosPacientes) {
                            boolean coincide = false;

                            if (p.getNombre() != null && p.getNombre().toLowerCase().contains(busqueda)) {
                                coincide = true;
                            } else if (p.getApellido() != null && p.getApellido().toLowerCase().contains(busqueda)) {
                                coincide = true;
                            } else if (p.getTelefono() != null && p.getTelefono().contains(busqueda)) {
                                coincide = true;
                            } else if (p.getCorreo_electronico() != null && p.getCorreo_electronico().toLowerCase().contains(busqueda)) {
                                coincide = true;
                            } else if (String.valueOf(p.getId_paciente()).contains(busqueda)) {
                                coincide = true;
                            }

                            if (coincide) {
                                pacientesFiltrados.add(p);
                            }
                        }
                    }
                });
            }
        }

        if (tablaDoctores != null) {
            if (colNombreDoctor != null) {
                colNombreDoctor.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nombre"));
            }

            if (colFechaNacimiento != null) {
                colFechaNacimiento.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("fechaNacimiento"));
            }

            if (colDireccion != null) {
                colDireccion.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("direccion"));
            }

            if (colNumColegiado != null) {
                colNumColegiado.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("numColegiado"));
            }

            configurarColumnaEspecialidad();
            configurarColumnaAccionesDoctor();

            tablaDoctores.setItems(doctoresFiltrados);
            cargarDoctores();

            if (txtBuscar != null) {
                txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
                    doctoresFiltrados.clear();
                    if (newValue == null || newValue.trim().isEmpty()) {
                        doctoresFiltrados.addAll(todosLosDoctores);
                    } else {
                        String busqueda = newValue.toLowerCase().trim();
                        for (Doctor d : todosLosDoctores) {
                            boolean coincide = false;
                            if (d.getNombre() != null && d.getNombre().toLowerCase().contains(busqueda)) {
                                coincide = true;
                            } else if (d.getNumColegiado() != null && d.getNumColegiado().contains(busqueda)) {
                                coincide = true;
                            } else if (d.getDireccion() != null && d.getDireccion().toLowerCase().contains(busqueda)) {
                                coincide = true;
                            } else if (d.getEspecialidad() != null && d.getEspecialidad().name().toLowerCase().contains(busqueda)) {
                                coincide = true;
                            }
                            if (coincide) {
                                doctoresFiltrados.add(d);
                            }
                        }
                    }
                });
            }
        }

        if (choiceBoxEspecialidad != null) {
            choiceBoxEspecialidad.getItems().setAll(Especialidad.values());
        }


        // LLENAR DESPLEGABLES DEL FORMULARIO DE CITAS
        if (choicePaciente != null && choiceDoctor != null) {
            try {
                List<Paciente> listaPacientes = com.example.dentify.dao.PacienteDAO.getAllpacientes();
                choicePaciente.getItems().setAll(listaPacientes);

                java.util.List<Doctor> listaDoctores = new java.util.ArrayList<>();

                String sqlDoctores = "SELECT id_doctor, nombre, fecha_nacimiento, direccion, numero_colegiado, id_especialidad FROM DOCTOR";

                try (java.sql.Connection conn = com.example.dentify.Configuration.SQLDataBaseManager.getConnection();
                     java.sql.Statement stmt = conn.createStatement();
                     java.sql.ResultSet rs = stmt.executeQuery(sqlDoctores)) {

                    while (rs.next()) {
                        // Obtenemos el ID numérico de la especialidad
                        int idEsp = rs.getInt("id_especialidad");
                        com.example.dentify.Model.Especialidad espEnum = null;

                        // Mapeamos de forma segura el entero al Enum
                        com.example.dentify.Model.Especialidad[] valoresEnum = com.example.dentify.Model.Especialidad.values();
                        int indiceEnum = idEsp - 1; 

                        if (indiceEnum >= 0 && indiceEnum < valoresEnum.length) {
                            espEnum = valoresEnum[indiceEnum];
                        }

                        Doctor doc = new Doctor(
                                rs.getInt("id_doctor"),
                                rs.getString("nombre"),
                                rs.getDate("fecha_nacimiento") != null ? rs.getDate("fecha_nacimiento").toLocalDate() : null,
                                rs.getString("direccion"),
                                rs.getString("numero_colegiado"),
                                espEnum
                        );
                        listaDoctores.add(doc);
                    }
                }
                choiceDoctor.getItems().setAll(listaDoctores);

                if (cboHora != null) {
                    java.util.List<java.time.LocalTime> horasDisponibles = new java.util.ArrayList<>();
                    for (int h = 9; h <= 20; h++) {
                        horasDisponibles.add(java.time.LocalTime.of(h, 0));
                        horasDisponibles.add(java.time.LocalTime.of(h, 30));
                    }
                    cboHora.getItems().setAll(horasDisponibles);
                }

                if (choiceBoxEstados != null && choiceBoxEstados.getItems().size() <= 1) {
                    choiceBoxEstados.getItems().setAll(com.example.dentify.Model.Estado.values());
                }

            } catch (Exception e) {
                System.err.println("Error al poblar los desplegables de citas: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // (BLOQUE SEGURO DE HISTORIALES CLÍNICOS CON FILTRO)
        if (gridPanePacientes != null) {
            try {
                com.example.dentify.dao.HistorialClinicoDAO historialDAO = new com.example.dentify.dao.HistorialClinicoDAO();
                List<HistorialClinico> listaHistoriales = historialDAO.listarHistoriales();

                mostrarHistorialesEnPantalla(listaHistoriales);

                // ESCUCHADOR PARA FILTRAR TARJETAS EN TIEMPO REAL
                if (txtBuscar != null) {
                    txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue == null || newValue.trim().isEmpty()) {
                            // Si borra el texto, volvemos a mostrar todos los historiales
                            mostrarHistorialesEnPantalla(listaHistoriales);
                        } else {
                            String busqueda = newValue.toLowerCase().trim();
                            java.util.ArrayList<HistorialClinico> filtrados = new java.util.ArrayList<>();

                            // Recorremos los historiales buscando coincidencia en el paciente
                            for (HistorialClinico hc : listaHistoriales) {
                                if (hc.getPaciente() != null) {
                                    String nombreCompleto = (hc.getPaciente().getNombre() + " " + hc.getPaciente().getApellido()).toLowerCase();
                                    if (nombreCompleto.contains(busqueda)) {
                                        filtrados.add(hc);
                                    }
                                }
                            }
                            // Volvemos a pintar el GridPane solo con los que coinciden
                            mostrarHistorialesEnPantalla(filtrados);
                        }
                    });
                }

            } catch (Exception e) {
                System.err.println("Error al cargar los historiales clínicos en initialize: " + e.getMessage());
            }
        }

    // (BLOQUE SEGURO DE TRATAMIENTOS)
    if(tablaTratamientos !=null)

    {
        //ENLAZO COLUMNAS CON TRATAMIENTOS
        colNombreTratamiento.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("descripcion"));

        // CONFIGURO COLUMNA QUE PINTA LOS BOTONES EDITAR  Y ELIMINAR
        configurarColumnaAccionesTratamientos();

        // CARGO LOS DATOS DEL DAO POR PRIMERA VEZ
        configurarTablaTratamientos();

    }
    }

    //-------- Gestión Doctores ----------- ANDREA 29/05/2026

    private void configurarColumnaAccionesDoctor() {
        if (colAccionesDoc != null) {
            colAccionesDoc.setCellFactory(param -> new TableCell<>() {
                private final Button btnEditar = new Button("Editar");
                private final Button btnEliminar = new Button("Eliminar");
                private final HBox contenedorBotones = new HBox(10);

                {
                    btnEditar.getStyleClass().add("primary-button-table");
                    btnEliminar.getStyleClass().add("danger-button");

                    // --- ACCIÓN EDITAR DOCTOR ---
                    btnEditar.setOnAction(event -> {
                        // Comprobamos si lo que hay en la fila es un Doctor para evitar mezclas
                        Object filaActual = getTableView().getItems().get(getIndex());
                        if (filaActual instanceof Doctor) {
                            Doctor seleccionado = (Doctor) filaActual;
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctoresForm.fxml"));
                                Parent root = loader.load();
                                DentifyController controladorFormulario = loader.getController();
                                controladorFormulario.prepararEdicionDoctor(seleccionado);

                                Stage stage = (Stage) btnEditar.getScene().getWindow();
                                stage.setScene(new Scene(root));
                                stage.setTitle("Dentify - Editar Doctor");
                                stage.show();
                            } catch (IOException e) {
                                System.err.println("Error al ir al formulario de doctores: " + e.getMessage());
                            }
                        }
                    });

                    // --- ACCIÓN ELIMINAR DOCTOR ---
                    btnEliminar.setOnAction(event -> {
                        Object filaActual = getTableView().getItems().get(getIndex());
                        if (filaActual instanceof Doctor) {
                            Doctor seleccionado = (Doctor) filaActual;
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de eliminar al Dr./Dra. " + seleccionado.getNombre() + "?");
                            if (alert.showAndWait().get() == ButtonType.OK) {
                                boolean ok = DoctorDAO.deleteDoctor(seleccionado.getIdDoctor());
                                if (ok) {
                                    getTableView().getItems().remove(seleccionado);
                                }
                            }
                        }
                    });

                    contenedorBotones.getChildren().addAll(btnEditar, btnEliminar);
                    contenedorBotones.setAlignment(javafx.geometry.Pos.CENTER);
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : contenedorBotones);
                }
            });
        }
    }

    @FXML
    public void manejarGuardarDoctor(ActionEvent actionEvent) {
        if (TFNombreCompleto == null || TFNombreCompleto.getText().trim().isEmpty() ||
                TFNumColegiado.getText().trim().isEmpty() || choiceBoxEspecialidad.getValue() == null) {

            Alert alerta = new Alert(Alert.AlertType.WARNING, "Por favor, rellena los campos obligatorios: Nombre, Nº Colegiado y Especialidad.");
            alerta.showAndWait();
            return;
        }

        if (doctorAEditar != null) {
            doctorAEditar.setNombre(TFNombreCompleto.getText().trim());
            doctorAEditar.setDireccion(TFDireccion.getText().trim());
            doctorAEditar.setNumColegiado(TFNumColegiado.getText().trim());
            doctorAEditar.setEspecialidad(choiceBoxEspecialidad.getValue());
            doctorAEditar.setFechaNacimiento(TFNacimientoDoctor.getValue());

            // --- CORREGIDO: Llamamos al método que acabamos de crear en el DAO ---
            boolean editadoOk = DoctorDAO.updateDoctor(doctorAEditar);
            if (editadoOk) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "¡Datos del doctor actualizados con éxito!");
                alerta.showAndWait();
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "No se pudieron actualizar los datos en la base de datos.");
                alerta.showAndWait();
            }

            redireccionarAGestionDoctores(actionEvent);
        } else {
            Doctor nuevo = new Doctor(
                    0,
                    TFNombreCompleto.getText().trim(),
                    TFNacimientoDoctor.getValue(),
                    TFDireccion.getText().trim(),
                    TFNumColegiado.getText().trim(),
                    choiceBoxEspecialidad.getValue()
            );

            boolean insertadoOk = DoctorDAO.createDoctor(nuevo);
            if (insertadoOk) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "¡Doctor guardado correctamente!");
                alerta.showAndWait();
                redireccionarAGestionDoctores(actionEvent);
            } else {
                new Alert(Alert.AlertType.ERROR, "Error al guardar el doctor.").showAndWait();
            }
        }
    }

    public void prepararEdicionDoctor(Doctor doc) {
        this.doctorAEditar = doc;

        // Control seguro de los elementos del formulario
        if (lblTituloFormulario != null) lblTituloFormulario.setText("Editar Doctor");
        if (btnGuardar != null) btnGuardar.setText("Actualizar datos");

        // CORREGIDO AQUÍ: Cambiado a TFNombreCompleto
        if (TFNombreCompleto != null && doc.getNombre() != null) {
            TFNombreCompleto.setText(doc.getNombre());
        }
        if (TFDireccion != null && doc.getDireccion() != null) {
            TFDireccion.setText(doc.getDireccion());
        }
        if (TFNumColegiado != null && doc.getNumColegiado() != null) {
            TFNumColegiado.setText(doc.getNumColegiado());
        }
        if (TFNacimientoDoctor != null) {
            TFNacimientoDoctor.setValue(doc.getFechaNacimiento());
        }
        if (choiceBoxEspecialidad != null) {
            choiceBoxEspecialidad.setValue(doc.getEspecialidad());
        }
    }

    private void cargarDoctores() {
        if (tablaDoctores != null) {
            List<Doctor> lista = DoctorDAO.getAllDoctores();

            todosLosDoctores.clear();
            doctoresFiltrados.clear();

            if (lista != null && !lista.isEmpty()) {
                todosLosDoctores.addAll(lista);
                doctoresFiltrados.addAll(lista);

                // Forzamos la asignación de la lista cargada
                tablaDoctores.setItems(doctoresFiltrados);
                // ¡Línea mágica! Obliga a la interfaz visual a redibujarse con los datos actuales
                tablaDoctores.refresh();

                System.out.println("DEBUG DOCTORES: Se han cargado " + lista.size() + " doctores.");
            } else {
                System.out.println("DEBUG DOCTORES: La lista devuelta por el DAO está vacía o es nula.");
            }
        }
    }

    private void redireccionarAGestionDoctores(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Doctores.fxml"));
            Parent root = loader.load();

            // Forzamos la recarga de datos al volver
            DentifyController controlador = loader.getController();
            controlador.cargarDoctores();

            // Conseguimos la ventana actual de forma segura usando el evento del botón pulsado
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dentify - Gestión de Doctores");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al redirigir a Gestión de Doctores: " + e.getMessage());
        }
    }

    @FXML
    public void manejarNuevoDoctor(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctoresForm.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dentify - Agregar Doctor");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al ir a DoctoresForm: " + e.getMessage());
        }
    }

    @FXML
    public void onVolverDoctoresButton(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dentify - Menú Principal");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al volver a Main.fxml: " + e.getMessage());
        }
    }

    @FXML
    public void onVolverDoctoresFormButtonClick(javafx.event.ActionEvent event) {
        redireccionarAGestionDoctores(event);
    }
    @FXML
    public void onGesDoctoresButtonClick(javafx.event.ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("Doctores.fxml"));
            javafx.scene.Parent root = loader.load();

            // --- ESTO ES LO QUE OBLIGA A LA TABLA A RELLENARSE AL ENTRAR ---
            DentifyController controlador = loader.getController();
            controlador.cargarDoctores();
            // ---------------------------------------------------------------

            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Dentify - Gestión de Doctores");
            stage.show();
        } catch (java.io.IOException e) {
            System.err.println("Error al abrir la gestión de doctores (Doctores.fxml): " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void configurarColumnaEspecialidad() {
        if (colEspecialidad != null) {
            colEspecialidad.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEspecialidad()));
            colEspecialidad.setCellFactory(column -> new TableCell<>() {
                @Override
                protected void updateItem(Especialidad item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        switch (item) {
                            case ENDODONCITAS -> { setText("Endodoncia"); setStyle("-fx-text-fill: #9C27B0; -fx-font-weight: bold;"); }
                            case ORTODONCISTA -> { setText("Ortodoncia"); setStyle("-fx-text-fill: #2196F3; -fx-font-weight: bold;"); }
                            case CIRUGIA -> { setText("Cirugía Maxilofacial"); setStyle("-fx-text-fill: #F44336; -fx-font-weight: bold;"); }
                            case GENERAL -> { setText("Odontología General"); setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;"); }
                            case PERIODONCISTA -> { setText("Periodoncia"); setStyle("-fx-text-fill: #FF9800; -fx-font-weight: bold;"); }
                            default -> { setText(item.name()); setStyle(""); }
                        }
                    }
                }
            });
        }
    }

    private void configurarSelectores() {
        // Aquí puedes meter la lógica para rellenar los ComboBox o ChoiceBox más adelante
    }

    // ------ GESTION PACIENTES ------

    private void configurarColumnaAccionesPaciente() {
        colAcciones.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnEliminar = new Button("Eliminar");
            private final HBox contenedorBotones = new HBox(10);

            {
                btnEditar.getStyleClass().add("primary-button-table");
                btnEliminar.getStyleClass().add("danger-button");

                btnEditar.setOnAction(event -> {
                    Paciente seleccionado = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("pacientesForm.fxml"));
                        Parent root = loader.load();
                        DentifyController controladorFormulario = loader.getController();
                        controladorFormulario.prepararEdicionPaciente(seleccionado);

                        Stage stage = (Stage) btnEditar.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Dentify - Editar Paciente");
                        stage.show();
                    } catch (IOException e) {
                        System.err.println("Error al ir al formulario: " + e.getMessage());
                    }
                });

                btnEliminar.setOnAction(event -> {
                    Paciente seleccionado = getTableView().getItems().get(getIndex());
                    Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmacion.setTitle("Confirmar eliminación");
                    confirmacion.setHeaderText(null);
                    confirmacion.setContentText("¿Estás seguro de que deseas eliminar al paciente "
                            + seleccionado.getNombre() + " " + seleccionado.getApellido() + "?");

                    confirmacion.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {

                            boolean eliminadoOk = PacienteDAO.deletePaciente(seleccionado.getId_paciente());

                            if (eliminadoOk) {
                                getTableView().getItems().remove(seleccionado);
                                Alert exito = new Alert(Alert.AlertType.INFORMATION, "Paciente eliminado correctamente.");
                                exito.showAndWait();
                            } else {
                                Alert error = new Alert(Alert.AlertType.ERROR, "No se pudo eliminar el paciente de la base de datos.");
                                error.showAndWait();
                            }
                        }
                    });
                });

                contenedorBotones.getChildren().addAll(btnEditar, btnEliminar);
                contenedorBotones.setAlignment(javafx.geometry.Pos.CENTER);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : contenedorBotones);
            }
        });
    }

    @FXML
    private void onGesPacientesButtonClick(javafx.event.ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("Pacientes.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Dentify - Gestión de Pacientes");
            stage.show();
        } catch (java.io.IOException e) {
            System.err.println("Error al abrir la gestión de pacientes (Pacientes.fxml): " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void prepararEdicionPaciente(Paciente p) {
        this.pacienteAEditar = p;
        lblTituloFormulario.setText("Editar Paciente");
        btnGuardar.setText("Actualizar datos");

        TFNombre.setText(p.getNombre());
        TFApellido.setText(p.getApellido());
        TFTelefono.setText(p.getTelefono());
        TFCorreo.setText(p.getCorreo_electronico());
        TFNacimiento.setValue(p.getFecha_nacimiento());
    }

    @FXML
    private void manejarGuardarPaciente() {
        // 1. Validación de campos obligatorios
        if (TFNombre.getText().trim().isEmpty() || TFApellido.getText().trim().isEmpty() || TFTelefono.getText().trim().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING, "Por favor, rellena los campos obligatorios: Nombre, Apellido y Teléfono.");
            alerta.showAndWait();
            return;
        }

        if (pacienteAEditar != null) {
            pacienteAEditar.setNombre(TFNombre.getText().trim());
            pacienteAEditar.setApellido(TFApellido.getText().trim());
            pacienteAEditar.setTelefono(TFTelefono.getText().trim());
            pacienteAEditar.setCorreo_electronico(TFCorreo.getText().trim());
            pacienteAEditar.setFecha_nacimiento(TFNacimiento.getValue());

            boolean actualizadoOk = PacienteDAO.actualizarPaciente(pacienteAEditar);

            if (actualizadoOk) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "¡Paciente actualizado correctamente!");
                alerta.showAndWait();

                redireccionarAGestionPacientes();
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Error al actualizar el paciente en la base de datos.");
                alerta.showAndWait();
            }

        } else {
            Paciente nuevo = new Paciente(
                    0,
                    TFNombre.getText().trim(),
                    TFApellido.getText().trim(),
                    TFTelefono.getText().trim(),
                    TFCorreo.getText().trim(),
                    TFNacimiento.getValue()
            );

            boolean insertadoOk = PacienteDAO.createPaciente(nuevo);

            if (insertadoOk) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "¡Paciente guardado correctamente!");
                alerta.showAndWait();

                redireccionarAGestionPacientes();
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Error al guardar el paciente en la base de datos.");
                alerta.showAndWait();
            }
        }
    }

    private void redireccionarAGestionPacientes() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("Pacientes.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) btnGuardar.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Dentify - Gestión de Pacientes");
            stage.show();
        } catch (java.io.IOException e) {
            System.err.println("Error al redirigir a la tabla de pacientes: " + e.getMessage());
        }
    }

    private void limpiarFormulario() {
        pacienteAEditar = null;
        TFNombre.clear();
        TFApellido.clear();
        TFTelefono.clear();
        TFCorreo.clear();
        TFNacimiento.setValue(null);

        lblTituloFormulario.setText("Agregar paciente");
        btnGuardar.setText("Guardar");
    }

    private void cargarPacientes() {
        if (tablaPacientes != null) {
            List<Paciente> lista = PacienteDAO.getAllpacientes();

            todosLosPacientes.clear();
            pacientesFiltrados.clear();

            if (lista != null) {
                todosLosPacientes.addAll(lista);
                pacientesFiltrados.addAll(lista);
            }
        }
    }



    @FXML
    private void onNuevoPacienteButtonClick(javafx.event.ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("PacientesForm.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Dentify - Agregar Paciente");
            stage.show();
        } catch (java.io.IOException e) {
            System.err.println("Error al abrir el formulario (PacientesForm.fxml): " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onVolverPacientesFormButtonClick(javafx.event.ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("Pacientes.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Dentify - Gestión de Pacientes");
            stage.show();
        } catch (java.io.IOException e) {
            System.err.println("Error al volver a la tabla (Pacientes.fxml): " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onVolverPacientesButtonClick(javafx.event.ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("Main.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Dentify - Gestión de Pacientes");
            stage.show();
        } catch (java.io.IOException e) {
            System.err.println("Error al volver a la tabla (Main.fxml): " + e.getMessage());
            e.printStackTrace();
        }
    }


    // ------ Gestión citas ------

    @FXML
    private void onGesCitasButtonClick(javafx.event.ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("citas.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Dentify - Gestión de Citas");
            stage.show();
        } catch (java.io.IOException e) {
            System.err.println("Error al abrir la gestión de citas (citas.fxml): " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onVolverCitasButtonClick(javafx.event.ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("Main.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Dentify - Inicio");
            stage.show();
        } catch (java.io.IOException e) {
            System.err.println("Error al volver a la pantalla principal (Main.fxml): " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onNuevoCitaButtonClick(javafx.event.ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("citasform.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Dentify - Agregar Cita");
            stage.show();
        } catch (java.io.IOException e) {
            System.err.println("Error al abrir el formulario de citas (citasform.fxml): " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onVolverCitasFormButtonClick(javafx.event.ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("citas.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Dentify - Gestión de Citas");
            stage.show();
        } catch (java.io.IOException e) {
            System.err.println("Error al volver al listado de citas (citas.fxml): " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void prepararEdicionCita(Cita seleccionada) {
        this.citaAEditar = seleccionada;

        if (lblTituloCita != null) {
            lblTituloCita.setText("Editar cita #" + seleccionada.getIdCita());
        }
        if (btnGuardarCita != null) {
            btnGuardarCita.setText("Actualizar datos");
        }

        if (choiceBoxEstados != null) {
            choiceBoxEstados.getItems().setAll(com.example.dentify.Model.Estado.values());
            choiceBoxEstados.setValue(seleccionada.getEstado());
        }

        if (cboHora != null) {
            java.util.List<java.time.LocalTime> horasDisponibles = new java.util.ArrayList<>();
            for (int h = 9; h <= 20; h++) {
                horasDisponibles.add(java.time.LocalTime.of(h, 0));
                horasDisponibles.add(java.time.LocalTime.of(h, 30));
            }

            cboHora.getItems().setAll(horasDisponibles);

            if (seleccionada.getHora() != null) {
                cboHora.setValue(seleccionada.getHora().toLocalTime());
            }
        }

        if (DatePickerFecha != null) {
            DatePickerFecha.setValue(seleccionada.getFecha());
        }
        if (txtMotivo != null) {
            txtMotivo.setText(seleccionada.getMotivo());
        }

        if (choicePaciente != null) {
            choicePaciente.setValue(seleccionada.getPaciente());
        }
        if (choiceDoctor != null) {
            choiceDoctor.setValue(seleccionada.getDoctor());
        }
    }

    @FXML
    private void manejarGuardarCita() {
        if (DatePickerFecha.getValue() == null || cboHora.getValue() == null ||
                choicePaciente.getValue() == null || choiceDoctor.getValue() == null ||
                txtMotivo.getText().trim().isEmpty()) {

            Alert alerta = new Alert(Alert.AlertType.WARNING, "Por favor, rellene todos los campos para continuar.");
            alerta.showAndWait();
            return;
        }

        LocalDate fecha = DatePickerFecha.getValue();
        java.time.LocalTime tiempo = cboHora.getValue();
        LocalDateTime fechaHoraCompleta = LocalDateTime.of(fecha, tiempo);

        com.example.dentify.dao.CitaDAO citaDAO = new com.example.dentify.dao.CitaDAO();

        if (citaAEditar != null) {
            citaAEditar.setFecha(fecha);
            citaAEditar.setHora(fechaHoraCompleta);
            citaAEditar.setPaciente(choicePaciente.getValue());
            citaAEditar.setDoctor(choiceDoctor.getValue());
            citaAEditar.setMotivo(txtMotivo.getText().trim());
            citaAEditar.setEstado(choiceBoxEstados.getValue());

            boolean actualizadoOk = citaDAO.actualizarCita(citaAEditar);

            if (actualizadoOk) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "¡Cita actualizada correctamente con éxito!");
                alerta.showAndWait();
                redireccionarAGestionCitas();
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Error al actualizar la cita en la base de datos.");
                alerta.showAndWait();
            }

        } else {
            Cita nuevaCita = new Cita();
            nuevaCita.setFecha(fecha);
            nuevaCita.setHora(fechaHoraCompleta);
            nuevaCita.setPaciente(choicePaciente.getValue());
            nuevaCita.setDoctor(choiceDoctor.getValue());
            nuevaCita.setMotivo(txtMotivo.getText().trim());
            nuevaCita.setEstado(choiceBoxEstados.getValue());

            boolean insertadoOk = citaDAO.insertarCita(nuevaCita);

            if (insertadoOk) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "¡Cita programada y guardada con éxito en la base de datos!");
                alerta.showAndWait();

                redireccionarAGestionCitas();
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Error al insertar la cita en la base de datos.");
                alerta.showAndWait();
            }
        }
    }

    private void redireccionarAGestionCitas() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("Citas.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) btnGuardarCita.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Dentify - Gestión de Citas");
            stage.show();
        } catch (java.io.IOException e) {
            System.err.println("Error al redirigir a la vista de citas: " + e.getMessage());
        }
    }

    private void limpiarFormularioCita() {
        citaAEditar = null;
        DatePickerFecha.setValue(null);
        cboHora.setValue(null);
        choicePaciente.setValue(null);
        choiceDoctor.setValue(null);
        txtMotivo.clear();
        choiceBoxEstados.setValue(Estado.PENDIENTE);

        lblTituloCita.setText("Agregar cita");
        btnGuardarCita.setText("Guardar");
    }

    private void configurarColumnaAccionesCitas() {
        colAccionesCitas.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnEliminar = new Button("Eliminar");
            private final HBox contenedor = new HBox(10);

            {
                btnEditar.getStyleClass().add("primary-button-table");
                btnEliminar.getStyleClass().add("danger-button");

                btnEditar.setOnAction(event -> {
                    Cita seleccionada = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("CitasForm.fxml"));
                        Parent root = loader.load();
                        DentifyController controladorFormulario = loader.getController();
                        controladorFormulario.prepararEdicionCita(seleccionada);

                        Stage stage = (Stage) btnEditar.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Dentify - Editar Cita");
                        stage.show();
                    } catch (IOException e) {
                        System.err.println("Error al ir al formulario: " + e.getMessage());
                    }
                });

                btnEliminar.setOnAction(event -> {
                    Cita seleccionada = getTableView().getItems().get(getIndex());
                    Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmacion.setTitle("Confirmar eliminación");
                    confirmacion.setHeaderText(null);
                    confirmacion.setContentText("¿Estás seguro de que deseas eliminar la cita?");

                    confirmacion.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            boolean eliminadoOk = CitaDAO.eliminarCita(seleccionada.getIdCita());

                            if (eliminadoOk) {
                                getTableView().getItems().remove(seleccionada);
                                Alert exito = new Alert(Alert.AlertType.INFORMATION, "Cita eliminada correctamente.");
                                exito.showAndWait();
                            } else {
                                Alert error = new Alert(Alert.AlertType.ERROR, "No se pudo eliminar la cita de la base de datos.");
                                error.showAndWait();
                            }
                        }
                    });
                });

                contenedor.getChildren().addAll(btnEditar, btnEliminar);
                contenedor.setAlignment(javafx.geometry.Pos.CENTER);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(contenedor);
                }
            }
        });
    }


    private void confirmarEliminarCita(Cita cita) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Estás seguro de eliminar la cita #" + cita.getIdCita() + "?");
        alert.setContentText("Esta acción no se puede deshacer.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            tablaCitas.getItems().remove(cita);
            System.out.println("Cita deleted: " + cita.getIdCita());
        }
    }

    private void configurarColumnasCitas() {
        colFecha.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getFecha()));
        colMotivo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMotivo()));
        colEstado.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getEstado()));

        colHora.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getHora()));

        colHora.setCellFactory(column -> new TableCell<Cita, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm");
                    setText(item.format(formatter));
                }
            }
        });

        colPaciente.setCellValueFactory(cellData -> {
            Paciente p = cellData.getValue().getPaciente();
            return new javafx.beans.property.SimpleStringProperty(p != null ? p.getNombre() + " " + p.getApellido() : "");
        });

        colDoctor.setCellValueFactory(cellData -> {
            Doctor d = cellData.getValue().getDoctor();
            return new javafx.beans.property.SimpleStringProperty(d != null ? d.getNombre() : "");
        });
    }

    @FXML
    private void manejarNuevaCita() {
        limpiarFormularioCita();
        System.out.println("Cambiando a la vista de formulario de citas...");
    }


    private void cargarDatosDePruebaCitas() {
        Paciente p1 = new Paciente(1, "Francella", "Rojas", "123456789",
                "fran@test.com", LocalDate.of(2000, 1, 1));
        Paciente p2 = new Paciente(2, "Juan", "David", "987654321",
                "juan@test.com", LocalDate.of(1998, 5, 10));

        Doctor d1 = new Doctor();
        d1.setIdDoctor(1);
        d1.setNombre("Dr. Anuar");

        Doctor d2 = new Doctor();
        d2.setIdDoctor(2);
        d2.setNombre("Dra. Andrea");

        choicePaciente.getItems().addAll(p1, p2);
        choiceDoctor.getItems().addAll(d1, d2);

        LocalDateTime hora1 = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0));
        LocalDateTime hora2 = LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 30));

        Cita c1 = new Cita();
        c1.setIdCita(1);
        c1.setPaciente(p1);
        c1.setDoctor(d1);
        c1.setFecha(LocalDate.now());
        c1.setHora(hora1);
        c1.setMotivo("Revisión rutinaria");
        c1.setEstado(Estado.PENDIENTE);

        Cita c2 = new Cita();
        c2.setIdCita(2);
        c2.setPaciente(p2);
        c2.setDoctor(d2);
        c2.setFecha(LocalDate.now().plusDays(1));
        c2.setHora(hora2);
        c2.setMotivo("Ajuste de ortodoncia");
        c2.setEstado(Estado.ACTIVO);

        tablaCitas.getItems().addAll(c1, c2);
    }

// -------- GESTION TRATAMIENTOS --------

    @FXML
    public void manejarNuevoTratamiento(ActionEvent actionEvent) {
        limpiarFormularioTratamiento();
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("TratamientosForm.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Dentify - Agregar Tratamiento");
            stage.show();
        } catch (java.io.IOException e) {
            System.err.println("Error al abrir el formulario (TratamientosForm.fxml): " + e.getMessage());
        }
    }

    @FXML
    public void onVolverTratamientosFormButtonClick(javafx.event.ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("Tratamientos.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Dentify - Gestión de Tratamientos");
            stage.show();
        } catch (java.io.IOException e) {
            System.err.println("Error al volver a la tabla (Tratamientos.fxml): " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onTratamientoButtonClick(javafx.event.ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("Tratamientos.fxml"));
            javafx.scene.Parent root = loader.load();

            // recupero el controlador de la nueva escena y fuerzo la carga
            DentifyController controlador = loader.getController();
            controlador.configurarTablaTratamientos();

            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Dentify - Gestión de Tratamientos");
            stage.show();
        } catch (java.io.IOException e) {
            System.err.println("Error al abrir la gestión de tratamientos (Tratamientos.fxml): " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onVolverTratamientosButtonClick(javafx.event.ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("Main.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Dentify - Inicio");
            stage.show();
        } catch (java.io.IOException e) {
            System.err.println("Error al volver a la tabla (Main.fxml): " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void manejarGuardarTratamiento(ActionEvent actionEvent) {
        if (TFNombreTratamiento.getText().trim().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING, "El nombre del tratamiento es obligatorio.");
            alerta.showAndWait();
            return;
        }

        if (tratamientoAEditar != null) {
            tratamientoAEditar.setNombre(TFNombreTratamiento.getText().trim());
            tratamientoAEditar.setDescripcion(TFDescripcion.getText().trim());

            boolean actualizadoOk = com.example.dentify.dao.TratamientoDAO.updateTratamiento(tratamientoAEditar);

            if (actualizadoOk) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "¡Tratamiento actualizado correctamente!");
                alerta.showAndWait();
                onVolverTratamientosFormButtonClick(actionEvent);
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Error al actualizar el tratamiento en la base de datos.");
                alerta.showAndWait();
            }
        } else {
            Tratamiento nuevo = new Tratamiento(
                0,
                TFNombreTratamiento.getText().trim(),
                TFDescripcion.getText().trim()
            );

            boolean insertadoOk = com.example.dentify.dao.TratamientoDAO.createTratamiento(nuevo);

            if (insertadoOk) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "¡Tratamiento guardado correctamente!");
                alerta.showAndWait();
                onVolverTratamientosFormButtonClick(actionEvent);
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Error al guardar el tratamiento en la base de datos.");
                alerta.showAndWait();
            }
        }
    }

    public void prepararEdicionTratamiento(Tratamiento t){
        this.tratamientoAEditar = t;
        lblTituloFormularioTratamiento.setText("Editar Tratamiento");
        btnGuardarTratamiento.setText("Actualizar");

        TFNombreTratamiento.setText(t.getNombre());
        TFDescripcion.setText(t.getDescripcion());
    }

    private void configurarTablaTratamientos(){
        List<Tratamiento> lista = com.example.dentify.dao.TratamientoDAO.getAllTratamientos();
        tablaTratamientos.getItems().clear();
        if (lista != null) {
            tablaTratamientos.getItems().addAll(lista);
        }
    }

    private void configurarColumnaAccionesTratamientos(){
        colAccionesTratamientos.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnEliminar = new Button("Eliminar");
            private final HBox contenedorBotones = new HBox(10);

            {
                btnEditar.getStyleClass().add("primary-button-table");
                btnEliminar.getStyleClass().add("danger-button");

                btnEditar.setOnAction(event -> {
                    Tratamiento seleccionado = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("TratamientosForm.fxml"));
                        Parent root = loader.load();
                        DentifyController controladorFormulario = loader.getController();

                        controladorFormulario.prepararEdicionTratamiento(seleccionado);

                        Stage stage = (Stage) btnEditar.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Dentify - Editar Tratamiento");
                        stage.show();
                    } catch (IOException e) {
                        System.err.println("Error al ir al formulario de tratamientos: " + e.getMessage());
                    }
                });

                btnEliminar.setOnAction(event -> {
                    Tratamiento seleccionado = getTableView().getItems().get(getIndex());
                    confirmarEliminarTratamiento(seleccionado);
                });

                contenedorBotones.getChildren().addAll(btnEditar, btnEliminar);
                contenedorBotones.setAlignment(javafx.geometry.Pos.CENTER);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : contenedorBotones);
            }
        });
    }

    private void confirmarEliminarTratamiento(Tratamiento t) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que deseas eliminar el tratamiento: " + t.getNombre() + "?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean eliminadoOk = com.example.dentify.dao.TratamientoDAO.deleteTratamiento(t.getIdTratamiento());
                if (eliminadoOk) {
                    tablaTratamientos.getItems().remove(t);
                    Alert exito = new Alert(Alert.AlertType.INFORMATION, "Tratamiento eliminado correctamente.");
                    exito.showAndWait();
                } else {
                    Alert error = new Alert(Alert.AlertType.ERROR, "No se pudo eliminar de la base de datos.");
                    error.showAndWait();
                }
            }
        });
    }

    private void limpiarFormularioTratamiento() {
        tratamientoAEditar = null;
        if (TFNombreTratamiento != null) TFNombreTratamiento.clear();
        if (TFDescripcion != null) TFDescripcion.clear();
        if (lblTituloFormularioTratamiento != null) lblTituloFormularioTratamiento.setText("Agregar Tratamiento");
        if (btnGuardarTratamiento != null) btnGuardarTratamiento.setText("Guardar");
    }


    //------------------HISTORIAL CLINICO-------------------------
    @FXML private javafx.scene.layout.GridPane gridPanePacientes;

    public void mostrarHistorialesEnPantalla(List<HistorialClinico> listaHistoriales) {
        gridPanePacientes.getChildren().clear();

        com.example.dentify.dao.HistorialClinicoDAO historialDAO = new com.example.dentify.dao.HistorialClinicoDAO();

        int columna = 0;
        int fila = 0;

        for (HistorialClinico hc : listaHistoriales) {
            VBox tarjeta = new VBox(15);
            tarjeta.getStyleClass().add("panel-background");
            tarjeta.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 4);");

            BorderPane header = new BorderPane();
            VBox infoPaciente = new VBox(2);

            Label lblNombre = new Label(hc.getPaciente().getNombre() + " " + hc.getPaciente().getApellido());
            lblNombre.getStyleClass().add("label-primary");
            lblNombre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            infoPaciente.getChildren().addAll(lblNombre);
            header.setLeft(infoPaciente);

            HBox layoutDatos = new HBox(60);

            VBox bloqueFecha = new VBox(4);
            Label titFecha = new Label("Fecha Creación");
            titFecha.setStyle("-fx-text-fill: #888888; -fx-font-size: 11px;");
            Label valFecha = new Label(hc.getFechaCreacion().toString());
            valFecha.getStyleClass().add("label-primary");
            bloqueFecha.getChildren().addAll(titFecha, valFecha);

            VBox bloqueSangre = new VBox(4);
            Label titSangre = new Label("Grupo Sanguíneo");
            titSangre.setStyle("-fx-text-fill: #888888; -fx-font-size: 11px;");
            Label valSangre = new Label(hc.getGrupoSanguineo());
            valSangre.getStyleClass().add("label-primary");
            bloqueSangre.getChildren().addAll(titSangre, valSangre);

            layoutDatos.getChildren().addAll(bloqueFecha, bloqueSangre);

            VBox seccionTratamientos = new VBox(10);
            BorderPane headerTratamientos = new BorderPane();

            Label titTratamientos = new Label("Tratamientos Registrados");
            titTratamientos.getStyleClass().add("label-primary");

            List<String> intervenciones = historialDAO.obtenerIntervencionesCronologicas(hc.getIdPaciente());

            Label badgeContador = new Label(String.valueOf(intervenciones.size()));
            badgeContador.getStyleClass().add("label-secondary");
            badgeContador.setStyle("-fx-background-color: #EBF7F7; -fx-background-radius: 50; -fx-padding: 2 8 2 8; -fx-font-weight: bold;");

            headerTratamientos.setLeft(titTratamientos);
            headerTratamientos.setRight(badgeContador);
            seccionTratamientos.getChildren().add(headerTratamientos);

            if (intervenciones.isEmpty()) {
                Label sinDatos = new Label("No hay intervenciones registradas.");
                sinDatos.setStyle("-fx-text-fill: #A0A0A0; -fx-font-style: italic; -fx-padding: 0 0 0 5;");
                seccionTratamientos.getChildren().add(sinDatos);
            } else {
                VBox listaLineas = new VBox(6);
                for (String linea : intervenciones) {
                    Label lblLinea = new Label(linea);
                    lblLinea.setStyle("-fx-text-fill: #4A4A4A; -fx-font-size: 13px; -fx-padding: 0 0 0 5;");
                    lblLinea.setWrapText(true);
                    listaLineas.getChildren().add(lblLinea);
                }

                ScrollPane scrollTarjeta = new ScrollPane();
                scrollTarjeta.setContent(listaLineas);
                scrollTarjeta.setFitToWidth(true);
                scrollTarjeta.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                scrollTarjeta.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

                // Mantiene el tamaño compacto adaptativo y activa scroll vertical si supera los 90px
                scrollTarjeta.setMaxHeight(90);
                scrollTarjeta.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

                seccionTratamientos.getChildren().add(scrollTarjeta);
            }

            tarjeta.getChildren().addAll(header, layoutDatos, seccionTratamientos);

            // Posicionamos la tarjeta en la coordenada bidimensional de la rejilla
            gridPanePacientes.add(tarjeta, columna, fila);

            // Control estructural clásico del flujo de dos columnas
            columna++;
            if (columna > 1) {
                columna = 0;
                fila++;
            }
        }
    }

    @FXML
    private void onVolverHistorialButtonClick(javafx.event.ActionEvent event){
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("Main.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Dentify - Gestión de Pacientes");
            stage.show();
        } catch (java.io.IOException e) {
            System.err.println("Error al volver a la tabla (Main.fxml): " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onHistorialButtonClick(javafx.event.ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("HistorialClinico.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Dentify - Gestión de Citas");
            stage.show();
        } catch (java.io.IOException e) {
            System.err.println("Error al abrir la gestión de citas (citas.fxml): " + e.getMessage());
            e.printStackTrace();
        }
    }

}
        //CIERRE DENTIFY CONTROLLER