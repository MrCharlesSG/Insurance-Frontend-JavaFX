package hr.algebra.javafxinsurance.controller;

import hr.algebra.javafxinsurance.dto.DriverDTO;
import hr.algebra.javafxinsurance.service.DriverService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static hr.algebra.javafxinsurance.utils.UIUtils.showAlert;
import static hr.algebra.javafxinsurance.utils.UIUtils.showInvalidFormAlert;

public class DriversMenuController implements Initializable {

    public TextField tfName;
    public TextField tfSurname;
    public TextField tfPassport;
    public TextField tfEmail;
    public TextField tfBirtday;
    public TableView<DriverDTO> tableDrivers;
    public TableColumn<DriverDTO, String> tcName;
    public TableColumn<DriverDTO, String> tcSurname;
    public TableColumn<DriverDTO, String> tcPassport;
    public TableColumn<DriverDTO, String> tcEmail;
    public TableColumn<DriverDTO, String> tcBirthday;

    private ObservableList<DriverDTO> drivers;
    private DriverDTO driverSelected;


    public void onDissociate(ActionEvent actionEvent) {
        if(driverSelected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Dissociate Driver");
            alert.setContentText("Are you sure you want to dissociate this driver?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        DriverService.INSTANCE.disassociateDriver(driverSelected.getEmail());
                        driverSelected=null;
                        bindSelectedDriver();
                        loadDrivers();
                    } catch (Exception e) {
                        showAlert(Alert.AlertType.ERROR,"Error", e.getMessage());
                    }
                }
            });
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupDriversTable();
        setupDriversTableListener();
        try {
            loadDrivers();
        } catch (IllegalAccessException e) {
            showInvalidFormAlert(e.getMessage());
        }
    }

    private void loadDrivers() throws IllegalAccessException {
        List<DriverDTO> driversList = DriverService.INSTANCE.getDrivers();

        Platform.runLater(() -> {
            drivers.clear();
            drivers.addAll(driversList);
        });
    }

    private void setupDriversTableListener() {
        tableDrivers.setRowFactory(tv -> {
            TableRow<DriverDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    DriverDTO rowData = row.getItem();
                    driverSelected=rowData;
                    bindSelectedDriver();
                }
            });
            return row;
        });
    }

    private void bindSelectedDriver() {
        if(driverSelected==null){
            reset();
        }else{

        tfBirtday.setText(driverSelected.getBirthday().toString());
        tfBirtday.setEditable(false);
        tfName.setText(driverSelected.getName());
        tfName.setEditable(false);
        tfSurname.setText(driverSelected.getSurnames());
        tfSurname.setEditable(false);
        tfPassport.setText(driverSelected.getPassport());
        tfPassport.setEditable(false);
        tfEmail.setText(driverSelected.getEmail());
        tfEmail.setEditable(false);
        }
    }

    private void reset() {
        tfBirtday.setText("");
        tfName.setText("");
        tfSurname.setText("");
        tfPassport.setText("");
        tfEmail.setText("");
    }

    private void setupDriversTable() {
        drivers = FXCollections.observableArrayList();
        tableDrivers.setItems(drivers);
        tcName.setCellValueFactory(new PropertyValueFactory<DriverDTO, String>("name"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<DriverDTO, String>("email"));
        tcSurname.setCellValueFactory(new PropertyValueFactory<DriverDTO, String>("surnames"));
        tcPassport.setCellValueFactory(new PropertyValueFactory<DriverDTO, String>("passport"));
        tcBirthday.setCellValueFactory(new PropertyValueFactory<DriverDTO, String>("birthday"));
    }


    public void onAssociate(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/javafxinsurance/new-driver.fxml"));
            Parent parent = loader.load();

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("New Driver");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL);
            AssociateDriverController controller = loader.getController();
            controller.setDialogStage(stage);
            stage.showAndWait();

            loadDrivers();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            showInvalidFormAlert(e.getMessage());
        }
    }
}
