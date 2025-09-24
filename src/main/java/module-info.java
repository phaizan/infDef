module com.example.infprotection {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.infprotection to javafx.fxml;
    exports com.example.infprotection;
}