module com.example.demo {
    requires javafx.controls;
    requires javafx.media;

    exports com.example.demo;
    exports com.example.demo.background;
    exports com.example.demo.entity;
    exports com.example.demo.entity.bullet;
    exports com.example.demo.entity.collectible;
    exports com.example.demo.entity.plane;
    exports com.example.demo.entity.state;
    exports com.example.demo.factory;
    exports com.example.demo.level;
    exports com.example.demo.screen;
    exports com.example.demo.service;
    exports com.example.demo.ui;
    exports com.example.demo.util;
}