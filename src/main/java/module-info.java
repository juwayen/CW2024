module com.example.demo {
    requires javafx.controls;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.util;
    exports com.example.demo.entity.plane;
    exports com.example.demo.service;
    exports com.example.demo.entity.bullet;
}