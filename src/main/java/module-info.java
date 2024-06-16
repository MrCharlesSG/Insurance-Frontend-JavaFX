module hr.algebra.javafxinsurance {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires spring.web;
    requires com.google.gson;
    requires reflections;
    requires spring.core;

    opens hr.algebra.javafxinsurance to javafx.fxml;
    exports hr.algebra.javafxinsurance;
    exports hr.algebra.javafxinsurance.controller;
    exports hr.algebra.javafxinsurance.dto;
    exports hr.algebra.javafxinsurance.service;
    exports hr.algebra.javafxinsurance.model;

    opens hr.algebra.javafxinsurance.controller to javafx.fxml;
}
