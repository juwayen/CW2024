module com.example.demo {
    requires javafx.controls;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}