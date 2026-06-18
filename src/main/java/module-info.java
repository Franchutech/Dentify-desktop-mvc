module com.example.dentify {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires mysql.connector.j;


    opens com.example.dentify to javafx.fxml;
    opens com.example.dentify.Configuration to javafx.fxml;
    opens com.example.dentify.dao to javafx.fxml;
    opens com.example.dentify.Model to javafx.fxml;


    exports com.example.dentify;
    exports com.example.dentify.Configuration;
    exports com.example.dentify.dao;
    exports com.example.dentify.Model;
}