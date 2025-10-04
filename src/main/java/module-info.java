module com.example.guiassignment {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires junit;

    opens com.example.guiassignment to javafx.fxml;
    exports com.example.guiassignment;
}