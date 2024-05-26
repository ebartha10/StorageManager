module com.example.tp_tema3_fx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;

    opens com.example.tp_tema3_fx to javafx.fxml;
    exports com.example.tp_tema3_fx;
    exports com.example.tp_tema3_fx.ui;
    exports com.example.tp_tema3_fx.backend.model;
    exports com.example.tp_tema3_fx.backend.dao;
    exports com.example.tp_tema3_fx.backend.dto;
    exports com.example.tp_tema3_fx.backend.service;
    exports com.example.tp_tema3_fx.backend.singleton;
    exports com.example.tp_tema3_fx.backend.database;
    exports com.example.tp_tema3_fx.backend.dao.impl;
    exports com.example.tp_tema3_fx.recycler;
    exports com.example.tp_tema3_fx.backend.database.impl;

    opens com.example.tp_tema3_fx.ui to javafx.fxml;
}