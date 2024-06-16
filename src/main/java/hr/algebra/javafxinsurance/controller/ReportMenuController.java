package hr.algebra.javafxinsurance.controller;

import hr.algebra.javafxinsurance.dto.ReportDTO;
import hr.algebra.javafxinsurance.model.ReportStatus;
import hr.algebra.javafxinsurance.service.AuthService;
import hr.algebra.javafxinsurance.service.ReportService;
import hr.algebra.javafxinsurance.utils.UIUtils;
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
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReportMenuController implements Initializable {
    public TextField tfVehicleA;
    public TextField tfDriverA;
    public TextArea taDamagesA;
    public TextField tfVehicleB;
    public TextField tfDriverB;
    public TextArea taDamagesB;
    public HBox hbButtons;
    public ComboBox<ReportStatus> cbReportsSelector;
    public TableColumn<ReportDTO, String> tcDate;
    public TableColumn<ReportDTO, String> tcPlace;
    public TableColumn<ReportDTO, String> tcDetails;
    public TableColumn<ReportDTO, ReportStatus> tcStatus;
    public TableView<ReportDTO> tableReports;

    private ObservableList<ReportDTO> reports;
    private ReportDTO reportSelected;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            hbButtons.setVisible(false);
            setupComboBox();
            initializeComboBox();
            setupTable();
            loadReports();
        } catch (IllegalAccessException e) {
            UIUtils.showInvalidFormAlert(e.getMessage());
        }
    }

    private void setupComboBox() {
        cbReportsSelector
                .getItems()
                .addAll(
                        ReportStatus
                                .getAll()
                );
    }

    private void initializeComboBox() {
        cbReportsSelector.setValue(ReportStatus.ALL);
    }

    private void setupTable() {
        reports = FXCollections.observableArrayList();
        tableReports.setItems(reports);
        tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tcDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        tcPlace.setCellValueFactory(new PropertyValueFactory<>("place"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableReports.setRowFactory(tv -> {
            TableRow<ReportDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    ReportDTO rowData = row.getItem();
                    reportSelected = rowData;
                    try {
                        bindSelectedReport();
                    } catch (IllegalAccessException e) {
                        UIUtils.showInvalidFormAlert(e.getMessage());
                    }
                }
            });
            return row;
        });
    }

    private void bindSelectedReport() throws IllegalAccessException {
        if (reportSelected == null) {
            this.taDamagesA.setText("");
            this.taDamagesB.setText("");
            this.tfDriverA.setText("");
            this.tfDriverB.setText("");
            this.tfVehicleA.setText("");
            this.tfVehicleB.setText("");
            this.hbButtons.setVisible(
                    false
            );
        } else {
            this.taDamagesA.setText(reportSelected.getInfoReportDriverA().getDamages());
            this.taDamagesB.setText(reportSelected.getInfoReportDriverB().getDamages());
            this.tfDriverA.setText(reportSelected.getInfoReportDriverA().getDriver().toString());
            this.tfDriverB.setText(reportSelected.getInfoReportDriverB().getDriver().toString());
            this.tfVehicleA.setText(reportSelected.getInfoReportDriverA().getVehicle().toString());
            this.tfVehicleB.setText(reportSelected.getInfoReportDriverB().getVehicle().toString());
            this.hbButtons.setVisible(
                    Objects.equals(reportSelected.getInfoReportDriverB().getVehicle().getPlate(), AuthService.INSTANCE.getAuthenticatedVehicle().getPlate()) &&
                            reportSelected.getStatus() == ReportStatus.WAITING
            );
        }
    }

    private void loadReports() throws IllegalAccessException {
        List<ReportDTO> reportList = ReportService
                .INSTANCE
                .getReportsFiltered(
                        cbReportsSelector.getValue()
                );

        Platform.runLater(() -> {
            reports.clear();
            reports.addAll(reportList);
        });
    }

    public void onReject(ActionEvent actionEvent) {
        try {
            ReportService.INSTANCE.rejectReport(reportSelected);
            loadReports();
            reportSelected = null;
            bindSelectedReport();
        } catch (IllegalArgumentException | IllegalAccessException e) {
            UIUtils.showInvalidFormAlert(e.getMessage());
        }
    }

    public void onAccept(ActionEvent actionEvent) {
        try {
            String damagesBText = taDamagesB.getText();
            if (damagesBText.isBlank()) throw new IllegalArgumentException("Damages must be completed");
            reportSelected.getInfoReportDriverB().setDamages(damagesBText);
            ReportService.INSTANCE.acceptReport(reportSelected);
            loadReports();
            reportSelected = null;
            bindSelectedReport();
        } catch (IllegalArgumentException | IllegalAccessException e) {
            UIUtils.showInvalidFormAlert(e.getMessage());
        }
    }

    public void onOpenReport(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/javafxinsurance/open-report.fxml"));
            Parent parent = loader.load();

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Open Report");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL);
            OpenReportController controller = loader.getController();
            controller.setDialogStage(stage);
            stage.showAndWait();

            loadReports();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            UIUtils.showInvalidFormAlert(e.getMessage());
        }
    }

    public void onReportStatusSelected(ActionEvent actionEvent) {
        try {
            loadReports();
        } catch (IllegalAccessException e) {
            UIUtils.showInvalidFormAlert(e.getMessage());
        }
    }
}
