package hr.algebra.javafxinsurance.controller;

import hr.algebra.javafxinsurance.Main;
import hr.algebra.javafxinsurance.dto.VehicleInfoDTO;
import hr.algebra.javafxinsurance.serialization.exceptions.NonSerializableClassException;
import hr.algebra.javafxinsurance.service.AuthService;
import hr.algebra.javafxinsurance.utils.UIUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileMenuController implements Initializable {
    public TextField tfPlate;
    public TextField tfBrand;
    public TextField tfModel;
    public TextField tfManufacturingYear;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            bindVehicle();
        } catch (IllegalAccessException e) {
            UIUtils.showInvalidFormAlert(e.getMessage());
        }
    }

    private void bindVehicle() throws IllegalAccessException {
        VehicleInfoDTO authenticatedVehicle = AuthService.INSTANCE.getAuthenticatedVehicle();
        tfBrand.setText(authenticatedVehicle.getBrand());
        tfManufacturingYear.setText(authenticatedVehicle.getManufacturingYear().toString());
        tfModel.setText(authenticatedVehicle.getModel());
        tfPlate.setText(authenticatedVehicle.getPlate());
    }

    public void onLogout(ActionEvent actionEvent) {
        try {
            AuthService.INSTANCE.logOut();
            Main.showLoginScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
