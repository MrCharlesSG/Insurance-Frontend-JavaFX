package hr.algebra.javafxinsurance.controller;

import hr.algebra.javafxinsurance.dto.DriverDTO;
import hr.algebra.javafxinsurance.dto.ReportRequestDTO;
import hr.algebra.javafxinsurance.dto.VehicleInfoDTO;
import hr.algebra.javafxinsurance.service.AuthService;
import hr.algebra.javafxinsurance.service.DriverService;
import hr.algebra.javafxinsurance.service.ReportService;
import hr.algebra.javafxinsurance.utils.DateUtils;
import hr.algebra.javafxinsurance.utils.UIUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class OpenReportController implements Initializable {
    public TextField tfVehicleA;
    public TableView<DriverDTO> tableDriversA;
    public TableColumn<DriverDTO, String> tcNameA;
    public TableColumn<DriverDTO, String> tcSurnamesA;
    public TableView<DriverDTO> tableDriversB;
    public TableColumn<DriverDTO, String> tcNameB;
    public TableColumn<DriverDTO, String> tcSurnamesB;
    public Label lbSelectedDriverA;
    public TextArea taDamagesB;
    public Label lbSelectedDriverB;
    public TextField tfVehicleB;
    private ObservableList<DriverDTO> driversA;
    private ObservableList<DriverDTO> driversB;
    public TextArea taDamagesA;
    public DatePicker dpDate;
    public TextField tfPlace;
    public TextArea taDetails;

    public DriverDTO driverASelected;
    public DriverDTO driverBSelected;
    private Stage dialogStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableDriversA();
        setupTableDriversB();
        try {
            bindTableA();
            dpDate.setValue(LocalDate.now());
        } catch (IllegalAccessException e) {
            UIUtils.showInvalidFormAlert(e.getMessage());
        }
    }

    private void setupTableDriversA() {
        driversA = FXCollections.observableArrayList();
        tableDriversA.setItems(driversA);
        tcNameA.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcSurnamesA.setCellValueFactory(new PropertyValueFactory<>("surnames"));
        tableDriversA.setRowFactory(tv -> {
            TableRow<DriverDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    DriverDTO rowData = row.getItem();
                    driverASelected = rowData;
                    bindSelectedA();
                }
            });
            return row;
        });
    }

    private void bindSelectedA() {
        if(driverASelected==null){
            lbSelectedDriverA.setText("No selected");
        }else{
            lbSelectedDriverA.setText(driverASelected.toString());
        }
    }

    private void setupTableDriversB() {
        driversB = FXCollections.observableArrayList();
        tableDriversB.setItems(driversB);
        tcNameB.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcSurnamesB.setCellValueFactory(new PropertyValueFactory<>("surnames"));
        tableDriversB.setRowFactory(tv -> {
            TableRow<DriverDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    driverBSelected = row.getItem();
                    bindSelectedB();
                }
            });
            return row;
        });

    }

    private void bindSelectedB() {
        if(driverBSelected==null){
            lbSelectedDriverB.setText("No selected");
        }else{
            lbSelectedDriverB.setText(driverBSelected.toString());
        }
    }

    private void bindTableA() throws IllegalAccessException {
        VehicleInfoDTO authenticatedVehicle = AuthService.INSTANCE.getAuthenticatedVehicle();
        tfVehicleA.setText(authenticatedVehicle.toString());
        driversA.addAll(DriverService.INSTANCE.getDriversByVehicle(authenticatedVehicle.getPlate()));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void onResetReport(ActionEvent actionEvent) {
        tfPlace.clear();
        taDetails.clear();
        dpDate.setValue(LocalDate.now());
    }

    public void onResetInfoA(ActionEvent actionEvent) {
        taDamagesA.clear();
        driverASelected=null;
        bindSelectedA();
    }

    public void onSearchA() {

    }

    public void onOpenReport() {
        try {
            validateForm();
            ReportRequestDTO reportRequestDTO = new ReportRequestDTO(
                    driverASelected.getId(),
                    driverBSelected.getId(),
                    tfVehicleB.getText(),
                    taDamagesA.getText(),
                    DateUtils.getDateFromLocalDate(dpDate.getValue()),
                    tfPlace.getText(),
                    taDetails.getText()
            );
            ReportService.INSTANCE.openReport(reportRequestDTO);
            dialogStage.close();
        }catch (IllegalArgumentException | IllegalAccessException e){
            UIUtils.showInvalidFormAlert(e.getMessage());
        }
    }

    private void validateForm() {
        if(tfVehicleB.getText().isBlank()) throw new IllegalArgumentException("Vehicle B must be determined");
        if(driverBSelected==null || driverASelected==null) throw new IllegalArgumentException("You must select both drivers");
        if(tfPlace.getText().isBlank()) throw new IllegalArgumentException("Place must be determined");
        if(dpDate.getValue()==null) throw new IllegalArgumentException("Date must be determined");
        if(taDetails.getText().isBlank()) throw new IllegalArgumentException("Put some details of the incident");
        if(taDamagesA.getText().isBlank()) throw new IllegalArgumentException("Set the damages of your vehicle");
    }

    public void onSearchB(ActionEvent actionEvent) {
        try{
            driversB.clear();
            driverBSelected=null;
            bindSelectedB();
            List<DriverDTO> driversByVehicle = DriverService.INSTANCE.getDriversByVehicle(tfVehicleB.getText());
            driversB.addAll(driversByVehicle);
        }catch (Exception e){
            UIUtils.showInvalidFormAlert("Could not found the vehicle: "+tfVehicleB.getText());
        }
    }

    public void onResetInfoB(ActionEvent actionEvent) {
        tfVehicleB.clear();
        driverBSelected = null;
        bindSelectedB();
        driversB.clear();
        taDamagesB.clear();
    }
}
