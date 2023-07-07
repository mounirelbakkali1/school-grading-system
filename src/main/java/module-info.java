module ma.example.schoolsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires net.synedra.validatorfx;
    requires mysql.connector.java;
    requires java.sql;

    opens ma.example.schoolsystem to javafx.fxml;
    exports ma.example.schoolsystem;
}