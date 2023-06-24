module com.example.teste123 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires com.dlsc.formsfx;

    opens com.example.teste123 to javafx.fxml;
    exports com.example.teste123;
}