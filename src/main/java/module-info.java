module com.example.demo {
    requires javafx.graphics;
    requires javafx.base;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}