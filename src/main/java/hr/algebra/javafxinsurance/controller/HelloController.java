package hr.algebra.javafxinsurance.controller;

import hr.algebra.javafxinsurance.dto.VehicleInfoDTO;
import hr.algebra.javafxinsurance.service.AuthService;
import hr.algebra.javafxinsurance.utils.UIUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    private static final String VIEWS_DIRECTORIES = "/hr/algebra/javafxinsurance/";
    public BorderPane bpMain;
    public Button btnProfile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        VehicleInfoDTO authenticatedVehicle = null;
        try {
            authenticatedVehicle = AuthService.INSTANCE.getAuthenticatedVehicle();
            String plate = authenticatedVehicle.getPlate();
            btnProfile.setText(plate);
        } catch (IllegalAccessException e) {
            UIUtils.showInvalidFormAlert(e.getMessage());
        }
    }

    public void onProfile(ActionEvent actionEvent) {
        loadPage("profile-menu");
    }

    public void onDrivers(ActionEvent actionEvent) {
        loadPage("drivers-menu");
    }

    public void onReport(ActionEvent actionEvent) {
        loadPage("report-menu");

    }

    private void loadPage(String page){
        Parent root=null;
        try {
            root = FXMLLoader.load(getClass().getResource(VIEWS_DIRECTORIES+page+".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bpMain.setCenter(root);
    }
}