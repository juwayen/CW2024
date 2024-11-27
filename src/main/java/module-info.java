module com.example.demo {
    requires javafx.graphics;
    requires javafx.base;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo.manager;
    opens com.example.demo.entity to javafx.fxml;
    opens com.example.demo.level to javafx.fxml;
    opens com.example.demo.signal to javafx.fxml;
    opens com.example.demo.ui to javafx.fxml;
    exports com.example.demo;
    opens com.example.demo.entity.boss to javafx.fxml;
    opens com.example.demo.entity.enemy to javafx.fxml;
    opens com.example.demo.entity.player to javafx.fxml;
}